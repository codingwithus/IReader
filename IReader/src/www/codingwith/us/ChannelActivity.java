/**
 * 
 */
package www.codingwith.us;

import www.codingwith.us.view.MyPage;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ViewFlipper;

/**
 * @author chenwei
 * 
 */
public class ChannelActivity extends Activity {

	private ViewFlipper channel_viewFlipper;

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

		channel_viewFlipper = (ViewFlipper) findViewById(R.id.channel_viewFlipper);
		for (int i = 0; i < 4; i++) {
			MyPage page = new MyPage(this);
			if (i % 2 == 0) {
				page.SetViewFlipper(channel_viewFlipper, "http://www.baidu.com");
			} else {
				page.SetViewFlipper(
						channel_viewFlipper,
						"http://wapp.baidu.com/?ssid=0&from=0&bd_page_type=1&uid=wiaui_1331883276_4810&pu=sz%40224_220&idx=20000&itj=23");
			}
			channel_viewFlipper.addView(page);
		}
	}
}
