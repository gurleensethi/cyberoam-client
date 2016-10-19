package app.com.thetechnocafe.cyberoamclient.Login;

/**
 * Created by gurleensethi on 18/10/16.
 */

public interface ILoginPresenter {
    void login(String username, String password);

    void setUpAlarmManager();

    void setLoginState(boolean isLoggedIn);
}
