package app.com.thetechnocafe.cyberoamclient.SavedAccounts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.com.thetechnocafe.cyberoamclient.R;

public class SavedAccountsActivity extends AppCompatActivity implements ISavedAccountsView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_accounts);
    }
}
