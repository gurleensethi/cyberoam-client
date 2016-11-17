package app.com.thetechnocafe.cyberoamclient.Models;

import io.realm.RealmObject;

/**
 * Created by gurleensethi on 17/11/16.
 */

public class SessionLogModel extends RealmObject {
    private String mUsername;
    private String mLoggedInTime;
    private long mDataConsumed;
    private String mLoggedInDuration;

    public long getDataConsumed() {
        return mDataConsumed;
    }

    public void setDataConsumed(long dataConsumed) {
        mDataConsumed = dataConsumed;
    }

    public String getLoggedInDuration() {
        return mLoggedInDuration;
    }

    public void setLoggedInDuration(String loggedInDuration) {
        mLoggedInDuration = loggedInDuration;
    }

    public String getLoggedInTime() {
        return mLoggedInTime;
    }

    public void setLoggedInTime(String loggedInTime) {
        mLoggedInTime = loggedInTime;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }
}
