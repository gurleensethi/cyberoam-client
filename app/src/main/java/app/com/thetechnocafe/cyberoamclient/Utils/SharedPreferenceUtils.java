package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gurleensethi on 19/10/16.
 */

public class SharedPreferenceUtils {
    private static final String SHARED_PREFERENCES_FILE = "sharedpreferencefile";
    private static final String SHARED_PREFERENCES_LOGGED_IN_STATE = "loggedin";
    private static final String SHARED_PREFERENCES_BASE_IP_ADDRESS = "ip_address";
    private static final String SHARED_PREFERENCES_PORT = "post";

    //Change the login state
    public static void changeLoginState(Context context, String state) {
        //Get editor from shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Change the state
        editor.putString(SHARED_PREFERENCES_LOGGED_IN_STATE, state);
        editor.commit();
    }

    //Query logged in state
    public static String getLoginState(Context context) {
        //Get shared preferences and return state
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SHARED_PREFERENCES_LOGGED_IN_STATE, ValueUtils.STATE_LOGGED_OUT);
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

    //Get First Run
    public static boolean isFirstRun(Context context) {
        //Get shared preferences and return boolean
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean(ValueUtils.FIRST_RUN, true)) {
            //Change the first run status
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(ValueUtils.FIRST_RUN, false);
            editor.commit();

            return true;
        }

        return false;
    }

    //Set the base IP Address
    public static void setBaseIPAddress(Context context, String ipAddress) {
        //Get shared preferences and return boolean
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);

        //Get editor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_BASE_IP_ADDRESS, ipAddress);
        editor.commit();
    }

    //Set the base Port
    public static void setBasePort(Context context, String port) {
        //Get shared preferences and return boolean
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);

        //Get editor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_PORT, port);
        editor.commit();
    }

    //Set the base IP Address
    public static String getBaseIPAddress(Context context) {
        //Get shared preferences and return boolean
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);

        return sharedPreferences.getString(SHARED_PREFERENCES_BASE_IP_ADDRESS, ValueUtils.BASE_IP_ADDRESS);
    }

    //Set the base Port
    public static String getBasePort(Context context) {
        //Get shared preferences and return boolean
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);

        return sharedPreferences.getString(SHARED_PREFERENCES_PORT, ValueUtils.BASE_PORT);
    }

    //Generate complete url address
    public static String getCompleteUrlAddress(Context context, String mode) {
        //Get shared preferences and return boolean
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);

        String completeUrl = ValueUtils.BASE_HTTP +
                sharedPreferences.getString(SHARED_PREFERENCES_BASE_IP_ADDRESS, ValueUtils.BASE_IP_ADDRESS) +
                ":" +
                sharedPreferences.getString(SHARED_PREFERENCES_PORT, ValueUtils.BASE_PORT) +
                mode;

        return completeUrl;
    }
}
