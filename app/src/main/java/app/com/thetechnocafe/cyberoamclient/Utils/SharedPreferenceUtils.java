package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gurleensethi on 19/10/16.
 */

public class SharedPreferenceUtils {
    private static final String SHARED_PREFERENCES_FILE = "sharedpreferencefile";
    private static final String SHAREDPREFERENCES_LOGGED_IN_STATE = "loggedin";

    //Change the login state
    public static void changeLoginState(Context context, String state) {
        //Get editor from shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Change the state
        editor.putString(SHAREDPREFERENCES_LOGGED_IN_STATE, state);
        editor.commit();
    }

    //Query logged in state
    public static String getLoginState(Context context) {
        //Get shared preferences and return state
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SHAREDPREFERENCES_LOGGED_IN_STATE, ValueUtils.STATE_LOGGED_OUT);
    }

    //Change the username and password
    public static void setUsernameAndPassword(Context context, String username, String passwrod) {
        //Get editor from shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Put username and password
        editor.putString(ValueUtils.USERNAME, username);
        editor.putString(ValueUtils.PASSWORD, passwrod);
        editor.commit();
    }

    //Get the username
    public static String getUsername(Context context) {
        //Get shared preferences and return username
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ValueUtils.USERNAME, "");
    }

    //Get the password
    public static String getPassword(Context context) {
        //Get shared preferences and return password
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ValueUtils.PASSWORD, "");
    }
}
