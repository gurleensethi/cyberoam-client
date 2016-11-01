package app.com.thetechnocafe.cyberoamclient.Common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gurleensethi on 01/11/16.
 */

public class AccountsDatabase {
    private DatabaseHelper mDatabaseHelper;

    public AccountsDatabase(Context context) {
        mDatabaseHelper = new DatabaseHelper(context);
    }

    //Run a insert query in database(Accounts table)
    public boolean insertAccount(String username, String password) {
        //Get write-able database
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();

        //Check if already exists
        String sql = "SELECT * FROM " + DatabaseHelper.ACCOUNTS_TABLE + " WHERE " + DatabaseHelper.ACCOUNTS_ID + " = " + username;
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            return false;
        }

        //Create content values
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ACCOUNTS_ID, username);
        contentValues.put(DatabaseHelper.ACCOUNTS_PASSWORD, password);

        //Write data
        database.insert(DatabaseHelper.ACCOUNTS_TABLE, null, contentValues);

        //Close database
        database.close();

        return true;
    }

    //Get account corresponding a username
    public AccountsModel getModelWithUsername(String username) {
        //Get write-able database
        SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + DatabaseHelper.ACCOUNTS_TABLE + " WHERE " + DatabaseHelper.ACCOUNTS_ID + " = " + username;

        //Run query and get cursor
        Cursor cursor = database.rawQuery(sql, null);

        AccountsModel accountsModel = new AccountsModel();

        while (cursor.moveToNext()) {
            String account_username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ACCOUNTS_ID));
            String account_password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ACCOUNTS_PASSWORD));

            accountsModel.setPassword(account_password);
            accountsModel.setUsername(account_username);
        }

        //Close database
        database.close();

        return accountsModel;
    }

    //Get account list
    public List<AccountsModel> getAllAccounst() {
        List<AccountsModel> accountsList = new ArrayList<>();

        //Get database
        SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + DatabaseHelper.ACCOUNTS_TABLE;

        Cursor cursor = database.rawQuery(sql, null);

        //Prepare list
        while (cursor.moveToNext()) {
            String account_username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ACCOUNTS_ID));
            String account_password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ACCOUNTS_PASSWORD));

            AccountsModel accountsModel = new AccountsModel();
            accountsModel.setUsername(account_username);
            accountsModel.setPassword(account_password);

            accountsList.add(accountsModel);
        }

        //Close database
        database.close();

        return accountsList;
    }

    //Update account
    public void updateAccount(String username, String password) {
        //Get database
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();

        String sql = "UPDATE " + DatabaseHelper.ACCOUNTS_TABLE +
                " SET " +
                DatabaseHelper.ACCOUNTS_ID + " = " + username + ", " +
                DatabaseHelper.ACCOUNTS_PASSWORD + " = " + password +
                " WHERE " + DatabaseHelper.ACCOUNTS_ID + "=" + username;

        //Close database
        database.close();
    }

    //Delete account
    public void deleteAccount(String username) {
        //Get database
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();

        String sql = "DELETE FROM " + DatabaseHelper.ACCOUNTS_TABLE + " WHERE " + DatabaseHelper.ACCOUNTS_ID + " = " + username;

        database.execSQL(sql);

        //Close database
        database.close();

    }
}
