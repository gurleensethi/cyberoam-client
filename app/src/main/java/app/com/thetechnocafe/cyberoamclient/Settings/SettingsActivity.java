package app.com.thetechnocafe.cyberoamclient.Settings;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import app.com.thetechnocafe.cyberoamclient.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements ISettingsView {
    private ISettingsPresenter mPresenter;
    @BindView(R.id.text_ip_address)
    TextView mIPAddressTextView;
    @BindView(R.id.text_port_address)
    TextView mPortTextView;
    @BindView(R.id.notifications_switch)
    Switch mNotificationsSwitch;
    @BindView(R.id.activity_log_switch)
    Switch mActivityLogSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Bind butter knife
        ButterKnife.bind(this);

        mPresenter = new SettingsPresenter(this);
        mPresenter.onViewReady();
    }

    @Override
    public void setUpView() {
        //Set up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));

        setUpOnClickListeners();
    }

    @Override
    public void setUpSettingsState(String ipAddress, String port, boolean notificationsEnabled, boolean isActivityLogEnabled) {
        mIPAddressTextView.setText(ipAddress);
        mPortTextView.setText(port);
        mNotificationsSwitch.setChecked(notificationsEnabled);
        mActivityLogSwitch.setChecked(isActivityLogEnabled);
    }

    //Set up onclickListeners
    private void setUpOnClickListeners() {
        mNotificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPresenter.changeNotificationsState(isChecked);
            }
        });

        mActivityLogSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
