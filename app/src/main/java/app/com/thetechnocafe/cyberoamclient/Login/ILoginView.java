package app.com.thetechnocafe.cyberoamclient.Login;

import android.content.Context;

/**
 * Created by gurleensethi on 18/10/16.
 */

public interface ILoginView {

    int LOGIN_SUCCESS = 100;
    int ERROR_USERNAME_EMPTY = 101;
    int ERROR_PASSWORD_EMPTY = 102;
    int ERROR_USERNAME_PASSWORD = 103;
    int ERROR_MAXIMUM_LOGIN_LIMIT = 104;
    int ERROR_SERVER_ACCOUNT_LOCKED = 105;
    int ERROR_VOLLEY_ERROR = 110;

    void isLoginSuccessful(boolean success, int errorCode);

    void setUpOnClickListeners();

    Context getContext();
}
