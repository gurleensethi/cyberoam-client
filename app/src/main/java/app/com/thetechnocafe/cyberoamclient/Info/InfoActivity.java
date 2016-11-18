package app.com.thetechnocafe.cyberoamclient.Info;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.com.thetechnocafe.cyberoamclient.R;

public class InfoActivity extends AppCompatActivity implements IInfoView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    @Override
    public void onViewReady() {

    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
