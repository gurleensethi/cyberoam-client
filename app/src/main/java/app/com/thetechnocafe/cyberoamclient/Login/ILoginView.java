package app.com.thetechnocafe.cyberoamclient.Login;

/**
 * Created by gurleensethi on 18/10/16.
 */

interface ILoginView {

    static int ERROR_USERNAME_EMPTY = 101;
    static int ERROR_PASSWORD_EMPTY = 102;

    void isLoginSuccessful(boolean success, int errorCode);

    void setUpOnClickListeners();
}
