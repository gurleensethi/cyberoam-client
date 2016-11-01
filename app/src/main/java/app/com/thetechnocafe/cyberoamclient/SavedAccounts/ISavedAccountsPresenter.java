package app.com.thetechnocafe.cyberoamclient.SavedAccounts;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.AccountsModel;

/**
 * Created by gurleensethi on 02/11/16.
 */

public interface ISavedAccountsPresenter {

    public List<AccountsModel> getSavedAccounts();

    public boolean addNewAccount(String username, String password);

    public void editExistingAccount(String username, String password);

    public void getAccountDetails(String username);

}
