package com.chunfeng.zhjz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.chunfeng.utils.MyConstants;
import com.chunfeng.utils.SPTools;
import com.example.test.R;

public class LogoActivity extends Activity {

	private ImageView iv_logoView;
	private AnimationSet as;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        initView();
        startAnimation();
        initEvent();
	}


    private void initEvent() {
		// 如果只有一处用到该事件, 可以直接用匿名类来处理
    	// 如果多出用到. 可以声明称成员变量
    	as.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
				System.out.println("动画开始播放");
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				System.out.println("动画重复播放1次");
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				System.out.println("动画播放完毕");
				if(SPTools.getBoolean(LogoActivity.this, MyConstants.IS_SETUP, false)){
					System.out.println("如果已经设置过, 直接进入主界面");
					Intent intent = new Intent(LogoActivity.this,MainActivity.class);
					LogoActivity.this.startActivity(intent);
//					Intent intent = new Intent(LogoActivity.this,GuideActivity.class);
//					LogoActivity.this.startActivity(intent);
				}else{
					System.out.println("如果没有设置过, 进入向导界面");
					Intent intent = new Intent(LogoActivity.this,GuideActivity.class);
					LogoActivity.this.startActivity(intent);
				}
				LogoActivity.this.finish();
			}
		});
	}


	private void startAnimation() {
		as = new AnimationSet(false);
    	int timeLength = 500;
		RotateAnimation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		ra.setDuration(timeLength);
		ra.setFillAfter(true); //动画播放完毕后停留在最后状态
		
		ScaleAnimation sa = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		sa.setDuration(timeLength);
		sa.setFillAfter(true);
		
		AlphaAnimation aa = new AlphaAnimation(0, 1);
		aa.setDuration(timeLength);
		aa.setFillAfter(true);
		
		as.addAnimation(ra);
		as.addAnimation(sa);
		as.addAnimation(aa);
		
		iv_logoView.startAnimation(as);
//		as.start();
		
	}


	private void initView() {
		// TODO Auto-generated method stub
		View view = View.inflate(this, R.layout.activity_logo, null);
		this.setContentView(view);
//		setContentView(R.layout.activity_logo);
		iv_logoView = (ImageView)findViewById(R.id.imageViewLogo);
		
    }


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}
