package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Date;
import java.util.GregorianCalendar;

import app.com.thetechnocafe.cyberoamclient.Common.VolleyRequestQueue;

/**
 * Created by gurleensethi on 18/10/16.
 */

public abstract class NetworkUtils {
    private static final String TAG = "NetworkUtils";
    private static String VOLLEY_TAG;

    public NetworkUtils(@Nullable String tag) {
        VOLLEY_TAG = tag;
    }

    public abstract void onResultReceived(boolean success, int errorCode);

    /**
     * Send login POST request to cyberoam ip address specified
     * Notify the result to the presenter
     */
    public void login(Context context, final String username, final String password) {
        //Create new String request
        StringRequest loginRequest = new StringRequest(Request.Method.POST, SharedPreferenceUtils.getCompleteUrlAddress(context, ValueUtils.BASE_LOGIN_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);

                //Check for login success
                if (response.contains("successfully logged into JIIT")) {
                    onResultReceived(true, ValueUtils.LOGIN_SUCCESS);
                } else if (response.contains("could not log you")) {
                    onResultReceived(false, ValueUtils.ERROR_USERNAME_PASSWORD);
                } else if (response.contains("Maximum Login Limit")) {
                    onResultReceived(false, ValueUtils.ERROR_MAXIMUM_LOGIN_LIMIT);
                } else if (response.contains("Your AD Server account is locked")) {
                    onResultReceived(false, ValueUtils.ERROR_SERVER_ACCOUNT_LOCKED);
                } else if (response.contains("You are not allowed to login")) {
                    onResultReceived(false, ValueUtils.ERROR_NOT_ALLOWED);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onResultReceived(false, ValueUtils.ERROR_VOLLEY_ERROR);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //Return the body
                return getLoginRequestBody(username, password).getBytes();
            }
        };

        //Add tag to volley request
        if (VOLLEY_TAG != null) {
            loginRequest.setTag(VOLLEY_TAG);
        }

        //Add request to queue
        VolleyRequestQueue.getInstance(context).getRequestQueue().add(loginRequest);
    }


    /**
     * Create login body from username and password and current time
     */
    private String getLoginRequestBody(String username, String password) {
        return ValueUtils.MODE + "=" + ValueUtils.MODE_LOGIN + "&" +
                ValueUtils.USERNAME + "=" + username + "&" +
                ValueUtils.PASSWORD + "=" + password + "&" +
                ValueUtils.A + "=" + new Date().getTime();
    }

    /**
     * Check for login status,
     * whether logged in or not
     */
    public void checkLoginStatus(Context context, final String username, String password) {
        //Create new string request to check status
        StringRequest checkRequest = new StringRequest(Request.Method.GET, getLoginCheckUrl(context, username, password), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Check if already logged in
                if (response.contains("login_again")) {
                    //Login again
                    onResultReceived(false, ValueUtils.ERROR_LOGIN_AGAIN);
                } else {
                    onResultReceived(true, ValueUtils.ALREADY_LOGGED_IN);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onResultReceived(false, ValueUtils.ERROR_VOLLEY_ERROR);
            }
        });

        //Add tag to volley request
        if (VOLLEY_TAG != null) {
            checkRequest.setTag(VOLLEY_TAG);
        }

        //Add request to Volley queue
        VolleyRequestQueue.getInstance(context).getRequestQueue().add(checkRequest);
    }

    private String getLoginCheckUrl(Context context, String username, String password) {
        return SharedPreferenceUtils.getCompleteUrlAddress(context, ValueUtils.BASE_CHECK_URL) + "?" +
                ValueUtils.MODE + "=" + ValueUtils.MODE_CHECK + "&" +
                ValueUtils.USERNAME + "=" + username + "&" +
                ValueUtils.A + "=" + new GregorianCalendar().getTimeInMillis();
    }

    /**
     * Logout from existing logged in account
     * Notify presenter on login
     */
    public void logout(Context context, final String username, String password) {
        //Create new string request to logout
        StringRequest logoutRequest = new StringRequest(Request.Method.POST, SharedPreferenceUtils.getCompleteUrlAddress(context, ValueUtils.BASE_LOGOUT_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Check for response
                if (response.contains("successfully logged off from JIIT")) {
                    onResultReceived(true, ValueUtils.LOGOUT_SUCCESS);
                } else {
                    onResultReceived(false, ValueUtils.ERROR_LOGOUT);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return getLogoutRequestBody(username).getBytes();
            }
        };

        //Add tag to volley request
        if (VOLLEY_TAG != null) {
            logoutRequest.setTag(VOLLEY_TAG);
        }

        //Add request to queue
        VolleyRequestQueue.getInstance(context).getRequestQueue().add(logoutRequest);
    }

    /**
     * Create logout body from username and current time
     */
    private String getLogoutRequestBody(String username) {
        return ValueUtils.MODE + "=" + ValueUtils.MODE_LOGOUT + "&" +
                ValueUtils.USERNAME + "=" + username + "&" +
                ValueUtils.A + "=" + new Date().getTime();
    }

}
