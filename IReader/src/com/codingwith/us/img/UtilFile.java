/**
 * 
 */
package com.codingwith.us.img;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/**
 * @author chenwei10
 * 
 */
public class UtilFile {
	private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 100;
	private static final String TAG = "UtilFile";
	private static final CharSequence WHOLESALE_CONV = "png";
	private static final int CACHE_SIZE = 50;
	private static final int MB = 1024;
	private static final String AppName = "aaa";
	private long mTimeDiff;

	private static UtilFile inst;

	public static UtilFile GetInstance() {
		if (inst == null) {
			inst = new UtilFile();
		}
		return inst;
	}

	public boolean checksdcard() {
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			Log.w("cksdcard", "MEDIA_MOUNTED");
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			Log.w("cksdcard", "MEDIA_MOUNTED_READ_ONLY");
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states,
			// but all we need to know is we can neither read nor write
			Log.w("cksdcard", "Something else is wrong");
			mExternalStorageAvailable = false;
			mExternalStorageWriteable = false;
		}
		return mExternalStorageWriteable;
	}

	public Bitmap getBmpFromSd(String url) {
		if (!checksdcard()) {
			return null;
		}
		String filename = convertUrlToFileName(url);
		String dir = getDirectory(filename);
		File imageFileOnExternalDirectory = new File(dir + "/" + filename);
		if (!imageFileOnExternalDirectory.exists()) {
			return null;
		}
		// Decode image size
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		FileInputStream fis = null;
		try {
			int scale = 1;
			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;

			fis = new FileInputStream(imageFileOnExternalDirectory);
			Bitmap bm = BitmapFactory.decodeStream(fis, null, o2);
			if (fis != null) {
				fis.close();
			}
			return bm;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		return null;
	}

	public void saveBmpToSd(Bitmap bm, String url) {
		if (bm == null) {
			Log.w(TAG, " trying to savenull bitmap");
			return;
		}
		// 判断sdcard上的空间
		if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
			Log.w(TAG, "Low free space onsd, do not cache");
			return;
		}
		String filename = convertUrlToFileName(url);
		String dir = getDirectory(filename);
		File file = new File(dir + "/" + filename);
		if (file.exists()) {
			file.setLastModified(System.currentTimeMillis());
			return ;
		}
		try {
			file.createNewFile();
			OutputStream outStream = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			outStream.flush();
			outStream.close();
			Log.i(TAG, "Image saved tosd");
		} catch (FileNotFoundException e) {
			Log.w(TAG, "FileNotFoundException");
		} catch (IOException e) {
			Log.w(TAG, "IOException");
		}
	}

	private String getDirectory(String filename) {
		// TODO Auto-generated method stub
		String directory = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/" + AppName + "/photo";
		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return directory;
	}

	private String convertUrlToFileName(String url) {
		// TODO Auto-generated method stub
		return MD5.md5_string(url);
	}

	/**
	 * 计算sdcard上的剩余空间
	 * 
	 * @return
	 */
	private int freeSpaceOnSd() {
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
				.getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat
				.getBlockSize()) / MB;
		return (int) sdFreeMB;
	}

	/**
	 * 修改文件的最后修改时间
	 * 
	 * @param dir
	 * @param fileName
	 */
	public void updateFileTime(String dir, String fileName) {
		File file = new File(dir, fileName);
		long newModifiedTime = System.currentTimeMillis();
		file.setLastModified(newModifiedTime);
	}

	// 本地缓存优化

	/**
	 * 计算存储目录下的文件大小，
	 * 当文件总大小大于规定的CACHE_SIZE或者sdcard剩余空间小于FREE_SD_SPACE_NEEDED_TO_CACHE的规定
	 * 那么删除40%最近没有被使用的文件
	 * 
	 * @param dirPath
	 * @param filename
	 */
	public void removeCache(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		int dirSize = 0;
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().contains(WHOLESALE_CONV)) {
				dirSize += files[i].length();
			}
		}
		if (dirSize > CACHE_SIZE * MB
				|| FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
			int removeFactor = (int) ((0.4 * files.length) + 1);

			Arrays.sort(files, new FileLastModifSort());

			Log.i(TAG, "Clear some expiredcache files ");

			for (int i = 0; i < removeFactor; i++) {

				if (files[i].getName().contains(WHOLESALE_CONV)) {

					files[i].delete();

				}

			}

		}

	}

	/**
	 * 删除过期文件
	 * 
	 * @param dirPath
	 * @param filename
	 */
	public void removeExpiredCache(String dirPath, String filename) {

		File file = new File(dirPath, filename);

		if (System.currentTimeMillis() - file.lastModified() > mTimeDiff) {

			Log.i(TAG, "Clear some expiredcache files ");

			file.delete();

		}

	}

	/**
	 * TODO 根据文件的最后修改时间进行排序 *
	 */
	class FileLastModifSort implements Comparator<File> {
		public int compare(File arg0, File arg1) {
			if (arg0.lastModified() > arg1.lastModified()) {
				return 1;
			} else if (arg0.lastModified() == arg1.lastModified()) {
				return 0;
			} else {
				return -1;
			}
		}
	}
}
