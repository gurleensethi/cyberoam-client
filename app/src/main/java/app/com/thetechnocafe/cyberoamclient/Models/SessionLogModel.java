package app.com.thetechnocafe.cyberoamclient.Models;

import io.realm.RealmObject;

/**
 * Created by gurleensethi on 17/11/16.
 */

public class SessionLogModel extends RealmObject {
    private String mUsername;
    private long mLoggedInTime;
    private double mDataConsumed;
    private long mLoggedInDuration;
    private int mLoginStatus;

    public double getDataConsumed() {
        return mDataConsumed;
    }

    public void setDataConsumed(double dataConsumed) {
        mDataConsumed = dataConsumed;
    }

    public long getLoggedInDuration() {
        return mLoggedInDuration;
    }

    public void setLoggedInDuration(long loggedInDuration) {
        mLoggedInDuration = loggedInDuration;
    }

    public long getLoggedInTime() {
        return mLoggedInTime;
    }

    public void setLoggedInTime(long loggedInTime) {
        mLoggedInTime = loggedInTime;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public int getLoginStatus() {
        return mLoginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        mLoginStatus = loginStatus;
    }
}
