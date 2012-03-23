/**
 * 
 */
package www.codingwith.us;

import www.codingwith.us.view.MyWebView;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.ViewFlipper;

/**
 * @author chenwei
 * 
 */
public class ChannelActivity extends BaseActivity {

	private ViewFlipper channel_viewFlipper;
	private String Url;
	private GestureDetector gesture;

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
		channel_viewFlipper = new ViewFlipper(this);
		setContentView(channel_viewFlipper);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String channel_name = bundle.getString("channel_name");
		Url = "http://www.hutaoshu.com/m?channel=" + channel_name;

		for (int i = 0; i < 4; i++) {
			MyWebView webView = new MyWebView(this);
			webView.loadUrl(Url);
			channel_viewFlipper.addView(webView);
		}

		gesture = new GestureDetector(new OnGestureListener() {

			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				// TODO Auto-generated method stub
				if (Math.abs(e1.getX() - e2.getX()) > 150
						&& Math.abs(velocityX) > ViewConfiguration.get(
								getApplicationContext())
								.getScaledMinimumFlingVelocity()) {
					if (e1.getX() > e2.getX()) {
						// to left
//						channel_viewFlipper.setInAnimation(AnimationUtils
//								.loadAnimation(ChannelActivity.this,
//										R.anim.push_left_in));
//						channel_viewFlipper.setOutAnimation(AnimationUtils
//								.loadAnimation(ChannelActivity.this,
//										R.anim.push_left_out));
						channel_viewFlipper.showNext();
					} else if (e1.getX() < e2.getX()) {
						// to right
//						channel_viewFlipper.setInAnimation(AnimationUtils
//								.loadAnimation(ChannelActivity.this,
//										R.anim.push_right_in));
//						channel_viewFlipper.setOutAnimation(AnimationUtils
//								.loadAnimation(ChannelActivity.this,
//										R.anim.push_right_out));
						channel_viewFlipper.showPrevious();
					} else {
						return false;
					}
					return true;
				}
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		MyWebView currFlipperView = (MyWebView) channel_viewFlipper
				.getCurrentView();
		if (currFlipperView != null && currFlipperView.canGoBack()
				&& keyCode == KeyEvent.KEYCODE_BACK) {
			currFlipperView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		gesture.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}
}
