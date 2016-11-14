package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.AccountsModel;
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

        //Iterate over the list for validation
        for (int count = 0; count < list.size(); count++) {
            //Retrieve particular username and passwords
            final String username = list.get(count).getUsername();
            final String password = list.get(count).getPassword();
            final int position = count;

            //Create new Network request
            new NetworkUtils(ValueUtils.VOLLEY_ACCOUNT_VALIDITY_TAG) {
                @Override
                public void onResultReceived(boolean success, int errorCode) {

                    //If cannot reach cyberoam, cancel all requests
                    switch (errorCode) {
                        case ValueUtils.ERROR_VOLLEY_ERROR: {
                            //Cannot reach cyberoam
                            VolleyRequestQueue.getInstance(context).getRequestQueue().cancelAll(ValueUtils.VOLLEY_ACCOUNT_VALIDITY_TAG);
                            onValidationComplete(false);
                            break;
                        }
                        case ValueUtils.ERROR_USERNAME_PASSWORD: {
                            //Change validation status to false
                            RealmDatabase.getInstance(context).changeValidation(username, false);
                            //Check if last
                            if (position + 1 == list.size()) {
                                onValidationComplete(true);
                            }
                            break;
                        }
                        default: {
                            //Change validation status to true
                            RealmDatabase.getInstance(context).changeValidation(username, true);

                            //If valid then account might get logged, log it out
                            new NetworkUtils(null) {
                                @Override
                                public void onResultReceived(boolean success, int errorCode) {

                                }
                            }.logout(context, username, password);

                            //Check if last
                            if (position + 1 == list.size()) {
                                onValidationComplete(true);
                            }
                        }
                    }
                }
            }.login(context, username, password);
        }
    }
}
