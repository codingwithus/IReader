/**
 * 
 */
package www.codingwith.us;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * @author chenwei10
 *
 */
public class SettingActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}
