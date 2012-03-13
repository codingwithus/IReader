package www.codingwith.us;

import java.util.ArrayList;

import www.codingwith.us.view.FixedGridLayout;
import www.codingwith.us.view.MyViewGroup;
import www.codingwith.us.view.MyViewGroup.ScrollToScreenCallback;
import www.codingwith.us.view.Rotate3dAnimation;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ScrollActivity extends Activity implements ScrollToScreenCallback {

	private TextView pageinfo_num;
	private LinearLayout pageinfo;
	private LinearLayout tool;
	private String[] channel;
	private MyViewGroup myViewGroup;

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
		
//		FixedGridLayout grid = new FixedGridLayout(this);
//		for (int i = 0; i < 8; i++) {
//			LinearLayout item = (LinearLayout) getLayoutInflater()
//					.inflate(R.layout.channel_item, null);
//			grid.addView(item);
//		}
//		myViewGroup.addView(grid);

		channel = getResources().getStringArray(R.array.channel);
		int page_num = channel.length / 8;
		if (page_num % 8 > 0) {
			page_num++;
		}

		String[][][] data = new String[page_num][4][2];
		for (int i = 0; i < data.length; i++) {
			LinearLayout page = (LinearLayout) getLayoutInflater().inflate(
					R.layout.channel_list, null);
			TableLayout channel_list_table = (TableLayout) page
					.findViewById(R.id.channel_list_table);
			for (int j = 0; j < data[i].length; j++) {
				TableRow tableRow = new TableRow(this);
				for (int j2 = 0; j2 < data[i][j].length; j2++) {
					int item_index = i*8 + j*2 +j2;
					if (item_index >= channel.length) {
						break;
					}
					LinearLayout item = (LinearLayout) getLayoutInflater()
							.inflate(R.layout.channel_item, null);
					TextView txt = (TextView) item
							.findViewById(R.id.channel_item_txt);
					txt.setText(channel[item_index]);
					ImageView channel_item_remove = (ImageView)item.findViewById(R.id.channel_item_remove);
					ArrayList<Integer> item_data = new ArrayList<Integer>();
					item_data.add(i);
					item_data.add(j);
					item_data.add(j2);
					item_data.add(item_index);					
					channel_item_remove.setTag(item_data);
					channel_item_remove.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							@SuppressWarnings("unchecked")
							ArrayList<Integer> temp_data = (ArrayList<Integer>)v.getTag();
							LinearLayout temp_page = (LinearLayout)myViewGroup.getChildAt(temp_data.get(0));
							TableLayout channel_list_table = (TableLayout) temp_page
									.findViewById(R.id.channel_list_table);
							TableRow temp_row= (TableRow)channel_list_table.getChildAt(temp_data.get(1));
							temp_row.removeViewAt(temp_data.get(2));							
						}
					});
					tableRow.addView(item);
				}
				channel_list_table.addView(tableRow);
			}
			myViewGroup.addView(page);
		}

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

	public void InitChannel() {
	}

}