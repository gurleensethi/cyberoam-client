package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.RealmDatabase;
import app.com.thetechnocafe.cyberoamclient.Models.AccountsModel;

/**
 * Created by gurleensethi on 22/11/16.
 */

public abstract class ContinuousLoginUtils {

    public abstract void onLoginResult(boolean success, int resultCode, boolean isLast, int position, String username, String password);

    /**
     * Login using all accounts
     * Notify in account login
     * Notify on is last account
     */
    public void continuousLogin(Context context, final int position) {

        //Get list of all accounts
        List<AccountsModel> list = RealmDatabase.getInstance(context).getAllAccounts();

        //If no saved accounts, return with error
        if (list.size() == 0) {
            onLoginResult(false, ValueUtils.ERROR_NO_SAVED_ACCOUNTS, true, 0, null, null);
            return;
        }

        //Check if is last account
        final boolean isLast = ((position + 1) == list.size());

        //Get the accounts model
        final AccountsModel model = list.get(position);

        new NetworkUtils(null) {
            @Override
            public void onResultReceived(boolean success, int errorCode) {
                //Notify about result
                onLoginResult(success, errorCode, isLast, position, model.getUsername(), model.getPassword());
            }
        }.login(context, model.getUsername(), model.getPassword());
    }
}
