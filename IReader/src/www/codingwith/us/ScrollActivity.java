package www.codingwith.us;

import java.util.ArrayList;

import www.codingwith.us.view.MyViewGroup;
import www.codingwith.us.view.MyViewGroup.ScrollToScreenCallback;
import www.codingwith.us.view.Rotate3dAnimation;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScrollActivity extends Activity implements ScrollToScreenCallback {

	private TextView pageinfo_num;
	private LinearLayout pageinfo;
	private LinearLayout tool;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        
        
        pageinfo_num = (TextView)findViewById(R.id.pageinfo_num);
        pageinfo = (LinearLayout)findViewById(R.id.pageinfo);
        tool = (LinearLayout)findViewById(R.id.tool);
        ((MyApplication)getApplication()).SetPageInfoWidth(tool.getWidth());
        
        FrameLayout rootblock_body = (FrameLayout)findViewById(R.id.rootblock_body);
        
        MyViewGroup myViewGroup = new MyViewGroup(this);
        
        ImageView image = new ImageView(this);
        image.setBackgroundResource(R.drawable.volume_0_ic);
        ImageView image1 = new ImageView(this);
        image1.setBackgroundResource(R.drawable.volume_1_ic);
        ImageView image2 = new ImageView(this);
        image2.setBackgroundResource(R.drawable.volume_2_ic);
        ImageView image3 = new ImageView(this);
        image3.setBackgroundResource(R.drawable.volume_3_ic);
        
        ImageView mod1 = new ImageView(this);
        mod1.setBackgroundResource(R.drawable.rootblock_block_blue_1);
        ImageView mod2 = new ImageView(this);
        mod2.setBackgroundResource(R.drawable.rootblock_block_blue_2);
        
        
        LinearLayout lin = new LinearLayout(this);
        lin.setBackgroundColor(0xff00ffff);
        GridView grid1 = new GridView(this);
        grid1.setNumColumns(2);
        ArrayList<String> data = new ArrayList<String>();
        for (int i = 0; i < 8; i++) {
        	data.add(i+"");
		}
        ArrayAdapter<String> gridAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.channel, R.id.grit_item_txt, data);
        grid1.setAdapter(gridAdapter);
//        lin.addView(image1);
        grid1.setLayoutParams(new LayoutParams(480, 800));
        lin.addView(grid1);
        

        myViewGroup.addView(lin);
        myViewGroup.addView(image);

		
        
        myViewGroup.setScrollToScreenCallback(this);        
        rootblock_body.addView(myViewGroup);

    }

	public void callback(int currentIndex) {
		// TODO Auto-generated method stub
		pageinfo_num.setText((currentIndex+1)+"");
		applyRotation(0,180);

	}
    /**
     * Setup a new 3D rotation on the container view.
     *
     * @param start the start angle at which the rotation must begin
     * @param end the end angle of the rotation
     */
    private void applyRotation(float start, float end) {
        // Find the center of the container
        final float centerX = pageinfo.getWidth() / 2.0f;
        final float centerY = pageinfo.getHeight() / 2.0f;

        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        final Rotate3dAnimation rotation =
                new Rotate3dAnimation(start, end, centerX, centerY, 0.0f, true);
        rotation.setDuration(200);

        pageinfo.startAnimation(rotation);
    }
    public void InitChannel() {
	}
}