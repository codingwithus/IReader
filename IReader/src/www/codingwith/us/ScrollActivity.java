package www.codingwith.us;

import java.util.ArrayList;

import www.codingwith.us.view.FixedGridLayout;
import www.codingwith.us.view.MyViewGroup;
import www.codingwith.us.view.MyViewGroup.ScrollToScreenCallback;
import www.codingwith.us.view.Rotate3dAnimation;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScrollActivity extends Activity implements ScrollToScreenCallback, android.view.View.OnClickListener {

	private TextView pageinfo_num;
	private LinearLayout pageinfo;
	private LinearLayout tool;
	private MyViewGroup myViewGroup;
	private String mStrChannel = "";
	private ArrayList<String> mDisplayed = new ArrayList<String>();
	private ArrayList<String> mHided = new ArrayList<String>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		pageinfo_num = (TextView) findViewById(R.id.pageinfo_num);
		pageinfo = (LinearLayout) findViewById(R.id.pageinfo);
		tool = (LinearLayout) findViewById(R.id.tool);
		((MyApplication) getApplication()).SetPageInfoWidth(tool.getWidth());

		LinearLayout rootblock_body = (LinearLayout) findViewById(R.id.rootblock_body);

		myViewGroup = new MyViewGroup(this);

		
		SharedPreferences p_channel = getSharedPreferences("p_channel", MODE_PRIVATE);
		p_channel.edit().putString("p_channel_name", "").commit();
		mStrChannel = p_channel.getString("p_channel_name", "");
		if (mStrChannel == "") {
			String[]  channel_init = getResources().getStringArray(R.array.channel_init);
			for (int i = 0; i < channel_init.length; i++) {
				String temp = channel_init[i] + ",1|";
				mStrChannel  = mStrChannel + temp;
			}
			p_channel.edit().putString("p_channel_name", mStrChannel).commit();
		}
		
		String[] arrChannel = mStrChannel.split("\\|");
		for (int i = 0; i < arrChannel.length; i++) {
			String[] temp_arr = arrChannel[i].split(",");
			if (temp_arr != null) {
				if (temp_arr[1] != null) {
					if (temp_arr[1].equalsIgnoreCase("1")) {
						mDisplayed.add(temp_arr[0]);
					}else {
						mHided.add(temp_arr[0]);
					}
				}
			}
		}
		
		
		InitView();

		myViewGroup.setScrollToScreenCallback(this);
		rootblock_body.addView(myViewGroup);

	}

	public void callback(int currentIndex) {
		// TODO Auto-generated method stub
		pageinfo_num.setText((currentIndex + 1) + "");
		applyRotation(0, 180);

	}

	/**
	 * Setup a new 3D rotation on the container view.
	 * 
	 * @param start
	 *            the start angle at which the rotation must begin
	 * @param end
	 *            the end angle of the rotation
	 */
	private void applyRotation(float start, float end) {
		// Find the center of the container
		final float centerX = pageinfo.getWidth() / 2.0f;
		final float centerY = pageinfo.getHeight() / 2.0f;

		// Create a new 3D rotation with the supplied parameter
		// The animation listener is used to trigger the next animation
		final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end,
				centerX, centerY, 0.0f, true);
		rotation.setDuration(200);

		pageinfo.startAnimation(rotation);
	}

	public void InitView() {
		myViewGroup.removeAllViews();
		int page_num = mDisplayed.size() / 8;
		if (mDisplayed.size() % 8 > 0) {
			page_num++;
		}
		for (int i = 0; i < page_num; i++) {
			FixedGridLayout page = new FixedGridLayout(this);
			for (int j = 0; j < 8; j++) {
				int index = i*8+j;
				if (index >= mDisplayed.size()) {
					break;
				}
				LinearLayout item = (LinearLayout) getLayoutInflater()
						.inflate(R.layout.channel_item, null);
				LinearLayout channel_item_layout = (LinearLayout)item.findViewById(R.id.channel_item_layout);
				channel_item_layout.setTag(mDisplayed.get(index));
				channel_item_layout.setOnClickListener(this);
				TextView channel_item_txt = (TextView)item.findViewById(R.id.channel_item_txt);
				channel_item_txt.setText(mDisplayed.get(index));
				ImageView channel_item_remove = (ImageView)item.findViewById(R.id.channel_item_remove);
				channel_item_remove.setTag(mDisplayed.get(index));
				channel_item_remove.setOnClickListener(this);
				if (!mDisplayed.get(index).equalsIgnoreCase("")) {
					page.addView(item);
				}
			}
			myViewGroup.addView(page);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.channel_item_remove:
			mDisplayed.remove(v.getTag().toString());
			mHided.add(v.getTag().toString());
			InitView();
			break;
		case R.id.channel_item_layout:
			Intent intent = new Intent(this,ChannelActivity.class);
			intent.putExtra("channel_name", v.getTag().toString());
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}