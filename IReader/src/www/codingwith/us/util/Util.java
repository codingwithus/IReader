package www.codingwith.us.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Util {
	public static String convertStreamToString(InputStream is) {   
        /*  
         * To convert the InputStream to String we use the BufferedReader.readLine()  
         * method. We iterate until the BufferedReader return null which means  
         * there's no more data to read. Each line will appended to a StringBuilder  
         * and returned as String.  
         */  
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));   
        StringBuilder sb = new StringBuilder();   
    
        String line = null;   
        try {   
            while ((line = reader.readLine()) != null) {   
                sb.append(line + "/n");   
            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                is.close();   
            } catch (IOException e) {   
                e.printStackTrace();   
            }   
        }   
    
        return sb.toString();   
    }
	public static ProgressDialog ShowProgressDialog(Context context, String text) {
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		if (text != null) {
			progressDialog.setMessage(text);
		} else {
			progressDialog.setMessage("正在加载数据...");
		}
		return progressDialog;
	}
	public static void ShowTips(Context context, String conment) {
		Toast toast = Toast.makeText(context, conment, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
