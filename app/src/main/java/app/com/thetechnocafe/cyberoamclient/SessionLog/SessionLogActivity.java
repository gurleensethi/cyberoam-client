package app.com.thetechnocafe.cyberoamclient.SessionLog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import app.com.thetechnocafe.cyberoamclient.R;
import butterknife.BindView;

public class SessionLogActivity extends AppCompatActivity implements ISessionLogView {
    private ISessionLogPresenter mPresenter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

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
    public void onViewReady() {
        //Set up toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.saved_accounts));
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));

        setUpOnClickListeners();
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
