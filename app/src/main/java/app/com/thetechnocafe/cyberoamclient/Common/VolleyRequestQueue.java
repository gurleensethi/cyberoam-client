package app.com.thetechnocafe.cyberoamclient.Common;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by gurleensethi on 18/10/16.
 */

public class VolleyRequestQueue {

    private static VolleyRequestQueue mInstance;
    private RequestQueue mRequestQueue;

    //Private constructor to make class singleton
    private VolleyRequestQueue(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    //Return Instance
    public static VolleyRequestQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyRequestQueue(context);
        }

        return mInstance;
    }

    //Return request queue
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
