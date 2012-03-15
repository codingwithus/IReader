/**
 * 
 */
package www.codingwith.us;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

/**
 * @author chenwei
 *
 */
public class ChannelActivity extends Activity {

	/**
	 * 
	 */
	public ChannelActivity() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.channel);
		
		WebView channel_wv = (WebView)findViewById(R.id.channel_wv);
		channel_wv.loadUrl("http://www.baidu.com/");
		
	}

}
