/**
 * 
 */
package www.codingwith.us;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import www.codingwith.us.task.TitleTask;
import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * @author chenwei10
 *
 */
public class TitleActivity extends Activity implements ICallBack{
private ViewFlipper channel_viewFlipper;
private GestureDetector gesture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		channel_viewFlipper = new ViewFlipper(this);
		setContentView(channel_viewFlipper);
		String url = "http://ws1z.com/t.php";
		new TitleTask().execute(this,url);
		
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
	public void doCallBack(Map<String, Object> map) {
		// TODO Auto-generated method stub
		if (map.get("data") != null) {
			String jsonString = map.get("data").toString();
			try {
				JSONArray page_array = new JSONArray(jsonString);
				for (int i = 0; i < page_array.length(); i++) {
					JSONArray page = (JSONArray)page_array.get(i);
					LinearLayout linear = new LinearLayout(this);
					linear.setOrientation(LinearLayout.VERTICAL);
					for (int j = 0; j < page.length(); j++) {
						JSONObject conment = (JSONObject)page.get(i);
						String title = (String)conment.get("title");
						if (j%2 == 0) {
							linear.addView(GetTitleText(title));
						}else {
							linear.addView(GetTitleImageText(title));
						}
					}
					channel_viewFlipper.addView(linear);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	View GetTitleText(String conment){
		LayoutInflater inflater = getLayoutInflater();  
		LinearLayout title_text = (LinearLayout)inflater.inflate(R.layout.title_text, null);
		TextView title_conment1 = (TextView)title_text.findViewById(R.id.title_conment1);
		TextView title_conment2 = (TextView)title_text.findViewById(R.id.title_conment2);
		title_conment1.setText(conment);
		title_conment2.setText(conment);
		return title_text;		
	}
	View GetTitleImageText(String conment){
		LayoutInflater inflater = getLayoutInflater();  
		LinearLayout title_image_text = (LinearLayout)inflater.inflate(R.layout.title_image_text, null);
		TextView title_image_text_conment = (TextView)title_image_text.findViewById(R.id.title_image_text_conment);
		title_image_text_conment.setText(conment);
		return title_image_text;
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		gesture.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}
}
