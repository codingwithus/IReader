/**
 * 
 */
package www.codingwith.us;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;

/**
 * @author chenwei10
 *
 */
public class FlowListPreference extends ListPreference {

	/**
	 * @param context
	 */
	public FlowListPreference(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * 
	 * @param attrs
	 */
	public FlowListPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		// TODO Auto-generated method stub
		super.onDialogClosed(positiveResult);
        if (positiveResult) {
    		setSummary(getValue());
        }
	}
}
