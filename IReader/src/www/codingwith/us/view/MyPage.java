/**
 * 
 */
package www.codingwith.us.view;

import www.codingwith.us.ChannelActivity;
import www.codingwith.us.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

/**
 * @author chenwei
 * 
 */
public class MyPage extends LinearLayout {

	private ViewFlipper mViewFlipper;
	private Context mContext;
	private GestureDetector gesture;
	private WebView wv;

	/**
	 * @param context
	 */
	public MyPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		InitView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public MyPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		InitView(context);
	}

	private void InitView(Context context) {
		mContext = context;
		wv = new WebView(context);
		addView(wv);
		gesture = new GestureDetector(new OnGestureListener() {

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				// TODO Auto-generated method stub
				Log.e("mypage",
						velocityY + "  " + Math.abs(e1.getY() - e2.getY())
								+ "  " + 300);
				if (velocityY > 3500
						&& Math.abs(e1.getY() - e2.getY()) > getHeight() / 4) {
					((ChannelActivity) mContext).finish();
					return true;
				}
				if (Math.abs(e1.getX() - e2.getX()) > 150) {
					if (e1.getX() > e2.getX()) {
						// to left
						mViewFlipper.setInAnimation(AnimationUtils
								.loadAnimation(mContext, R.anim.push_left_in));
						mViewFlipper.setOutAnimation(AnimationUtils
								.loadAnimation(mContext, R.anim.push_left_out));
						mViewFlipper.showNext();
					} else if (e1.getX() < e2.getX()) {
						// to right
						mViewFlipper.setInAnimation(AnimationUtils
								.loadAnimation(mContext, R.anim.push_right_in));
						mViewFlipper.setOutAnimation(AnimationUtils
								.loadAnimation(mContext, R.anim.push_right_out));
						mViewFlipper.showPrevious();
					} else {
						return false;
					}
					return true;
				}
				return false;
			}

			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

	public void SetViewFlipper(ViewFlipper viewFlipper, String url) {
		mViewFlipper = viewFlipper;
		wv.loadUrl(url);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return gesture.onTouchEvent(ev);
	}

}
