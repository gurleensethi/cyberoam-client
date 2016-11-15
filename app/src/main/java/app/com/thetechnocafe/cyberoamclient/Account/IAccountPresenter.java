package app.com.thetechnocafe.cyberoamclient.Account;

/**
 * Created by gurleensethi on 15/11/16.
 */

public interface IAccountPresenter {
    void onViewReady();

    void logout();

    boolean isLoggedIn();
}
