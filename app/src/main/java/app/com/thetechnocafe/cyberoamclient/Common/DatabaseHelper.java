package app.com.thetechnocafe.cyberoamclient.Common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gurleensethi on 01/11/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ccdatabase";
    private static final int DATABASE_VERSION = 1;
    public static final String ACCOUNTS_TABLE = "Accounts";
    public static final String ACCOUNTS_ID = "accounts_id";
    public static final String ACCOUNTS_PASSWORD = "accounts_password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAccountsTable = "CREATE TABLE " + ACCOUNTS_TABLE +
                "(" +
                ACCOUNTS_ID + " VARCHAR(10) PRIMARY KEY UNIQUE," +
                ACCOUNTS_PASSWORD + " VARCHAR(24)" +
                ")";

        //Create accounts database table
        db.execSQL(createAccountsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
