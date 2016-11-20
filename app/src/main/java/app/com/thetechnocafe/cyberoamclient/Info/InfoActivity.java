package app.com.thetechnocafe.cyberoamclient.Info;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import app.com.thetechnocafe.cyberoamclient.R;
import butterknife.BindView;

public class InfoActivity extends AppCompatActivity implements IInfoView {

    private IInfoPresenter mPresenter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mPresenter = new InfoPresenter(this);
        mPresenter.onViewReady();
    }

    @Override
    public void onViewReady() {
        //Set up toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.activity_log));
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
