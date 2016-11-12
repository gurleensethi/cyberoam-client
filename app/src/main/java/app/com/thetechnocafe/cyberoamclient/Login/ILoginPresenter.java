package app.com.thetechnocafe.cyberoamclient.Login;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.AccountsModel;

/**
 * Created by gurleensethi on 18/10/16.
 */

public interface ILoginPresenter {
    void login(String username, String password);

    void setUpAlarmManager();

    void setLoginState(boolean isLoggedIn);

    List<AccountsModel> getSavedAccounts();

    void changeSharedUsernameAndPassword(String username);
}
