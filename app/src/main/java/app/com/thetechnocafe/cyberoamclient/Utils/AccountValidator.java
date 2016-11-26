package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Models.AccountsModel;
import app.com.thetechnocafe.cyberoamclient.Common.RealmDatabase;
import app.com.thetechnocafe.cyberoamclient.Common.VolleyRequestQueue;

/**
 * Created by gurleensethi on 14/11/16.
 */

public abstract class AccountValidator {

    abstract public void onValidationComplete(boolean isSuccessful);

    public void validateAccounts(final Context context) {
        //Get the list of Accounts
        final List<AccountsModel> list = RealmDatabase.getInstance(context).getAllAccounts();

        //Check if list is empty
        if (list.size() == 0) {
            onValidationComplete(true);
        }

        //Iterate over the list for validation
        for (int count = 0; count < list.size(); count++) {
            //Retrieve particular username and passwords
            final String username = list.get(count).getUsername();
            final String password = list.get(count).getPassword();
            final int position = count;

            //Create new Network request
            new NetworkUtils(ValueUtils.VOLLEY_ACCOUNT_VALIDITY_TAG) {
                @Override
                public void onResultReceived(boolean success, String message) {

                    //If cannot reach cyberoam, cancel all requests
                    if (message.equals(ValueUtils.ERROR_VOLLEY_ERROR)) {
                        //Cannot reach cyberoam
                        VolleyRequestQueue.getInstance(context).getRequestQueue().cancelAll(ValueUtils.VOLLEY_ACCOUNT_VALIDITY_TAG);
                        onValidationComplete(false);
                    } else if (!success && message.contains("system could not log you on")) {
                        //Change validation status to false
                        RealmDatabase.getInstance(context).changeValidation(username, false);
                        //Check if last
                        if (position + 1 == list.size()) {
                            onValidationComplete(true);
                            loginIfWasLoggedIn(context);
                        }
                    } else {
                        //Change validation status to true
                        RealmDatabase.getInstance(context).changeValidation(username, true);

                        //If valid then account might get logged, log it out
                        new NetworkUtils(null) {
                            @Override
                            public void onResultReceived(boolean success, String message) {

                            }
                        }.logout(context, username, password);

                        //Check if last
                        if (position + 1 == list.size()) {
                            onValidationComplete(true);
                            loginIfWasLoggedIn(context);
                        }
                    }
                }
            }.login(context, username, password);
        }
    }

    /**
     * If the user had already logged in with a account and
     * the account exits in the saved accounts, log in it again because
     * it got logged out while checking account validity
     */
    private void loginIfWasLoggedIn(final Context context) {
        if (SharedPreferenceUtils.getLoginState(context).equals(ValueUtils.STATE_LOGGED_IN)) {
            new NetworkUtils(null) {
                @Override
                public void onResultReceived(boolean success, String message) {
                    //If unable to log in, change state to logged out
                    if (success != true) {
                        SharedPreferenceUtils.changeLoginState(context, ValueUtils.STATE_LOGGED_OUT);
                    }
                }
            }.login(context, SharedPreferenceUtils.getUsername(context), SharedPreferenceUtils.getPassword(context));
        }
    }
}
