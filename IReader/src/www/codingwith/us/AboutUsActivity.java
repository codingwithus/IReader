/**
 * 
 */
package www.codingwith.us;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * @author chenwei10
 *
 */
public class AboutUsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		WebView aboutUs = new WebView(this);
		aboutUs.loadUrl("http://www.budaigou.com/contact");		
		setContentView(aboutUs);
	}
}
