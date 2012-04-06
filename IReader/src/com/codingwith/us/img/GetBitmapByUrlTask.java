/**
 * 
 */
package com.codingwith.us.img;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

/**
 * @author chenwei10
 * 
 */
public class GetBitmapByUrlTask extends BaseAsyncTask<Object, Object, Object> {
	private ImageView view;
	private String url;

	@Override
	protected Bitmap doInBackground(Object... params) {
		// TODO Auto-generated method stub
		url = (String) params[0];
		view = (ImageView) params[1];
		Bitmap bmp = null;
		try {
			bmp = ImageDownloader.GetInstance().GetBitmapFromCache(url);
			if (bmp == null) {
				bmp = BitmapFactory.decodeStream(new URL(url).openStream());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (bmp != null) {
			ImageDownloader.GetInstance().AddBitmapToCache(url, bmp);
			UtilFile.GetInstance().saveBmpToSd(bmp, url);
		}
		return bmp;
	}

	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (result != null) {
			Bitmap bmp = (Bitmap) result;
			String kk = (String) view.getTag();
			if (!bmp.isRecycled() & url.equalsIgnoreCase(kk)) {
				view.setImageBitmap(bmp);
			} else {
				Log.i("GetBitmapByUrlTask", url);
			}
		}
	}
}
