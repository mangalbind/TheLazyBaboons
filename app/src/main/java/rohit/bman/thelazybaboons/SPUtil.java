package rohit.bman.thelazybaboons;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtil {


    public static void setSPString(Context context, String key, String value, String spName) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key, value);

        editor.apply();

    }


    public static String getSPString(Context context, String key, String spName) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);

        return sharedPreferences.getString(key, "");

    }

}
