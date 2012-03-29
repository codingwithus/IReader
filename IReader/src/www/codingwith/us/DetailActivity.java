/**
 * 
 */
package www.codingwith.us;

import www.codingwith.us.view.MyWebView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

/**
 * @author chenwei10
 *
 */
public class DetailActivity extends Activity {
	private MyWebView web;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().requestFeature(Window.FEATURE_PROGRESS); 
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String url = bundle.getString("url");
		web= new MyWebView(this);
		if (url != null) {
			web.loadUrl(url);
		}
		setContentView(web);
		getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
	}
}
