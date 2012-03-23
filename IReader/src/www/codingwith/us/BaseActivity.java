/**
 * 
 */
package www.codingwith.us;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

/**
 * @author chenwei10
 *
 */
public class BaseActivity extends Activity {
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		InitScreenLight();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		InitScreenLight();
	}
	void InitScreenLight(){
		Window window = getWindow();
		LayoutParams lp = window.getAttributes();
		String strKey = getResources().getString(R.string.setting_screen_light);
		String setting_screen_light_def_val = getResources().getString(R.string.setting_screen_light_def_val);
		Integer def_val = Integer.valueOf(setting_screen_light_def_val);
		int barValue = PreferenceManager.getDefaultSharedPreferences(this).getInt(strKey, def_val);
		if (barValue < 1) {
			barValue = 1;
		}
		float temp = barValue/100.0f;
		lp.screenBrightness = temp;
		window.setAttributes(lp);
	}
}
