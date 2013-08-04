package com.hotpot;

import java.io.File;

import com.hotpot.activity.BaseActivity;
import com.hotpot.util.FileUtil;
import com.hotpot.util.UtilsHelper;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.asy.image.ImageCache;
import android.support.asy.image.ImageFetcher;
import android.widget.Toast;


public class HotPotApplication extends Application {

	// 图片加载类的实例，有需要加载图片的时候可以直接获取该变量，然后进行异步的加载
	private ImageFetcher mImageFetcher;


	@Override
	public void onCreate() {
		super.onCreate();
		/** 初始化图片缓存文件夹 */
		FileUtil.createDirs(getImageCacheFolderPath());
		FileUtil.createDirs(getImageDownloadFolderPath());
	}




	/**
	 * 清除程序缓存
	 */
	public void clearAppCache() {
		// 删除缓存文件夹中的数据
		if (mImageFetcher != null)
			mImageFetcher.clearCache();
	}

	/** 销毁 */
	public void destory() {
		// if (mImageFetcher != null)
		// mImageFetcher.closeCache();
	}

	/**
	 * 读取应用名称
	 */
	public String getAppName() {
		ApplicationInfo info = getApplicationInfo();
		return info.loadLabel(getPackageManager()).toString();
	}

	/**
	 * 读取应用版本名称
	 */
	public String getAppVersion() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "1.0";
	}

	/**
	 * 读取应用版本号
	 */
	public int getAppVersionCode() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 获取图片缓存文件夹路径
	 */
	public String getImageCacheFolderPath() {
		return ImageCache.getDiskCacheDir(this, Globle.IMAGE_CACHE_DIR)
				.getAbsolutePath();
	}

	/**
	 * 获取图片文件下载文件夹路径
	 */
	public String getImageDownloadFolderPath() {
		return ImageCache.getDiskCacheDir(this, Globle.IMAGE_DOWNLOAD_DIR)
				.getAbsolutePath();
	}

	/**
	 * 获取url指向的图片文件在本地的路径（带文件名）
	 */
	public String getCacheImageAbsolutePath(String fileUrl) {
		String fileName = ImageCache.hashKeyForDisk(fileUrl) + ".0";
		return getImageCacheFolderPath() + File.separator + fileName;
	}

	/**
	 * 获取图片加载类实例
	 */
	public ImageFetcher getImageFetcher(Activity mContext) {
		if (null == mImageFetcher) {
			final int width = UtilsHelper.getScreenWidth((Activity) mContext);
			ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(
					mContext, Globle.IMAGE_CACHE_DIR);
			cacheParams.setMemCacheSizePercent(mContext, 0.25f);
			mImageFetcher = new ImageFetcher(mContext, width);
			mImageFetcher.addImageCache(
					((BaseActivity) mContext).getSupportFragmentManager(),
					cacheParams);
			mImageFetcher.setLoadingImage(R.drawable.defult_image);
			mImageFetcher.setImageFadeIn(true);
		}
		return mImageFetcher;
	}

	public void setImageFetcher(ImageFetcher mImageFetcher) {
		this.mImageFetcher = mImageFetcher;
	}
}
