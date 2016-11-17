package app.com.thetechnocafe.cyberoamclient.SessionLog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.com.thetechnocafe.cyberoamclient.R;

public class SessionLogActivity extends AppCompatActivity implements ISessionLogView {
    private ISessionLogPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_log);

        mPresenter = new SessionLogPresenter(this);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onViewRead() {

    }

    //Set up onClickListeners
    private void setUpOnClickListeners() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onViewReady();
    }
}
