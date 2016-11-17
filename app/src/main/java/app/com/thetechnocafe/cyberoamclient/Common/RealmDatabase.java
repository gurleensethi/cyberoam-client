package app.com.thetechnocafe.cyberoamclient.Common;

import android.content.Context;

import app.com.thetechnocafe.cyberoamclient.Models.AccountsModel;
import app.com.thetechnocafe.cyberoamclient.Utils.RealmMigrationUtil;
import app.com.thetechnocafe.cyberoamclient.Utils.ValueUtils;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by gurleensethi on 12/11/16.
 */

public class RealmDatabase {
    private static RealmDatabase mRealmDatabase;
    private Realm mRealm;


    //Singleton class
    private RealmDatabase(Context context) {
        //Set up realm
        Realm.init(context);

        //Create new configuration file
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(ValueUtils.REALM_DATABASE_VERSION)
                .migration(new RealmMigrationUtil())
                .build();

        mRealm = Realm.getInstance(config);
    }

    public static RealmDatabase getInstance(Context context) {
        if (mRealmDatabase == null) {
            mRealmDatabase = new RealmDatabase(context);
        }

        return mRealmDatabase;
    }

    /**
     * Insert a new Account into realm
     * Take username and password
     */
    public boolean insertAccount(final String username, final String password) {
        try {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    //Create accounts model
                    AccountsModel accountsModel = new AccountsModel();
                    accountsModel.setUsername(username);
                    accountsModel.setPassword(password);
                    accountsModel.setAccountValid(true);

                    //Insert into realm
                    realm.insert(accountsModel);
                }
            });
        } catch (RealmPrimaryKeyConstraintException e) {
            //If account already exists return false
            return false;
        }
        //Account added successfully
        return true;
    }

    /**
     * Delete a Account
     * Take username
     */
    public void deleteAccount(final String username) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                AccountsModel model = mRealm.where(AccountsModel.class).equalTo(ValueUtils.REALM_ACCOUNTS_USERNAME, username).findFirst();
                if (model != null) {
                    model.deleteFromRealm();
                }
            }
        });
    }

    /**
     * Get a list of all accounts in database
     * Return a List(RealmResults) of AccountsModel
     */
    public RealmResults<AccountsModel> getAllAccounts() {
        RealmResults<AccountsModel> mList;

        //Run realm transactions
        mRealm.beginTransaction();
        mList = mRealm.where(AccountsModel.class).findAll();
        mRealm.commitTransaction();

        return mList;
    }

    /**
     * Get passwrod for a corresponding username
     */
    public String getPassword(String username) {
        String password = "";

        //Run realm transactions
        mRealm.beginTransaction();
        AccountsModel model = mRealm.where(AccountsModel.class).equalTo(ValueUtils.REALM_ACCOUNTS_USERNAME, username).findFirst();
        if (model != null) {
            password = model.getPassword();
        }
        mRealm.commitTransaction();

        return password;
    }

    /**
     * Change account validation boolean
     */
    public void changeValidation(final String username, final boolean isValid) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                AccountsModel model = mRealm.where(AccountsModel.class).equalTo(ValueUtils.REALM_ACCOUNTS_USERNAME, username).findFirst();
                model.setAccountValid(isValid);
            }
        });
    }
}
