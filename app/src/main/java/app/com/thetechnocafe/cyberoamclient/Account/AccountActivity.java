package app.com.thetechnocafe.cyberoamclient.Account;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.com.thetechnocafe.cyberoamclient.R;
import butterknife.BindView;

public class AccountActivity extends AppCompatActivity implements IAccountView {
    private IAccountPresenter mPresenter;
    @BindView(R.id.logged_in_username)
    TextView mLoggedInUsername;
    @BindView(R.id.logout_button)
    Button mLogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mPresenter = new AccountPresenter(this);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onViewReady() {
        setUpOnClickListeners();
    }

    //Set up onClickListeners
    private void setUpOnClickListeners() {
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.logout();
            }
        });
    }

    @Override
    public void setInitialData(String username) {
        mLoggedInUsername.setText(username);
    }

    @Override
    public void onLogout() {
        finish();
    }
}
