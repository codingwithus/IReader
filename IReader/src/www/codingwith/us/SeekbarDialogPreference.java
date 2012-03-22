/**
 * 
 */
package www.codingwith.us;

import android.app.Activity;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * @author chenwei10
 *
 */
public class SeekbarDialogPreference extends DialogPreference implements OnSeekBarChangeListener{

	private SeekBar seekbar;
	private int barValue;
	private Context mContext;

	/**
	 * @param context
	 * @param attrs
	 */
	public SeekbarDialogPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SeekbarDialogPreference(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onBindDialogView(View view) {
		// TODO Auto-generated method stub
		super.onBindDialogView(view);
		barValue = getPersistedInt(10);
		seekbar = (SeekBar)view.findViewById(R.id.seekBar_light);
		seekbar.setOnSeekBarChangeListener(this);
		seekbar.setProgress(barValue);
	}
	@Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
        	persistInt(barValue);
        }
    }
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		barValue = progress;
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		Window window = ((Activity)mContext).getWindow();
		LayoutParams lp = window.getAttributes();
		if (barValue < 1) {
			barValue = 1;
		}
		float temp = barValue/100.0f;
		lp.screenBrightness = temp;
		window.setAttributes(lp);
	}

}
