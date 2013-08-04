package com.hotpot.activity;

import com.hotpot.util.AppManager;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * 2013-8-3 下午4:38:45
 * @author:zhangzhi
 * @e-mail:crazyhelloworld@163.com
 * TODO:
 * 
 */
public class BaseActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		AppManager.getAppManager().addActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}
}
