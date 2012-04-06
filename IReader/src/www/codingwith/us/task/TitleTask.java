/**
 * 
 */
package www.codingwith.us.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import www.codingwith.us.ICallBack;
import www.codingwith.us.util.Util;
import android.os.AsyncTask;

/**
 * @author chenwei10
 *
 */
public class TitleTask extends BaseAsyncTask<Object, Object, Object> {

	private String mJsonString;
	private ICallBack mCallBack;
	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		mJsonString = null;
		mCallBack = (ICallBack)params[0];
		if (params[1] != null) {
			String url = (String)params[1];
			HttpGet request = new HttpGet(url);
			try {
				HttpResponse response = CustomerHttpClient.getHttpClient().execute(request);
				mJsonString = GetSinaData(response);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mJsonString;
	}
	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (result != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", result);
			mCallBack.doCallBack(map );
		}
	}
	public String GetSinaData(HttpResponse response) {
		if (response != null) {
			if (200 == response.getStatusLine().getStatusCode()) {
				try {
					InputStream is = response.getEntity().getContent();
					return Util.convertStreamToString(is);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return null;
	}
}
