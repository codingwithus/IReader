/**
 * 
 */
package www.codingwith.us.view;

import android.content.Context;
import android.view.MotionEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author chenwei
 * 
 */
public class MyWebView extends WebView {

	protected static final float FLING_SPACE = 150;
	private Context mContext;

	public MyWebView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		getSettings().setJavaScriptEnabled(true);
		setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
			}
		});
		setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
			}
		});

	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(ev);
	}
}
