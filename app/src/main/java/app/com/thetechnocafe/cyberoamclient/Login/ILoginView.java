package app.com.thetechnocafe.cyberoamclient.Login;

/**
 * Created by gurleensethi on 18/10/16.
 */

interface ILoginView {
    void isLoginSuccessful(boolean success, String responseToDisplay);

    void setUpOnClickListeners();
}
