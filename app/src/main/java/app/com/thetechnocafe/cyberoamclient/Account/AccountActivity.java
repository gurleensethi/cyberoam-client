package app.com.thetechnocafe.cyberoamclient.Account;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.com.thetechnocafe.cyberoamclient.R;

public class AccountActivity extends AppCompatActivity implements IAccountView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onViewReady() {

    }
}
