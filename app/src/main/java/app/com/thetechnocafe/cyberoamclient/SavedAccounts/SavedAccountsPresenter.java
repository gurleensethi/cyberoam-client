package app.com.thetechnocafe.cyberoamclient.SavedAccounts;

import android.widget.Toast;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.AccountsDatabase;
import app.com.thetechnocafe.cyberoamclient.Common.AccountsModel;

/**
 * Created by gurleensethi on 02/11/16.
 */

public class SavedAccountsPresenter implements ISavedAccountsPresenter {

    private ISavedAccountsView mView;
    private AccountsDatabase mAccountsDatabase;

    /**
     * Constructor
     * Require ISavedAccountsView
     */
    public SavedAccountsPresenter(ISavedAccountsView view) {
        mView = view;

        mAccountsDatabase = new AccountsDatabase(view.getContext());

        //Call the initial setup methods on View layer
        mView.setUpView();
        mView.setUpOrRefreshRecyclerView(mAccountsDatabase.getAllAccounst());
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
        mAccountsDatabase.insertAccount(username, password);
        mView.setUpOrRefreshRecyclerView(mAccountsDatabase.getAllAccounst());
        Toast.makeText(mView.getContext(), mAccountsDatabase.getAllAccounst().size() + "", Toast.LENGTH_SHORT).show();
        return false;
    }
}
