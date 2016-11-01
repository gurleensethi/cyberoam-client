package app.com.thetechnocafe.cyberoamclient.SavedAccounts;

/**
 * Created by gurleensethi on 02/11/16.
 */

public class SavedAccountsPresenter implements ISavedAccountsPresenter {

    private ISavedAccountsView mView;

    /**
     * Constructor
     * Require ISavedAccountsView
     * */
    public SavedAccountsPresenter(ISavedAccountsView view) {
        mView = view;
    }


}
