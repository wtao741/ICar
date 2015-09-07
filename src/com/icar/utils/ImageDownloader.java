/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.icar.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import com.icar.activity.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


/**
 * 图片下载类
 * This helper class download images from the Internet and binds those with the
 * provided ImageView.
 */
public class ImageDownloader {
	private static final String LOG_TAG = "ImageDownloader";
	private int screenWidth;
	private int screenHeight;
	private static Context context;
	private int defaultHeadImgID;
	private static final int IMAGE_HALFWIDTH = 20;
	private String imgSource = IMG_SOURCE_EXCEPT_CHAT_RAW;// 图片来源：聊天、俱乐部、文章
	public static String IMG_SOURCE_CHAT_RAW = "img_source_chat_raw";
	public static String IMG_SOURCE_EXCEPT_CHAT_THUMB = "img_source_except_chat_thumb";
	public static String IMG_SOURCE_EXCEPT_CHAT_RAW = "img_source_except_chat_raw";

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	private ImageDownloader() {
	}

	public ImageDownloader(Context context) {
		this.context = context;
	}

	/**
	 * 最常用的的图片加载方法
	 * 1、从互联网上下载指定的图像并将它绑定到提供ImageView
	 * 2、如果要下载的图片在缓存中找到，则立即的并且是异步的执行绑定操作
	 * 3、如果发生了错误或异常则会绑定一张默认的图片到imageView
	 * 
	 * Download the specified image from the Internet and binds it to the provided ImageView.
	 * The binding is immediate if the image is found in the cache and will be done asynchronously otherwise
	 * A null bitmap will be associated to the ImageView if an error occurs.
	 * 
	 * @param url		要下载的图片的地址			
	 * @param imageView 		下载图片要绑定的imageView控件
	 * @param defaultHeadImgID		默认显示的图片
	 */
	public void download(String url, ImageView imageView, int defaultHeadImgID) {
		this.defaultHeadImgID = defaultHeadImgID;
		// resetPurgeTimer();
		
		//根据url获取缓存位图
		Bitmap bitmap;
		try{
		   bitmap = getBitmapFromCache(url);
		}catch(Exception e){
		   bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.car_normal_low);
		}
		//如果位图为空或被内存释放，下载图片
		if(bitmap == null || bitmap.isRecycled()){
			forceDownload(url, imageView, defaultHeadImgID);
		}else{
			//如果有缓存，取消下载
			cancelPotentialDownload(url, imageView);
			//绑定图片到ImageView上
			imageView.setImageBitmap(bitmap);
			imageView.setVisibility(View.VISIBLE);
		}
	}
	
	public void download(String url, ImageView imageView, int defaultHeadImgID,String imgSource) {
		this.defaultHeadImgID = defaultHeadImgID;
		this.imgSource = imgSource;
		// resetPurgeTimer();
		Bitmap bitmap = getBitmapFromCache(url);
//		Log.e("", "imagedownloader.url.begin=" + url);
		if (bitmap == null || bitmap.isRecycled()) {
//			Log.e("", "oplain.hasnot=" + url);
			forceDownload(url, imageView, defaultHeadImgID);
		} else {
//			Log.e("", "oplain.has=" + url);
			cancelPotentialDownload(url, imageView);

			// imageView.setImageBitmap(ImageUtil.getRoundedCornerBitmap(bitmap));
			imageView.setImageBitmap(bitmap);

			// imageView.setImageBitmap(bitmap);
			imageView.setVisibility(View.VISIBLE);
		}
	}

	/*
	 * Same as download but the image is always downloaded and the cache is not
	 * used. Kept private at the moment as its interest is not clear. private
	 * void forceDownload(String url, ImageView view) { forceDownload(url, view,
	 * null); }
	 */

	/**
	 * Same as download but the image is always downloaded and the cache is not
	 * used. Kept private at the moment as its interest is not clear.
	 */
	// private void forceDownload(String url, ImageView imageView) {
	// // State sanity: url is guaranteed to never be null in
	// // DownloadedDrawable and cache keys.
	// if (url == null) {
	// imageView.setImageDrawable(null);
	// return;
	// }
	//
	// if (cancelPotentialDownload(url, imageView)) {
	// BitmapDownloaderTask task = new BitmapDownloaderTask(imageView);
	// DownloadedDrawable downloadedDrawable = new DownloadedDrawable(task);
	// imageView.setImageDrawable(downloadedDrawable);
	// imageView.setMinimumHeight(156);
	// task.execute(url);
	// }
	// }

	/**
	 * 强制下载图片
	 * @param url  要下载的图片的url值
	 * @param imageView 放置下载图片的控件
	 * @param defaultHeadImgID 
	 */
	private void forceDownload(String url, ImageView imageView,
			int defaultHeadImgID) {
		// State sanity: url is guaranteed to never be null in
		// DownloadedDrawable and cache keys.
		//如果没有图片，设置显示图片的地方不显示?不能不显示吧，至少也设置张图片
		if (url == null) {
			imageView.setImageDrawable(null);
			return;
		}
		//如果有取消下载操作
		if (cancelPotentialDownload(url, imageView)) {
			BitmapDownloaderTask task = new BitmapDownloaderTask(imageView,
					defaultHeadImgID);
			Drawable downloadedDrawable = new DownloadedDrawable(task);
			//设置取消后显示图片？
			imgSource = IMG_SOURCE_EXCEPT_CHAT_THUMB;
			imageView.setImageDrawable(downloadedDrawable);
			imageView.setMinimumHeight(156);
			
			task.execute(url);
		}
	}

	/**
	 * 取消下载
	 * 取消的判断：用户手动取消下载or此时没有下载任务，则返回true取消下载 / 如果下载了相同的url，则下载无法取消，返回false
	 * Returns true if the current download has been canceled or if there was no
	 * download in progress on this image view. Returns false if the download in
	 * progress deals with the same url. The download is not stopped in that case.
	 */
	private static boolean cancelPotentialDownload(String url,ImageView imageView) {
		//获取与传入的imageView绑定的、正在进行的下载任务
		BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
		//如果没有下载任务，再判断一下它的url,如果url也为空的话，就彻底取消下载任务
		if (bitmapDownloaderTask != null) {
			String bitmapUrl = bitmapDownloaderTask.url;
			if (bitmapUrl == null) {
				bitmapDownloaderTask.cancel(false);
			} else {
				// The same URL is already being downloaded.
				//有下载的url，不取消下载
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查是否有与此imageView绑定的下载任务，有任务则返回下载任务
	 * @param imageView
	 *            Any imageView
	 * @return Retrieve the currently active download task (if any) associated
	 *         with this imageView. null if there is no such task.
	 *         getBitmapDownloaderTask获取是否有绑定此imageView的下载任务
	 */
	private static BitmapDownloaderTask getBitmapDownloaderTask(ImageView imageView) {
		if (imageView != null) {
			Drawable drawable = null;

			drawable = imageView.getDrawable();

			// Drawable drawable = imageView.getDrawable();
//			Log.e("", "oplain.drawable.instance2="
//					+ (drawable instanceof DownloadedDrawable));
			if (drawable instanceof DownloadedDrawable) {
//				Log.e("", "oplain.drawable.instance");
				DownloadedDrawable downloadedDrawable = (DownloadedDrawable) drawable;
				return downloadedDrawable.getBitmapDownloaderTask();
			}
		}
		return null;
	}

	/**
	 * 下载图片
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap downloadBitmap(String url) {

		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 20000);
		HttpConnectionParams.setSoTimeout(httpParams, 30000);
		DefaultHttpClient client = new DefaultHttpClient(httpParams);
		// client.setCookieStore();
		// AndroidHttpClient is not allowed to be used from the main thread
		// final HttpClient client = AndroidHttpClient.newInstance("Android");
		final HttpGet getRequest = new HttpGet(url);

		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w("ImageDownloader", "Error " + statusCode
						+ " while retrieving bitmap from " + url + "|"
						+ statusCode);
				return null;
			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					Log.e("",
							"oplain.imagedownloader.inputStream="
									+ entity.getContentLength());
					// return BitmapFactory.decodeStream(inputStream);
					// Bug on slow connections, fixed in future release.
					return BitmapFactory.decodeStream(new FlushedInputStream(
							inputStream));
					// return zoomBitmap(new FlushedInputStream(inputStream),
					// 1);
				} catch (Exception e) {
					Log.e("", "oplain.imagedownloader.error");
					e.printStackTrace();
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (IOException e) {
			getRequest.abort();
			Log.w(LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
		} catch (IllegalStateException e) {
			getRequest.abort();
			Log.w(LOG_TAG, "Incorrect URL: " + url);
		} catch (Exception e) {
			getRequest.abort();
			Log.e("", "geturl.e=" + e.getMessage());
			Log.w(LOG_TAG, "Error while retrieving bitmap from " + url, e);
		} finally {
			// if ((client instanceof AndroidHttpClient)) {
			// ((AndroidHttpClient) client).close();
			// }
		}
		return null;
	}

	public static Bitmap downloadBitmap(String url, String dir) {

		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
		HttpConnectionParams.setSoTimeout(httpParams, 30000);
		DefaultHttpClient client = new DefaultHttpClient(httpParams);
		// client.setCookieStore(WeClubApplication.getInstance().getCookieStore());
		// AndroidHttpClient is not allowed to be used from the main thread
		// final HttpClient client = AndroidHttpClient.newInstance("Android");
		final HttpGet getRequest = new HttpGet(url);

		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w("ImageDownloader", "Error " + statusCode
						+ " while retrieving bitmap from " + url + "|"
						+ statusCode);
				return null;
			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					Log.e("",
							"oplain.imagedownloader.inputStream="
									+ entity.getContentLength());
					// return BitmapFactory.decodeStream(inputStream);
					// Bug on slow connections, fixed in future release.
					Bitmap bitmap = BitmapFactory
							.decodeStream(new FlushedInputStream(inputStream));

					if (bitmap != null)
						FileUtil.writeBitmapToFile(dir, bitmap);

					return bitmap;
					// return zoomBitmap(new FlushedInputStream(inputStream),
					// 1);
				} catch (Exception e) {
					Log.e("", "oplain.imagedownloader.error");
					e.printStackTrace();
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (IOException e) {
			getRequest.abort();
			Log.w(LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
		} catch (IllegalStateException e) {
			getRequest.abort();
			Log.w(LOG_TAG, "Incorrect URL: " + url);
		} catch (Exception e) {
			getRequest.abort();
			Log.e("", "geturl.e=" + e.getMessage());
			Log.w(LOG_TAG, "Error while retrieving bitmap from " + url, e);
		} finally {
			// if ((client instanceof AndroidHttpClient)) {
			// ((AndroidHttpClient) client).close();
			// }
		}
		return null;
	}

	public Bitmap downloadBitmapWithZoom(String url, int zoom) {
		final int IO_BUFFER_SIZE = 4 * 1024;
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
		HttpConnectionParams.setSoTimeout(httpParams, 30000);
		HttpClient client = new DefaultHttpClient(httpParams);
		// AndroidHttpClient is not allowed to be used from the main thread
		// final HttpClient client = AndroidHttpClient.newInstance("Android");
		final HttpGet getRequest = new HttpGet(url);

		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w("ImageDownloader", "Error " + statusCode
						+ " while retrieving bitmap from " + url);
				return null;
			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					// return BitmapFactory.decodeStream(inputStream);
					// Bug on slow connections, fixed in future release.
					// return BitmapFactory.decodeStream(new
					// FlushedInputStream(inputStream));

					return zoomBitmap(new FlushedInputStream(inputStream), zoom);
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (IOException e) {
			getRequest.abort();
			Log.w(LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
		} catch (IllegalStateException e) {
			getRequest.abort();
			Log.w(LOG_TAG, "Incorrect URL: " + url);
		} catch (Exception e) {
			getRequest.abort();
			Log.w(LOG_TAG, "Error while retrieving bitmap from " + url, e);
		} finally {
			// if ((client instanceof AndroidHttpClient)) {
			// ((AndroidHttpClient) client).close();
			// }
		}
		return null;
	}

	/*
	 * An InputStream that skips the exact number of bytes provided, unless it
	 * reaches EOF.
	 */
	static class FlushedInputStream extends FilterInputStream {
		public FlushedInputStream(InputStream inputStream) {
			super(inputStream);
		}

		@Override
		public long skip(long n) throws IOException {
			long totalBytesSkipped = 0L;
			while (totalBytesSkipped < n) {
				long bytesSkipped = in.skip(n - totalBytesSkipped);
				if (bytesSkipped == 0L) {
					int b = read();
					if (b < 0) {
						break; // we reached EOF
					} else {
						bytesSkipped = 1; // we read one byte
					}
				}
				totalBytesSkipped += bytesSkipped;
			}
			return totalBytesSkipped;
		}
	}

	/**
	 * The actual AsyncTask that will asynchronously download the image.
	 */
	public class BitmapDownloaderTask extends
			WeclubAsyncTask<String, Void, Bitmap> {
		private String url;
		private final WeakReference<ImageView> imageViewReference;
		private int defaultHeadImgID;

		public BitmapDownloaderTask(ImageView imageView, int _defaultHeadImgID) {
			imageViewReference = new WeakReference<ImageView>(imageView);
			defaultHeadImgID = _defaultHeadImgID;
		}

		/**
		 * Actual download method.
		 */
		@Override
		protected Bitmap doInBackground(String... params) {
			if (isCancelled()) {
				Log.e("", "oplain.doinback.iscancel");
				return null;
			}
			url = params[0];
			return downloadBitmap(url);
		}

		/**
		 * Once the image is downloaded, associates it to the imageView
		 */
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			// if (isCancelled()) {
			// bitmap = null;
			// }
			Log.e("", "oplain.imagedownloader.bitmap=" + (bitmap == null));
			if (bitmap != null) {
				addBitmapToCache(url, bitmap);
				Log.e("", "imagedownloader.url.suc=" + url);
			}
			// else {
			// addBitmapToCache(url, bitmap);
			// }
			// addBitmapToCache(url, bitmap);

			if (imageViewReference != null) {
				ImageView imageView = imageViewReference.get();

				BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
				// Change bitmap only if this process is still associated with
				// it
				// Or if we don't use any bitmap to task association
				// (NO_DOWNLOADED_DRAWABLE mode)
				if (this == bitmapDownloaderTask) {
					if (bitmap != null) {

						// imageView.setImageBitmap(ImageUtil
						// .getRoundedCornerBitmap(bitmap));
						imageView.setImageBitmap(bitmap);

						// imageView.setImageBitmap(bitmap);
					} else {

						bitmap = BitmapFactory.decodeResource(
								context.getResources(), defaultHeadImgID);
						imageView.setImageBitmap(bitmap);

					}
					imageView.setVisibility(View.VISIBLE);
				}
			}
		}
	}

	/**
	 * A fake Drawable that will be attached to the imageView while the download
	 * is in progress.
	 * 
	 * <p>
	 * Contains a reference to the actual download task, so that a download task
	 * can be stopped if a new binding is required, and makes sure that only the
	 * last started download process can bind its result, independently of the
	 * download finish order.
	 * </p>
	 */

	class DownloadedDrawable extends BitmapDrawable {
		private final WeakReference<BitmapDownloaderTask> bitmapDownloaderTaskReference;

		public DownloadedDrawable(BitmapDownloaderTask bitmapDownloaderTask) {
			super(BitmapFactory.decodeResource(context.getResources(),
					defaultHeadImgID));
			bitmapDownloaderTaskReference = new WeakReference<BitmapDownloaderTask>(
					bitmapDownloaderTask);
		}

		public BitmapDownloaderTask getBitmapDownloaderTask() {
			return bitmapDownloaderTaskReference.get();// get() 可以取得此 Reference
														// 的所指到的对象
		}
	}

	// static class DownloadedDrawable2 extends BitmapDrawable {
	// private final WeakReference<BitmapDownloaderTask>
	// bitmapDownloaderTaskReference;
	//
	// public DownloadedDrawable2(BitmapDownloaderTask bitmapDownloaderTask) {
	// super(BitmapFactory.decodeResource(context.getResources(),
	// R.drawable.male_blank_head));
	// bitmapDownloaderTaskReference = new WeakReference<BitmapDownloaderTask>(
	// bitmapDownloaderTask);
	// }
	//
	// public BitmapDownloaderTask getBitmapDownloaderTask() {
	// return bitmapDownloaderTaskReference.get();// get() 可以取得此 Reference
	// // 的所指到的对象
	// }
	// }

	/*
	 * Cache-related fields and methods.
	 * 
	 * We use a hard and a soft cache. A soft reference cache is too
	 * aggressively cleared by the Garbage Collector.
	 */

	private static final int HARD_CACHE_CAPACITY = 100;
	private static final int DELAY_BEFORE_PURGE = 10 * 1000; // in milliseconds

	/*
	 * 还有一个方法值得关注: protected boolean removeEldestEntry(Map.Entry eldest) {
	 * return false; } 当调用put(key,
	 * value)的时候，HashMap判断是否要自动增加map的size的作法是判断是否超过threshold，
	 * LinkedHashMap则进行了扩展，如果removeEldestEntry方法return
	 * false；（默认的实现），那么LinkedHashMap跟HashMap处理扩容的方式一致；如果removeEldestEntry返回
	 * true，那么LinkedHashMap会自动删掉最不常用的那个entry（也就是header线性表最前面的那个）。
	 * 
	 * 正如 LinkedHashMap的文档所说，LinkedHashMap简直就是为了实现LRU Cache(Least Recently
	 * Used)而编写的
	 * 。正因为如此，在oscache或者是ehcache都使用到了LinkedHashMap。（oscache中是否使用LRU是可以配置的）
	 */

	// Hard cache, with a fixed maximum capacity and a life duration
	// 按照访问的次序来排序的含义:当调用LinkedHashMap的get(key)或者put(key, value)时，碰巧key在map中被包含，
	// 那么LinkedHashMap会将key对象的entry放在线性结构的最后。
	// 按照插入顺序来排序的含义:调用get(key), 或者put(key, value)并不会对线性结构产生任何的影响。
	private static final HashMap<String, Bitmap> sHardBitmapCache = new LinkedHashMap<String, Bitmap>(
			HARD_CACHE_CAPACITY / 200, 0.75f, true) {// 这个true表明LinkedHashMap按照访问的次序来排序。
		@Override
		protected boolean removeEldestEntry(
				LinkedHashMap.Entry<String, Bitmap> eldest) {
			if (size() <= HARD_CACHE_CAPACITY) {
				// Entries push-out of hard reference cache are transferred to
				// soft reference cache
				// 当map的size大于10时，把最近不常用的key放到mSoftBitmapCache中，从而保证mHardBitmapCache的效率
				sSoftBitmapCache.put(eldest.getKey(),
						new SoftReference<Bitmap>(eldest.getValue()));
				return true;
			} else
				return false;
		}
	};

	// Soft cache for bitmaps kicked out of hard cache
	private final static ConcurrentHashMap<String, SoftReference<Bitmap>> sSoftBitmapCache = new ConcurrentHashMap<String, SoftReference<Bitmap>>(
			HARD_CACHE_CAPACITY / 200);
	private static final String TAG = null;

	private final Handler purgeHandler = new Handler();

	private final Runnable purger = new Runnable() {
		public void run() {
			clearCache();
		}
	};

	/**
	 * Adds this bitmap to the cache.
	 * 
	 * @param bitmap
	 *            The newly downloaded bitmap.
	 */
	public static void addBitmapToCache(String url, Bitmap bitmap) {
		if (bitmap != null) {
			synchronized (sHardBitmapCache) {
				sHardBitmapCache.put(url, bitmap);
				writeToSdcard(url, bitmap);
			}
		}
	}

	// 写入SDCard
	private static void writeToSdcard(String url, Bitmap bitmap) {
//		Log.e(LOG_TAG, "oplain.imagedownloader.url=" + url + "|" + imgSource);
		if (SystemUtil.isExitsSdcard()) {
			FileUtil.writeBitmapToFile(
					SystemUtil.getImgThumbnailDirExceptChat() + SystemUtil.ShortText(url)[0]
							+ ".png", bitmap);

		}
	}
	
	
	public static void writeToSdcardDD(String url, Bitmap bitmap){
		if (SystemUtil.isExitsSdcard()) {
			FileUtil.writeBitmapToFile(
					SystemUtil.getImgThumbnailDirExceptChat() + url, bitmap);

		}
	}

	/**
	 * 从SDCard中读取图片
	 * @param url
	 * @return
	 */
	private static Bitmap readFromSdcard(String url) {
		//如果sd卡存在，从指定的保存路径里面读取图片
		if (SystemUtil.isExitsSdcard()) {
			return FileUtil.getBitmapFromFile(SystemUtil.getImgThumbnailDirExceptChat()
					+ SystemUtil.ShortText(url)[0] + ".png", null);
		}
		return null;
	}

	public void clearCacheByKey(String url) {
		if (url != null) {
			synchronized (sHardBitmapCache) {
				sHardBitmapCache.remove(url);
			}
			sSoftBitmapCache.remove(url);

		}
	}

	public void clearMemoryCacheByKey(String url) {
		if (url != null) {
			synchronized (sHardBitmapCache) {
				Bitmap bit_delete = sHardBitmapCache.get(url);
				if (bit_delete != null) {
					bit_delete.recycle();
					Log.e("pic", "图片删除成功了");
				}
				sHardBitmapCache.remove(url);

			}
			SoftReference<Bitmap> ref_bitmap_delete = sSoftBitmapCache.get(url);
			if (ref_bitmap_delete != null) {
				ref_bitmap_delete.clear();
				ref_bitmap_delete = null;

			}
			sSoftBitmapCache.remove(url);
			// System.gc();

		}
	}

	/**
	 * 根据传入的url获取缓存图片
	 * @param url		根据此url到缓存中检索图片
	 *            The URL of the image that will be retrieved from the cache.
	 * @return The cached bitmap or null if it was not found.
	 */
	public static Bitmap getBitmapFromCache(String url) {
		// First try the hard reference cache
		synchronized (sHardBitmapCache) {
			//先从硬存中检索图片，检索到就返回，检索不到就去软引用缓存中检索
			final Bitmap bitmap = sHardBitmapCache.get(url);
			if (bitmap != null && !bitmap.isRecycled()) {
				// Bitmap found in hard cache
				// Move element to first position, so that it is removed last
				// 如果找到的话，把元素移到linkedhashmap的最前面，从而保证在LRU算法中是最后被删除
				// HashMap<String, Bitmap> sHardBitmapCache
//				sHardBitmapCache.remove(url);
				sHardBitmapCache.put(url, bitmap);
				return bitmap;
			}
		}

		// Then try the soft reference cache
		//找到就返回图片，找不到则先将软引用去除，再从sd卡中读取
		SoftReference<Bitmap> bitmapReference = sSoftBitmapCache.get(url);
		if (bitmapReference != null) {
			final Bitmap bitmap = bitmapReference.get();
			if (bitmap != null && !bitmap.isRecycled()) {
				// Bitmap found in soft cache
				return bitmap;
			} else {
				// Soft reference has been Garbage Collected
				sSoftBitmapCache.remove(url);
			}
		}
		
		//如果硬存软存都没有缓存图片，最后去sd卡里找一次，sd卡也没找到就返回null
		return readFromSdcard(url);
	}

	/**
	 * Clears the image cache used internally to improve performance. Note that
	 * for memory efficiency reasons, the cache will automatically be cleared
	 * after a certain inactivity delay.
	 */
	public void clearCache() {
		Log.e("oplain", "oplain.clearCache");
		sHardBitmapCache.clear();
		sSoftBitmapCache.clear();
	}

	public void clearCacheMemory() {
		Log.e("oplain", "oplain.clearCache");

		for (Bitmap bit_map : sHardBitmapCache.values()) {
			bit_map.recycle();
			Log.e("pic", "图片删除成功了");
		}
		for (Reference<Bitmap> bit_ref : sSoftBitmapCache.values()) {
			bit_ref.clear();
			Log.e("pic", "软引用图片删除成功了");
		}
		sHardBitmapCache.clear();
		sSoftBitmapCache.clear();
		// System.gc();

	}

	public static void cleanInternalCache(Context context) {
		File directory = context.getCacheDir();
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				item.delete();
			}
		}

	}

	/**
	 * Allow a new delay before the automatic cache clear is done.
	 */
	private void resetPurgeTimer() {
		purgeHandler.removeCallbacks(purger);
		purgeHandler.postDelayed(purger, DELAY_BEFORE_PURGE);
	}

	// 为避免OOM,先将图片缩小再放大
	private Bitmap zoomBitmap(InputStream is, int zoom) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;// 只进行大小判断
		byte[] data = null;
		try {
			data = getBytes(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BitmapFactory.decodeByteArray(data, 0, data.length, options);
		// Bitmap ok = BitmapFactory.decodeStream(is, null, options);
		int mWidth = options.outWidth;
		int mHeight = options.outHeight;
		int s = 1;
		// 缩放的标准是按照屏幕的2倍来进行缩放
		while ((mWidth / s > screenWidth * 2 * zoom)
				|| (mHeight / s > screenHeight * 2 * zoom)) {
			s *= 2;
		}
		options = new BitmapFactory.Options();
		options.inPreferredConfig = Config.ARGB_8888;
		options.inSampleSize = s;
		// Bitmap bm = BitmapFactory.decodeStream(is, null, options);
		Bitmap bm = BitmapFactory
				.decodeByteArray(data, 0, data.length, options);
		if (bm != null) {
			int h = bm.getHeight();// 缩小后的尺寸，宽小于screenWidth * 2 * zoom
			int w = bm.getWidth();// 缩小后的尺寸，高小于screenHeight * 2 * zoom

			float ft = (float) ((float) w / (float) h);
			float fs = (float) ((float) screenWidth / (float) screenHeight);

			int neww = ft >= fs ? screenWidth * zoom : (int) (screenHeight
					* zoom * ft);
			int newh = ft >= fs ? (int) (screenWidth * zoom / ft)
					: screenHeight * zoom;

			float scaleWidth = ((float) neww) / w;
			float scaleHeight = ((float) newh) / h;

			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeight);
			bm = Bitmap.createBitmap(bm, 0, 0, w, h, matrix, true);
			// bm = BitmapFactory.decodeByteArray(data, 0, data.length,
			// options);

			return bm;
		}
		return null;

	}

	public Bitmap resizeThumbFullWidth(Bitmap bitmap) {
		if (bitmap == null) {
			return BitmapFactory.decodeResource(context.getResources(),
					defaultHeadImgID);
		}
		// int screenWidth =
		// context.getResources().getDisplayMetrics().widthPixels;
		// // 等比例放大到图片宽等于屏幕宽
		// float imgWidth = screenWidth;
		// float imgHeight = ((float) screenWidth / bitmap.getWidth())
		// * bitmap.getHeight();
		// Matrix matrix = new Matrix();
		// matrix.postScale(imgWidth, imgHeight);
		// return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
		// bitmap.getHeight(), matrix, true);

		int bmpWidth = bitmap.getWidth();
		int bmpHeight = bitmap.getHeight();
		/* 计算出这次要放大的比例 */
		float scaleWidth = 2f;
		float scaleHeight = 2f;
		/* 产生reSize后的Bitmap对象 */
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix,
				true);
	}

	private byte[] getBytes(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = is.read(b, 0, 1024)) != -1) {
			baos.write(b, 0, len);
			baos.flush();
		}
		byte[] bytes = baos.toByteArray();
		baos.close();
		return bytes;
	}

	public static Bitmap getImage(String Url) throws Exception {

		try {

			URL url = new URL(Url);

			String responseCode = url.openConnection().getHeaderField(0);

			if (responseCode.indexOf("200") < 0)

				throw new Exception("图片文件不存在或路径错误，错误代码：" + responseCode);

			return BitmapFactory.decodeStream(url.openStream());

		} catch (IOException e) {

			// TODO Auto-generated catch block

			throw new Exception(e.getMessage());

		}
	}

	public static byte[] getImageByte(String path) {
		Log.i(TAG, "111111111111");
		URL url;
		byte[] b = null;
		Log.i(TAG, "2222222222222222");
		try {
			Log.i(TAG, "333333333333333");
			url = new URL(path);
			HttpURLConnection con;
			con = (HttpURLConnection) url.openConnection();
			Log.i(TAG, "4444444444444444444");
			con.setRequestMethod("GET");
			con.setConnectTimeout(5000);
			Log.i(TAG, "5555555555555555");
			InputStream in = con.getInputStream();
			Log.i(TAG, "66666666666666666");
			b = readInputStream(in);
			Log.i(TAG, "77777777777777777");
		} catch (Exception e) {
			Log.i(TAG, "88888888888888888");
			e.printStackTrace();
		}
		Log.i(TAG, "lenth====================" + b.length + "");
		return b;

	}

	public static byte[] readInputStream(InputStream in) throws Exception {
		Log.i(TAG, "999999999999999999999999999999999999999999999999");
		int len = 0;
		byte buf[] = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while ((len = in.read(buf)) != -1) {
			out.write(buf, 0, len); // 写入内存
		}
		out.close();
		return out.toByteArray();
	}

	/**
	 * 生成一张带logo的二维码名片
	 * 
	 * @param str
	 * @param mBitmap
	 * @return
	 * @throws WriterException
	 */
	// public static Bitmap cretaeBitmapWithPic(String str, Bitmap mBitmap)
	// throws WriterException {
	// BitMatrix matrix = new MultiFormatWriter().encode(str,
	// BarcodeFormat.QR_CODE, 300, 300);
	// int width = matrix.getWidth();
	// int height = matrix.getHeight();
	// // 二维矩阵转为一维像素数组,也就是一直横着排了
	// int halfW = width / 2;
	// int halfH = height / 2;
	// int[] pixels = new int[width * height];
	// // 首先把图片设置为白底，否则保存到本地使用手机图库查看看不到二维码
	// for (int i = 0; i < pixels.length; i++) {
	// pixels[i] = 0xffffffff;
	// }
	// for (int y = 0; y < height; y++) {
	// for (int x = 0; x < width; x++) {
	// if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
	// && y > halfH - IMAGE_HALFWIDTH
	// && y < halfH + IMAGE_HALFWIDTH) {
	// pixels[y * width + x] = mBitmap.getPixel(x - halfW
	// + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
	// } else {
	// if (matrix.get(x, y)) {
	// pixels[y * width + x] = 0xff000000;
	// }
	// }
	// }
	// }
	// Bitmap bitmap = Bitmap.createBitmap(width, height,
	// Bitmap.Config.ARGB_8888);
	// // 通过像素数组生成bitmap,具体参考api
	// bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
	// byte[] byte1 = SystemUtil.getBitmapByte(bitmap);
	// SystemUtil.saveBitmap(byte1);
	// return bitmap;
	// }

	public void initWeclubAsyncTask() {
		new BitmapDownloaderTask(new ImageView(context), R.drawable.car_normal_low);
	}

}
