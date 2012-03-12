package www.codingwith.us;

import www.codingwith.us.view.MyViewGroup;
import www.codingwith.us.view.MyViewGroup.ScrollToScreenCallback;
import www.codingwith.us.view.Rotate3dAnimation;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;
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
        
        String[] channel = getResources().getStringArray(R.array.channel);
        LinearLayout page = null;
        for (int i = 0; i < channel.length; i++) {
    		page = (LinearLayout)getLayoutInflater().inflate(R.layout.channel, null);
    		myViewGroup.addView(page);
		}
		
        
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