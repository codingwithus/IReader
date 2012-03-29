/**
 * 
 */
package www.codingwith.us;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import www.codingwith.us.task.TitleTask;
import www.codingwith.us.util.Util;
import www.codingwith.us.view.FlipLayout;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author chenwei10
 *
 */
public class TitleActivity extends Activity implements ICallBack{
private GestureDetector gesture;
private int numPage = 1;
protected FlipLayout channel_viewFlipper;
private TextView page_num;
private OnClickListener title_item_Listener;
private ProgressDialog progressDialog;
private ArrayList<Object> page_data;
private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.title);
		page_data = new ArrayList<Object>();
		channel_viewFlipper = (FlipLayout)findViewById(R.id.viewFlipper);
		page_num = (TextView)findViewById(R.id.page_num);
		page_num.setText("1/12");
//		String url = "http://ws1z.com/t.php";
		url = "http://58.215.184.11/t.json";
		new TitleTask().execute(this,url);
		progressDialog = Util.ShowProgressDialog(this, null);
		progressDialog.show();
		title_item_Listener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ToDetailActivity(v);
			}
		};	
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
				if (Math.abs(velocityX) > ViewConfiguration.get(
								getApplicationContext())
								.getScaledMinimumFlingVelocity()) {
					if (e1.getX() > e2.getX()) {
						// to left
//						channel_viewFlipper.setInAnimation(AnimationUtils
//								.loadAnimation(TitleActivity.this,
//										R.anim.push_left_in));
//						channel_viewFlipper.setOutAnimation(AnimationUtils
//								.loadAnimation(TitleActivity.this,
//										R.anim.push_left_out));
						if (numPage >= page_data.size()) {
							Util.ShowTips(getApplicationContext(), "请稍等......");
							new TitleTask().execute(TitleActivity.this,url);
							return true;
						}else if (numPage >= page_data.size() - 4) {
							
						}
						channel_viewFlipper.showNext();
						numPage = numPage+1;
						page_num.setText(numPage + "/" +page_data.size());
						return true;
					} else if (e1.getX() < e2.getX()) {
						// to right
//						channel_viewFlipper.setInAnimation(AnimationUtils
//								.loadAnimation(TitleActivity.this,
//										R.anim.push_right_in));
//						channel_viewFlipper.setOutAnimation(AnimationUtils
//								.loadAnimation(TitleActivity.this,
//										R.anim.push_right_out));
						if (numPage <= 1) {
							Util.ShowTips(getApplicationContext(), "已经是第一页！");
							return true;
						}
						channel_viewFlipper.showPrevious();
						numPage = numPage-1;
						page_num.setText(numPage + "/" +page_data.size());
						return true;
					}else {
						return false;
					}
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
		channel_viewFlipper.SetGestureDetector(gesture);
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
					page_data.add(page);
					TextView tc_1 = (TextView)title_item.findViewById(R.id.tc_1);
					JSONObject data1 = (JSONObject)page.get(0);
					tc_1.setText(Html.fromHtml(data1.getString("title")));
					tc_1.setTag(data1.getString("url"));
					tc_1.setOnClickListener(title_item_Listener);

					
					TextView tc_2 = (TextView)title_item.findViewById(R.id.tc_2);
					JSONObject data2 = (JSONObject)page.get(1);
					tc_2.setText(Html.fromHtml(data2.getString("title")));
					tc_2.setTag(data1.getString("url"));
					tc_2.setOnClickListener(title_item_Listener);
					
					TextView tc_3 = (TextView)title_item.findViewById(R.id.tc_3);
					JSONObject data3 = (JSONObject)page.get(2);
					tc_3.setText(Html.fromHtml(data3.getString("title")));
					tc_3.setTag(data1.getString("url"));
					tc_3.setOnClickListener(title_item_Listener);
					
					TextView tc_4 = (TextView)title_item.findViewById(R.id.tc_4);
					JSONObject data4 = (JSONObject)page.get(3);
					tc_4.setText(Html.fromHtml(data4.getString("title")));
					tc_4.setTag(data1.getString("url"));
					tc_4.setOnClickListener(title_item_Listener);
					
					TextView tc_5 = (TextView)title_item.findViewById(R.id.tc_5);
					JSONObject data5 = (JSONObject)page.get(4);
					tc_5.setText(Html.fromHtml(data5.getString("title")));
					tc_5.setTag(data1.getString("url"));
					tc_5.setOnClickListener(title_item_Listener);
					
					TextView tc_6 = (TextView)title_item.findViewById(R.id.tc_6);
					JSONObject data6 = (JSONObject)page.get(5);
					tc_6.setText(Html.fromHtml(data6.getString("title")));
					tc_6.setTag(data1.getString("url"));
					tc_6.setOnClickListener(title_item_Listener);

					channel_viewFlipper.addView(title_item);
				}
				page_num.setText(numPage+"/"+page_data.size());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		if (progressDialog != null) {
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		}
	}
	
	void ToDetailActivity(View v){
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra("url", v.getTag().toString());
		startActivity(intent);
	}
}
