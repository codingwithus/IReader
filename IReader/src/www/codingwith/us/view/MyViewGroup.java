package www.codingwith.us.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class MyViewGroup extends ViewGroup {

	private static final String TAG = "scroller";

	private Scroller scroller;

	private int currentScreenIndex;

	private GestureDetector gestureDetector;

	private ScrollToScreenCallback scrollToScreenCallback;

	public void setScrollToScreenCallback(
			ScrollToScreenCallback scrollToScreenCallback) {
		this.scrollToScreenCallback = scrollToScreenCallback;
	}

	private boolean fling;

	public MyViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public MyViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public MyViewGroup(Context context) {
		super(context);
		initView(context);
	}

	private void initView(final Context context) {
		this.scroller = new Scroller(context);

		this.gestureDetector = new GestureDetector(new OnGestureListener() {

			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}

			public void onShowPress(MotionEvent e) {
			}

			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				if ((distanceX > 0 && currentScreenIndex < getChildCount() - 1)
						|| (distanceX < 0 && getScrollX() > 0)) {
					scrollBy((int) distanceX, 0);
				}
				return true;
			}

			public void onLongPress(MotionEvent e) {
			}

			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				Log.d(TAG, "min velocity >>>"
						+ ViewConfiguration.get(context)
								.getScaledMinimumFlingVelocity()
						+ " current velocity>>" + velocityX);
				if (Math.abs(velocityX) > ViewConfiguration.get(context)
						.getScaledMinimumFlingVelocity()) {
					if (velocityX > 0 && currentScreenIndex > 0) {
						Log.d(TAG, ">>>>fling to left");
						fling = true;
						scrollToScreen(currentScreenIndex - 1);
					} else if (velocityX < 0
							&& currentScreenIndex < getChildCount() - 1) {
						Log.d(TAG, ">>>>fling to right");
						fling = true;
						scrollToScreen(currentScreenIndex + 1);
					}
				}

				return true;
			}

			public boolean onDown(MotionEvent e) {
				return false;
			}
		});
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		Log.d(TAG, ">>left: " + left + " top: " + top + " right: " + right
				+ " bottom:" + bottom);

		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			child.setVisibility(View.VISIBLE);
			child.measure(right - left, bottom - top);
			child.layout(0 + i * getWidth(), 0, getWidth() + i * getWidth(),
					getHeight());
		}
	}

	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			scrollTo(scroller.getCurrX(), 0);
			postInvalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			if (!fling) {
				snapToDestination();
			}
			fling = false;
			break;
		default:
			break;
		}
		return true;
	}

	public void scrollToScreen(int whichScreen) {
		if (getFocusedChild() != null && whichScreen != currentScreenIndex
				&& getFocusedChild() == getChildAt(currentScreenIndex)) {
			getFocusedChild().clearFocus();
		}

		final int delta = whichScreen * getWidth() - getScrollX();
		scroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) * 2);
		invalidate();

		currentScreenIndex = whichScreen;
		if (scrollToScreenCallback != null) {
			scrollToScreenCallback.callback(currentScreenIndex);
		}
	}

	private void snapToDestination() {
//		scrollToScreen((getScrollX() + (getWidth() / 2)) / getWidth());
	}

	public interface ScrollToScreenCallback {
		public void callback(int currentIndex);
	}
}
