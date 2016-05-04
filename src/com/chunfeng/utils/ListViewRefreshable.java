/**
 * 
 */
package com.chunfeng.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.test.R;

/**
 * @author Cfrjkj
 * @date 2016-5-4
 * @time 下午3:22:26
 * @todo 自定义的可刷新的ListView
 */
public class ListViewRefreshable extends ListView {

	
	private View tailView;
	private LinearLayout headView;
	private LinearLayout headContainer;
	private int heightOfHead;
	private int heightOfTail;
	private float downX;
	private float downY = -1;
	private float moveY = -1;
	private View lunbotu;
	
	private int listViewPosY;

	private final int STATE_PULLDOWN = 0;	//下拉刷新
	private final int STATE_RELEASE = 1;	//松开刷新
	private final int STATE_REFRESHING = 2;	//正在刷新
	private int currentState = -1;			//当前状态
	private TextView textViewState;
	private TextView textViewTime;
	private ImageView ivArrow;
	private ProgressBar pbRefresh;
	private RotateAnimation upRAnimation;
	private RotateAnimation downRAnimation;
	
	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ListViewRefreshable(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
		initAnimation();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ListViewRefreshable(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * @param context
	 */
	public ListViewRefreshable(Context context) {
		this(context, null);
	}
	
	private void initView(){
		initHead();
		initTail();
	}
	
	/**
	 * 特殊需求
	 * 加载轮播图view
	 */
	public void myAddHeadView(View view){
		lunbotu = view;
		headContainer.addView(view);
	}
	
	/**
	 * 初始化尾部组件
	 */
	@SuppressLint("NewApi")
	private void initTail(){
		//尾部加到listview中
		tailView = View.inflate(getContext(), R.layout.listview_refresh_tail, null);
//		ViewUtils.inject(getContext(), tailView);
		tailView.measure(0, 0);
		heightOfTail = tailView.getMeasuredHeight();
		tailView.setPadding(0, 0, 0, -heightOfTail);
//		this.setPadding(0, topPadding, 0, bottomPadding);
		this.addFooterView(tailView);
	}
	
	/**
	 * 初始化头部组件
	 */
	@SuppressLint("NewApi")
	private void initHead(){
		//头部加到listview中
		//头部的容器
		headContainer = (LinearLayout) View.inflate(getContext(), R.layout.listview_refresh_head_container, null);
		//从容器中拿到headview
		headView = (LinearLayout) headContainer.findViewById(R.id.ll_listview_head_root);
		//这一行可以没有
//		ViewUtils.inject(getContext(), headView);	
		
		//拿到需要控制的组件
		textViewState = (TextView) headView.findViewById(R.id.tv_listview_head_state);
		textViewTime = (TextView) headView.findViewById(R.id.tv_listview_head_time);
		ivArrow = (ImageView) headView.findViewById(R.id.iv_listview_head_arr);
		pbRefresh = (ProgressBar) headView.findViewById(R.id.pb_listview_head_loading);
		
		headView.measure(0, 0);
		heightOfHead = headView.getMeasuredHeight();
		headView.setPadding(0, -heightOfHead, 0, 0);
//		this.setPadding(0, topPadding, 0, bottomPadding);

		this.addHeaderView(headContainer);
	}

	/**
	 * 判断轮播图是否完全显示
	 * @return
	 */
	private boolean isLunboFullShow(){
		
		if(listViewPosY == 0) {
			int[] location1 = new int[2];
			this.getLocationOnScreen(location1);
			listViewPosY = location1[1];
		}
		//如果轮播图没有完全显示, 不响应自己的事件,由父组件处理事件
		//1.取出listview的坐标和轮播图的坐标
		//2.判断两个坐标大小
		
		int[] location2 = new int[2];
		lunbotu.getLocationOnScreen(location2);
		
		if(location2[1] < listViewPosY){
			//轮播图没有完全显示
			return false;
		}
		return true;
	}
	
	/**
	 * 滑动listview的逻辑处理
	 */
	private void listViewMoveLogic(){
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//自己的事件处理, 用来实现下拉刷新, 上拉刷新
		//需要屏蔽父类的touch事件
		//当下拉拖动, 并且显示listview中第一条view的时候
		to: switch(ev.getAction()){
		case MotionEvent.ACTION_DOWN:
			downY = ev.getY();
//			downY = ev.getY();
			break;
		case MotionEvent.ACTION_UP:
			downY = -1;
			moveY = -1;
			headView.setPadding(0, -heightOfHead, 0, 0);
			break;
		case MotionEvent.ACTION_CANCEL:
			downY = -1;
			moveY = -1;
			break;
		case MotionEvent.ACTION_MOVE:
			
			if(!isLunboFullShow()){
				//轮播没有完全显示
				break;
			}
			
			//防止按下时没有获取到坐标
			if(downY == -1){
				downY = ev.getY();
			}
			moveY = ev.getY();
			float dy = moveY - downY;
			if(dy >= 0 && this.getFirstVisiblePosition() == 0){
				if(dy <= heightOfHead && currentState != STATE_PULLDOWN){
					currentState = STATE_PULLDOWN;	//加上状态判断后, 状态变化只执行一次,方便对view的操作更新
					refreshRefreshState();
				}else if(dy > heightOfHead && currentState != STATE_RELEASE){
					currentState = STATE_RELEASE;
					refreshRefreshState();
				}else{
					
				}
				headView.setPadding(0, (int) (dy - heightOfHead), 0, 0);
				return true;
			}else{
				System.out.println("往上拉或者第一条内容没有显示");
			}
			break;
		}
		
		System.out.println("由listview处理事件");
		return super.onTouchEvent(ev);
	}

	/**
	 * 刷新状态切换
	 */
	private void refreshRefreshState() {
		switch (currentState) {
		case STATE_PULLDOWN:
			System.out.println("下拉刷新");
			textViewState.setText("下拉刷新");
			ivArrow.startAnimation(downRAnimation);
			break;
		case STATE_RELEASE:
			textViewState.setText("松开刷新");
			System.out.println("不能拉出太多,松开刷新");
			ivArrow.startAnimation(upRAnimation);
			break;
		case STATE_REFRESHING:
			break;
		default:
			break;
		}
	}
	
	private void initAnimation(){
		upRAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		upRAnimation.setDuration(500);
		upRAnimation.setFillAfter(true);

		downRAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		downRAnimation.setDuration(500);
		downRAnimation.setFillAfter(true);
	}
}



