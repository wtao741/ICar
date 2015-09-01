package com.icar.datatimewheel;

import java.util.Calendar;

import com.icar.activity.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;


/**
 * 性别
 * 
 * @author he xiaohui
 * 
 *         2015-2-8 下午4:06:12
 */
@SuppressLint("ViewConstructor")
public class DatePopupWindow extends PopupWindow
{

	private View mMenuView;

	private WheelView year_view, month_view, day_view;

	private NumericWheelAdapter yearAdapter, monthAdapter, dayAdapter;

	OnDateSelectListener selectListener;

	String title;

	public DatePopupWindow(Activity context, String title,
			OnDateSelectListener selectListener)
	{
		super(context);
		this.title = title;
		this.selectListener = selectListener;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.dateselect_dialog, null);
		year_view = (WheelView) mMenuView.findViewById(R.id.year_view);
		month_view = (WheelView) mMenuView.findViewById(R.id.month_view);
		day_view = (WheelView) mMenuView.findViewById(R.id.day_view);
		TextView title_textview = (TextView) mMenuView
				.findViewById(R.id.title_textview);
		title_textview.setText(title);

		initListener();
		initView();

		// 设置按钮监听
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener()
		{

			public boolean onTouch(View v, MotionEvent event)
			{

				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP)
				{
					if (y < height)
					{
						dismiss();
					}
				}
				return true;
			}
		});
	}

	private void initView()
	{
		int curYears = 0;
		int curMonth = 0;
		int day = 0;
		Calendar c = Calendar.getInstance();
		curYears = c.get(Calendar.YEAR);
		curMonth = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		yearAdapter = new NumericWheelAdapter(curYears - 50, curYears + 10);
		year_view.setAdapter(yearAdapter);
		year_view.setLabel("年");

		monthAdapter = new NumericWheelAdapter(1, 12);
		month_view.setAdapter(monthAdapter);
		month_view.setLabel("月");
		month_view.setCyclic(true);

		dayAdapter = new NumericWheelAdapter(1,
				c.getActualMaximum(Calendar.DAY_OF_MONTH));
		day_view.setAdapter(dayAdapter);
		day_view.setLabel("日");
		day_view.setCyclic(true);

		year_view.setCurrentYearItem(curYears);
		month_view.setCurrentItem(curMonth);
		day_view.setCurrentItem(day - 1);
	}

	protected void initListener()
	{
		year_view.addChangingListener(new OnWheelChangedListener()
		{

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue)
			{
				refreshDay(newValue, month_view.getCurrentItem());
			}
		});
		month_view.addChangingListener(new OnWheelChangedListener()
		{

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue)
			{
				refreshDay(year_view.getCurrentItem(), newValue);
			}
		});
		View close_button = mMenuView.findViewById(R.id.close_button);
		close_button.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				dismiss();
			}
		});
		View ok_button = mMenuView.findViewById(R.id.ok_button);
		ok_button.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				String value = changeTime(year_view.getCurrentYearValue())
						+ "-" + changeTime(month_view.getCurrentValue()) + "-"
						+ changeTime(day_view.getCurrentValue());
				if (null != selectListener)
				{
					selectListener.onDateSelect(value);
				}
				dismiss();
			}
		});
	}

	public static String changeTime(int value)
	{
		return value < 10 ? ("0" + value) : (value + "");
	}

	private void refreshDay(int year, int month)
	{
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		dayAdapter.setMaxValue(c.getActualMaximum(Calendar.DAY_OF_MONTH));
		day_view.setAdapter(dayAdapter);
	}

	public void showWindow(View view)
	{
		showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
	}

	public interface OnDateSelectListener
	{
		public void onDateSelect(String value);
	}
}
