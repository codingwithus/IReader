/**
 * 
 */
package com.codingwith.us.img;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;


import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

/**
 * @author chenwei10
 * 
 */
public class ImageDownloader {
	private static final int HARD_CACHE_CAPACITY = 0;
	private static final int SOFT_CACHE_CAPACITY = 0;
	private static ImageDownloader inst;

	public static ImageDownloader GetInstance() {
		if (inst == null) {
			inst = new ImageDownloader();

		}
		return inst;
	}

	private final static ConcurrentHashMap<String, SoftReference<Bitmap>> sSoftBitmapCache = new ConcurrentHashMap<String, SoftReference<Bitmap>>(
			SOFT_CACHE_CAPACITY / 2);
	private final static HashMap<String, Bitmap> sHardBitmapCache = new LinkedHashMap<String, Bitmap>(
			HARD_CACHE_CAPACITY / 2, 0.75f, true) {
		/**
				 * 
				 */
		private static final long serialVersionUID = 1L;

		@Override
		protected boolean removeEldestEntry(
				LinkedHashMap.Entry<String, Bitmap> eldest) {
			if (size() > HARD_CACHE_CAPACITY) {
				// Entries push-out of hard reference cache are transferred to
				// soft reference cache
				sSoftBitmapCache.put(eldest.getKey(),
						new SoftReference<Bitmap>(eldest.getValue()));
				// Log.i("removeEldestEntry",
				// "Transfer From hard cache to Soft cache");
				return true;
			} else
				return false;
		}
	};

	public void Clear() {
		sHardBitmapCache.clear();
		sSoftBitmapCache.clear();
	}

	public void AddBitmapToCache(String url, Bitmap bitmap) {
		if (bitmap != null) {
			synchronized (sHardBitmapCache) {
				sHardBitmapCache.put(url, bitmap);
			}
		}
	}

	public Bitmap GetBitmapFromCache(String url) {
		// First try the hard reference cache
		synchronized (sHardBitmapCache) {
			final Bitmap bitmap = sHardBitmapCache.get(url);
			if (bitmap != null) {
				// Bitmap found in hard cache
				// Move element to first position, so that it is removed last
				sHardBitmapCache.remove(url);
				sHardBitmapCache.put(url, bitmap);
				return bitmap;
			}
		}
		// Then try the soft reference cache
		SoftReference<Bitmap> bitmapReference = sSoftBitmapCache.get(url);
		if (bitmapReference != null) {
			final Bitmap bitmap = bitmapReference.get();
			if (bitmap != null) {
				// Bitmap found in soft cache
				return bitmap;
			} else {
				// Soft reference has been Garbage Collected
				sSoftBitmapCache.remove(url);
				Log.i("getBitmapFromCache", "gc Soft cache!");
			}
		}
		return null;
	}

	public void DownLoad(String url, ImageView img) {
		if (url == null || img == null) {
			return;
		}
		Bitmap bmp = null;
		bmp = GetBitmapFromCache(url);
		img.setTag(url);
		if (bmp != null) {
			img.setImageBitmap(bmp);
		} else {
			bmp = UtilFile.GetInstance().getBmpFromSd(url);
			if (bmp != null) {
				img.setImageBitmap(bmp);
			}else {
				new GetBitmapByUrlTask().execute(url, img);
			}
		}
	}
}
