package com.hotpot.util;

import android.util.Log;

public class Logger {

	private static boolean isLog = true;

	private static final String TAG = "HotPot";

	public static void d(String msg) {
		if (isLog) {
			Log.d(TAG, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (isLog) {
			Log.d(tag, msg);
		}
	}

	public static void d(String tag, String msg, Throwable tr) {
		if (isLog) {
			Log.d(tag, msg, tr);
		}
	}

	public static void w(String tag, Throwable tr) {
		if (isLog)
			Log.w(tag, tr);
	}

	public static void e(String msg) {
		Log.e(TAG, msg);
	}

	public static void e(String tag, String msg) {
		Log.e(tag, msg);
	}

	public static void e(String tag, String msg, Throwable tr) {
		Log.e(tag, msg, tr);
	}

	public static void i(String msg) {
		if (isLog) {
			Log.i(TAG, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (isLog) {
			Log.i(tag, msg);
		}
	}

	public static void i(String tag, String msg, Throwable tr) {
		if (isLog) {
			Log.i(tag, msg, tr);
		}
	}

}
