/**
 * 
 */
package www.codingwith.us;

import android.app.Application;

/**
 * @author chenwei10
 * 
 */
public class MyApplication extends Application {

	private int mPageInfoWidth;

	/**
	 * 
	 */
	public MyApplication() {
		// TODO Auto-generated constructor stub
	}

	public void SetPageInfoWidth(int pageInfoWidth) {
		mPageInfoWidth = pageInfoWidth;
	}

	public int GetPageInfoWidth() {
		return mPageInfoWidth;
	}

}
