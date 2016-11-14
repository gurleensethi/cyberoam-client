package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.AccountsModel;
import app.com.thetechnocafe.cyberoamclient.Common.RealmDatabase;

/**
 * Created by gurleensethi on 14/11/16.
 */

public abstract class AccountValidator {

    abstract public void onValidationComplete();

    public void isAccountValid(Context context) {
        //Get the list of Accounts
        List<AccountsModel> list = RealmDatabase.getInstance(context).getAllAccounts();
    }
}
