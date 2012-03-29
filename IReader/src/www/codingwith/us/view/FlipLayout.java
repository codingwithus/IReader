/**
 * 
 */
package www.codingwith.us.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

/**
 * @author eureka
 * 
 */
public class FlipLayout extends ViewFlipper {
	private GestureDetector detector = null;

	public FlipLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

	}

	public FlipLayout(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		super(context, attrs);
	}

	public void SetGestureDetector(GestureDetector detector) {
		this.detector = detector;
	}

	public boolean onInterceptTouchEvent(MotionEvent event) {

		if (detector != null) {
			if (detector.onTouchEvent(event)) {
				return true;
			}
		}
		return false;
	}
}
