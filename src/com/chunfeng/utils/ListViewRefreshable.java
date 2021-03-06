/**
 * 
 */
package com.chunfeng.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
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

	private boolean isEnablePullRefresh;
	
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
	
	private OnRefreshDataListener refreshListener;

	private boolean isLoadingMore;
	
	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ListViewRefreshable(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
		initAnimation();
		initEvent();
	}

	/**
	 * 
	 */
	private void initEvent() {
		this.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				//状态停止, 如果listview显示最后一条, 加载更多数据提示
				//是否最后一条数据显示
				System.out.println("getLastVisiblePosition(),getAdapter().getCount() = " + getLastVisiblePosition() + ", " + getAdapter().getCount());
				System.out.println("isLoadingMore = " + isLoadingMore);
				if(getLastVisiblePosition() == getAdapter().getCount() - 1 && !isLoadingMore){
					
					System.out.println("拉到最下面, 开始获取更多数据");
					//显示最后一条数据
					tailView.setPadding(0, 0, 0, 0);
					setSelection(getAdapter().getCount());		//什么作用???
					
					if(refreshListener != null){
						isLoadingMore = true;
						refreshListener.loadingMore();
					}
				}
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				
			}
		});
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
	 * 自己选择是否使用下拉刷新功能 
	 */
	public void setIsRefreshAble(boolean isRefreshable){
		isEnablePullRefresh = isRefreshable;
	}
	
	/**
	 * 自己选择是否使用下拉刷新功能 
	 */
	public void setIsEnableRefreshHead(boolean isRefreshable){
		isEnablePullRefresh = isRefreshable;
	}
	/**
	 * 自己选择是否使用下拉刷新功能 
	 */
	public void setIsEnableRefreshMore(boolean isRefreshable){
		isEnablePullRefresh = isRefreshable;
	}
	
	/**
	 * 特殊需求
	 * 加载轮播图view
	 */
	public void addHeaderView(View view){
		//判断, 如果使用下拉刷新,把布局头加到下拉刷新的容器中,否则加载到原生ListView中
		if(isEnablePullRefresh){
			lunbotu = view;
			headContainer.addView(view);
		}else{
			//调用父类的函数, 原生功能
			super.addHeaderView(view);
		}
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
			if(currentState == STATE_PULLDOWN){
				headView.setPadding(0, -heightOfHead, 0, 0);
			}else if(currentState == STATE_RELEASE){
				headView.setPadding(0, 0, 0, 0);
				currentState = STATE_REFRESHING;
				refreshRefreshState();	//刷新界面
				if(this.refreshListener != null){
					this.refreshListener.refreshData();
				}
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			downY = -1;
			moveY = -1;
			break;
		case MotionEvent.ACTION_MOVE:
			if(!isEnablePullRefresh){
				//判断是否激活了下拉刷新
				break;
			}
			if(currentState == STATE_REFRESHING){
				//判断是否处于正在刷新的状态
				break;
			}
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
		
		return super.onTouchEvent(ev);
	}

	/**
	 * 刷新数据成功
	 */
	public void refreshStateFinish(){
		if(isLoadingMore){
			//加载更多
			isLoadingMore = false;
			tailView.setPadding(0, 0, 0, -heightOfTail);
		}else{
			//下拉刷新
			System.out.println("refreshStateFinish 刷新结束");
			//改变页面状态
			currentState = STATE_PULLDOWN;
			textViewState.setText("下拉刷新");
			ivArrow.setVisibility(View.VISIBLE);
			pbRefresh.setVisibility(View.INVISIBLE);
			textViewTime.setText(getCurrentFromDate());
			headView.setPadding(0, -heightOfHead, 0, 0);
		}
	}
	
	/**
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	private CharSequence getCurrentFromDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		return format.format(new Date());
	}

	/*
	 * 刷新状态切换
	 */
	private void refreshRefreshState() {
		switch (currentState) {
		case STATE_PULLDOWN:
			System.out.println("下拉刷新");
			textViewState.setText("下拉刷新");
			ivArrow.startAnimation(downRAnimation);
			ivArrow.setVisibility(View.VISIBLE);
			pbRefresh.setVisibility(View.GONE);
			break;
		case STATE_RELEASE:
			textViewState.setText("松开刷新");
			System.out.println("不能拉出太多,松开刷新");
			ivArrow.startAnimation(upRAnimation);
			ivArrow.setVisibility(View.VISIBLE);
			pbRefresh.setVisibility(View.GONE);
			break;
		case STATE_REFRESHING:
			System.out.println("正在刷新");
			ivArrow.clearAnimation();
			ivArrow.setVisibility(View.GONE);
			pbRefresh.setVisibility(View.VISIBLE);
			textViewState.setText("正在刷新");
			break;
		default:
			System.out.println("refreshRefreshState 其他情况");
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
	
	/**
	 * 刷新数据的监听器, 需要刷新数据的类中实现这个接口, 就可以调用这里的数据刷新方法
	 * @author Cfrjkj
	 * @date 2016-5-4
	 * @time 下午10:28:12
	 * @todo TODO
	 */
	public interface OnRefreshDataListener{
		void refreshData();
		void loadingMore();
	}
	
	public void setOnRefreshDataListener(OnRefreshDataListener listener){
		this.refreshListener = listener;
	}
}



