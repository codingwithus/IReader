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
import android.text.Html;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
private GestureDetector gesture;
private int numPage = 1;
protected ViewFlipper channel_viewFlipper;
private TextView page_num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.title);
		channel_viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
		page_num = (TextView)findViewById(R.id.page_num);
		String url = "http://ws1z.com/t.php";
//		String url = "http://58.215.184.11/t.json";
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
						page_num.setText(numPage++ + "/12");
					} else if (e1.getX() < e2.getX()) {
						// to right
//						channel_viewFlipper.setInAnimation(AnimationUtils
//								.loadAnimation(ChannelActivity.this,
//										R.anim.push_right_in));
//						channel_viewFlipper.setOutAnimation(AnimationUtils
//								.loadAnimation(ChannelActivity.this,
//										R.anim.push_right_out));
						channel_viewFlipper.showPrevious();
						page_num.setText(numPage-- + "/12");
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
				for (int k = 0; k < page_array.length(); k++) {
					LayoutInflater inflater = getLayoutInflater();  
					LinearLayout title_item = (LinearLayout)inflater.inflate(R.layout.title_item, null);
					JSONArray page = (JSONArray)page_array.get(k);
					
					TextView tc_1 = (TextView)title_item.findViewById(R.id.tc_1);
					JSONObject data1 = (JSONObject)page.get(0);
					tc_1.setText(Html.fromHtml(data1.getString("title")));
					tc_1.setTag(data1.getString("url"));
					
					TextView tc_2 = (TextView)title_item.findViewById(R.id.tc_2);
					JSONObject data2 = (JSONObject)page.get(1);
					tc_2.setText(Html.fromHtml(data1.getString("title")));
					tc_2.setTag(data1.getString("url"));
					
					TextView tc_3 = (TextView)title_item.findViewById(R.id.tc_3);
					JSONObject data3 = (JSONObject)page.get(2);
					tc_3.setText(Html.fromHtml(data1.getString("title")));
					tc_3.setTag(data1.getString("url"));
					
					TextView tc_4 = (TextView)title_item.findViewById(R.id.tc_4);
					JSONObject data4 = (JSONObject)page.get(3);
					tc_4.setText(Html.fromHtml(data1.getString("title")));
					tc_4.setTag(data1.getString("url"));
					
					TextView tc_5 = (TextView)title_item.findViewById(R.id.tc_5);
					JSONObject data5 = (JSONObject)page.get(4);
					tc_5.setText(Html.fromHtml(data1.getString("title")));
					tc_5.setTag(data1.getString("url"));
					
					TextView tc_6 = (TextView)title_item.findViewById(R.id.tc_6);
					JSONObject data6 = (JSONObject)page.get(5);
					tc_6.setText(Html.fromHtml(data1.getString("title")));
					tc_6.setTag(data1.getString("url"));

					channel_viewFlipper.addView(title_item);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
}
