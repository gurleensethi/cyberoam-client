package app.com.thetechnocafe.cyberoamclient.Account;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.com.thetechnocafe.cyberoamclient.R;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity implements IAboutView {

    private IAboutPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //Bind butter knife
        ButterKnife.bind(this);

        mPresenter = new AboutPresenter(this);
        mPresenter.onViewReady();
    }

    @Override
    public void setUpView() {

    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
