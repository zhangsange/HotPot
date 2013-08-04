package com.hotpot.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;

import com.hotpot.R;
import com.hotpot.Globle;

public class UtilsHelper {

	/** 格式化时间 */
	public static String getStringDate(long time) {
		Date currentTime = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 读取屏幕宽
	 */
	public static int getScreenWidth(Activity context) {
		DisplayMetrics metrics = new DisplayMetrics();
		Display display = context.getWindowManager().getDefaultDisplay();
		display.getMetrics(metrics);

		return metrics.widthPixels;
	}

	/**
	 * 读取屏幕高
	 */
	public static int getScreenHeight(Activity context) {
		DisplayMetrics metrics = new DisplayMetrics();
		Display display = context.getWindowManager().getDefaultDisplay();
		display.getMetrics(metrics);
		return metrics.heightPixels;
	}

	/**
	 * 获得手机号
	 * */
	public static String GetPhoneNum(Context context) {
		String phoneNum = null;
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		phoneNum = tm.getLine1Number();
		if (!TextUtils.isEmpty(phoneNum)) {
			if (phoneNum.length() > 11) {
				phoneNum = phoneNum.substring(phoneNum.length() - 11,
						phoneNum.length());
			}
		}
		return phoneNum;
	}

	/**
	 * 是否是第一次安装或者执行了清除数据操作
	 * */
	public static boolean IS_FRISTORCLEANDATA(Context context) {
		SharedPreferences sp = context.getSharedPreferences("anshang",
				Context.MODE_PRIVATE);
		boolean isFrist = sp.getBoolean("isfirst", true);
		if (isFrist) {
			Editor editor = sp.edit();
			editor.putBoolean("isfirst", false);
			editor.commit();
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 存储手机号到配置
	 * */
	public static void savePhoneNumToConfig(Context context, String phoneNum) {
		SharedPreferences sp = context.getSharedPreferences("anshang",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("PHONENUM", phoneNum);
		editor.commit();
	}

	/**
	 * 读取配置中保存的手机号
	 * */
	public static String readPhoneNumFromConfig(Context context) {
		SharedPreferences sp = context.getSharedPreferences("anshang",
				Context.MODE_PRIVATE);
		String pn = sp.getString("PHONENUM", "");
		return pn;
	}

	/**
	 * 格式化数字
	 * */
	public static String Format_Decimal(String ds) {
		if (TextUtils.isEmpty(ds))
			return "0.00";
		double d = Double.parseDouble(ds);
		DecimalFormat decimal_Format = new DecimalFormat();
		decimal_Format.setMinimumFractionDigits(2);
		decimal_Format.setMaximumFractionDigits(2);
		return decimal_Format.format(d);
	}

	/**
	 * 退出程序
	 */
	public static void Exit(final Context cnt) {

		new AlertDialog.Builder(cnt)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle(R.string.app_quit_title)
				.setMessage(R.string.app_quit_message)
				.setPositiveButton(R.string.app_quit_ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								// 退出
								AppManager.getAppManager().AppExit(cnt);
							}
						})
				.setNegativeButton(R.string.app_quit_cencel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();

	}

	public static AlertDialog showNoNetWorkLinkDialog(final Activity context) {

		Resources res = context.getResources();
		AlertDialog dialog = null;

		dialog = new AlertDialog.Builder(context).setPositiveButton(
				R.string.app_action_dialog_no_network_setting,
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						context.startActivityForResult(new Intent(
								Settings.ACTION_WIFI_SETTINGS),
								Globle.NO_NETWORK_SETTING_RETURN_RESLUT);
						if (dialog != null)
							dialog.dismiss();
					}
				}).create();
		dialog.setIcon(android.R.drawable.ic_dialog_info);
		dialog.setTitle(R.string.app_action_dialog_no_network_title);
		dialog.setMessage(res
				.getString(R.string.app_action_dialog_no_network_message));

		return dialog;

	}


}
