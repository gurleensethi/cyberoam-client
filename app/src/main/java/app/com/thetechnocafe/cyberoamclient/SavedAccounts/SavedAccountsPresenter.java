package app.com.thetechnocafe.cyberoamclient.SavedAccounts;

import android.widget.Toast;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.AccountsModel;
import app.com.thetechnocafe.cyberoamclient.Common.RealmDatabase;

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
    }

    @Override
    public void onViewReady() {
        //Call the initial setup methods on View layer
        mView.setUpView();
        mView.setUpOrRefreshRecyclerView(RealmDatabase.getInstance(mView.getContext()).getAllAccounts());
    }

    @Override
    public void getAccountDetails(String username) {

    }

    @Override
    public void deleteAccount(String username) {
        RealmDatabase.getInstance(mView.getContext()).deleteAccount(username);
        mView.setUpOrRefreshRecyclerView(RealmDatabase.getInstance(mView.getContext()).getAllAccounts());
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
        RealmDatabase.getInstance(mView.getContext()).insertAccount(username, password);
        mView.setUpOrRefreshRecyclerView(RealmDatabase.getInstance(mView.getContext()).getAllAccounts());
        Toast.makeText(mView.getContext(), RealmDatabase.getInstance(mView.getContext()).getAllAccounts().size() + "", Toast.LENGTH_SHORT).show();
        return false;
    }
}
