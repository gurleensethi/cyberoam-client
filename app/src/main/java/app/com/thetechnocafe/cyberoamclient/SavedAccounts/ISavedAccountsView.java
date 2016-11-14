package app.com.thetechnocafe.cyberoamclient.SavedAccounts;

import android.content.Context;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.AccountsModel;

/**
 * Created by gurleensethi on 02/11/16.
 */

public interface ISavedAccountsView {

    public void setUpView();

    public void onListDataReceived(List<AccountsModel> list);

    public void setUpOrRefreshRecyclerView(List<AccountsModel> list);

    public Context getContext();

    public void onValidationComplete();
}
