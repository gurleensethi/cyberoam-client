package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Date;

import app.com.thetechnocafe.cyberoamclient.Login.ILoginView;

/**
 * Created by gurleensethi on 18/10/16.
 */

public abstract class NetworkUtils {
    private static final String TAG = "NetworkUtils";

    public abstract void onResultReceived(boolean success, int errorCode);

    public void login(Context context, final String username, final String password) {
        //Create new String request
        StringRequest loginRequest = new StringRequest(Request.Method.POST, StringUtils.BASE_LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);

                //Check for login success
                if (response.contains("successfully logged into JIIT")) {
                    onResultReceived(true, ILoginView.LOGIN_SUCCESS);
                } else if (response.contains("could not log you")) {
                    onResultReceived(false, ILoginView.ERROR_USERNAME_PASSWORD);
                } else if (response.contains("Maximum Login Limit")) {
                    onResultReceived(false, ILoginView.ERROR_MAXIMUM_LOGIN_LIMIT);
                } else if (response.contains("Your AD Server account is locked")) {
                    onResultReceived(false, ILoginView.ERROR_SERVER_ACCOUNT_LOCKED);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onResultReceived(false, ILoginView.ERROR_VOLLEY_ERROR);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //Return the body
                return getRequestBody(username, password).getBytes();
            }
        };

        //Add request to queue
        VolleyRequestQueue.getInstance(context).getRequestQueue().add(loginRequest);
    }


    /**
     * Create login url from username and password and current time
     */
    private String getRequestBody(String username, String password) {
        return StringUtils.MODE + "=" +
                StringUtils.MODE_LOGIN + "&" +
                StringUtils.USERNAME + "=" + username + "&" +
                StringUtils.PASSWORD + "=" + password + "&" +
                StringUtils.A + "=" + new Date().getTime();
    }
}
