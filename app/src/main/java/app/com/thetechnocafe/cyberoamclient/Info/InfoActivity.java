package app.com.thetechnocafe.cyberoamclient.Info;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.com.thetechnocafe.cyberoamclient.R;

public class InfoActivity extends AppCompatActivity implements IInfoView {

    private IInfoPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mPresenter = new InfoPresenter(this);
        mPresenter.onViewReady();
    }

    @Override
    public void onViewReady() {

    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
