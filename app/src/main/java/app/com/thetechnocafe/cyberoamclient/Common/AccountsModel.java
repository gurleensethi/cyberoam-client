package app.com.thetechnocafe.cyberoamclient.Common;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gurleensethi on 01/11/16.
 */

public class AccountsModel extends RealmObject {
    private String mPassword;
    @PrimaryKey
    private String mUsername;
    private boolean isAccountValid;

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public boolean isAccountValid() {
        return isAccountValid;
    }

    public void setAccountValid(boolean valid) {
        isAccountValid = valid;
    }
}
