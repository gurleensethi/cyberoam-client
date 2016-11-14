package app.com.thetechnocafe.cyberoamclient.SavedAccounts;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.AccountsModel;
import app.com.thetechnocafe.cyberoamclient.Common.RealmDatabase;
import app.com.thetechnocafe.cyberoamclient.Utils.AccountValidator;

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
        mView.setUpOrRefreshRecyclerView(getSavedAccounts());
    }

    @Override
    public void validateAccounts() {
        //Call the validation function
        new AccountValidator() {
            @Override
            public void onValidationComplete(boolean isSuccessful) {

                //Notify View on validation complete
                mView.setUpOrRefreshRecyclerView(getSavedAccounts());
                mView.onValidationComplete();
            }
        }.validateAccounts(mView.getContext());
    }

    @Override
    public void getAccountDetails(String username) {

    }

    @Override
    public void deleteAccount(String username) {
        RealmDatabase.getInstance(mView.getContext()).deleteAccount(username);
        mView.setUpOrRefreshRecyclerView(getSavedAccounts());
    }

    public ISavedAccountsView getView() {
        return mView;
    }

    @Override
    public List<AccountsModel> getSavedAccounts() {
        return RealmDatabase.getInstance(mView.getContext()).getAllAccounts();
    }

    @Override
    public void editExistingAccount(String username, String password) {

    }

    @Override
    public boolean addNewAccount(String username, String password) {
        RealmDatabase.getInstance(mView.getContext()).insertAccount(username, password);
        mView.setUpOrRefreshRecyclerView(getSavedAccounts());
        return false;
    }
}
