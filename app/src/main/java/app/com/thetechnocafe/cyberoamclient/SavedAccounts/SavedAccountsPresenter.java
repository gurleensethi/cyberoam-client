package app.com.thetechnocafe.cyberoamclient.SavedAccounts;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.AccountsModel;

/**
 * Created by gurleensethi on 02/11/16.
 */

public class SavedAccountsPresenter implements ISavedAccountsPresenter {

    private ISavedAccountsView mView;

    /**
     * Constructor
     * Require ISavedAccountsView
     */
    public SavedAccountsPresenter(ISavedAccountsView view) {
        mView = view;
        view.setUpView();
    }

    @Override
    public void getAccountDetails(String username) {

    }

    public ISavedAccountsView getView() {
        return mView;
    }

    @Override
    public List<AccountsModel> getSavedAccounts() {
        return null;
    }

    @Override
    public void editExistingAccount(String username, String password) {

    }

    @Override
    public boolean addNewAccount(String username, String password) {
        return false;
    }
}
