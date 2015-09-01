package com.icar.base;

import com.icar.activity.R;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;

import android.app.Activity;  
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.view.ViewGroup.LayoutParams;  
import android.widget.Button;
import android.widget.FrameLayout;  
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;  
import android.widget.Toast;  
    
public class AbstractTitleActivity extends Activity implements OnClickListener {  
  
    private TextView head_title;  
    private LinearLayout head_left;  
    private Button head_right;  
    private FrameLayout mContentLayout;  
    private ImageView head_collect; 
    private static Toast toast = null;

    private HeadClick headClick;
    
    private HttpUtil http;
    
    public void setHeadClick(HeadClick mHeadClick){
    	if(headClick == null){
    		headClick = mHeadClick;
    	}
    }
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        initViews();  
        ViewUtils.inject(this);
        http = new HttpUtil(this);
    }  
  
    private void initViews() {  
        super.setContentView(R.layout.activity_abstract_title);  
        head_title = (TextView) findViewById(R.id.head_title);  
        mContentLayout = (FrameLayout) findViewById(R.id.layout_content);  
        head_left = (LinearLayout) findViewById(R.id.head_left);  
        head_right = (Button) findViewById(R.id.head_right);  
        head_collect = (ImageView) findViewById(R.id.head_collect);
        head_left.setOnClickListener(this);  
        head_right.setOnClickListener(this);  
    }  
      
      
    /** 
     * �Ƿ���ʾ���ذ�ť 
     * @param backwardResid  ���� 
     * @param show true����ʾ 
     */  
    public void isShowLeftView(boolean show) {  
        if (head_left != null) {  
            if (show) {  
                head_left.setVisibility(View.VISIBLE);  
            } else {  
                head_left.setVisibility(View.GONE);  
            }  
        } // else ignored  
    }  
    
    /** 
     * �Ƿ���ʾ���ذ�ť 
     * @param backwardResid  ���� 
     * @param show true����ʾ 
     */  
    public void isShowRightView(int rightResid, boolean show) {  
        if (head_title != null) {  
            if (show) {  
                head_right.setText(getResources().getString(rightResid));  
                head_right.setVisibility(View.VISIBLE);  
            } else {  
                head_right.setVisibility(View.GONE);  
            }  
        } // else ignored  
    }  
      
    public void isShowCollect(boolean show){
    	if(show){
    		head_collect.setVisibility(View.VISIBLE);
    	}else{
    		head_collect.setVisibility(View.GONE);
    	}
    }
    
     /**  
     * ���ذ�ť����󴥷�  
     * @param backwardView  
     */    
    protected void onLeftBackward(View backwardView) {    
        //Toast.makeText(this, "������أ����ڴ˴�����finish()", Toast.LENGTH_LONG).show();    
        //finish();    
    }    
    
    /**  
     * �ύ��ť����󴥷�  
     * @param forwardView  
     */    
    protected void onRightForward(View forwardView) {    
        Toast.makeText(this, "����ύ", Toast.LENGTH_LONG).show();    
    }    
      
    //���ñ�������    
    @Override    
    public void setTitle(int titleId) {    
        head_title.setText(titleId);    
    }    
    
    //���ñ�������    
    @Override    
    public void setTitle(CharSequence title) {    
        head_title.setText(title);    
    }    
    
    //���ñ���������ɫ    
    @Override    
    public void setTitleColor(int textColor) {    
        head_title.setTextColor(textColor);    
    }    
      
  //���ñ���������ɫ    
    public void setRightTextColor(int textColor) {    
        head_right.setTextColor(getResources().getColor(textColor));    
    }  
    
    public void setRightText(String str){
    	head_right.setText(str);
    }
    
    public void setRightBackgorund(int des){
    	head_right.setBackgroundResource(des);
    }
    
    //ȡ��FrameLayout�����ø���removeAllViews()����    
    @Override    
    public void setContentView(int layoutResID) {    
        mContentLayout.removeAllViews();    
        View.inflate(this, layoutResID, mContentLayout);    
        onContentChanged();    
    }    
      
    @Override   
    public void setContentView(View view){  
        mContentLayout.removeAllViews();    
        mContentLayout.addView(view);    
        onContentChanged();  
    }  
      
    @Override   
    public void setContentView(View view, LayoutParams params){  
        mContentLayout.removeAllViews();    
        mContentLayout.addView(view, params);    
        onContentChanged();  
    }  
      
  
    @Override  
    public void onClick(View v) {  
        // TODO Auto-generated method stub  
         switch (v.getId()) {    
         case R.id.head_left:    
             finish();
             break;    
         case R.id.head_right:    
             headClick.right();  
             break;    
         default:    
             break;    
     }    
    }  
    
    protected void showShortToast(int pResId) {
		showShortToast(getString(pResId));
	}

	protected void showLongToast(int pResId) {
		showLongToast(getString(pResId));
	}

	protected void showShortToast(String pMsg) {
		if (toast == null)
			toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setText(pMsg);
		toast.show();
	}

	protected void showLongToast(String pMsg) {
		if (toast == null)
			toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setText(pMsg);
		toast.show();
	}

	/******************************** ��Activity Skip Event�� ***************************************/
	protected void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		openActivity(pClass, pBundle, null);
	}

	protected void openActivity(Class<?> pClass, Bundle pBundle, Uri uri) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		if (uri != null) {
			intent.setData(uri);
		}
		startActivity(intent);
	}

	protected void openActivity(String pAction) {
		openActivity(pAction, null);
	}

	protected void openActivity(String pAction, Bundle pBundle) {
		openActivity(pAction, pBundle, null);
	}

	protected void openActivity(String pAction, Bundle pBundle, Uri uri) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		if (uri != null) {
			intent.setData(uri);
		}
		startActivity(intent);
	}

	protected Intent getPrevIntent() {
		return getIntent();
	}

	protected Bundle getPrevExtras() {
		return getPrevIntent().getExtras();
	}
}  
