package app.com.thetechnocafe.cyberoamclient.Settings;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import app.com.thetechnocafe.cyberoamclient.Dialogs.ColorPickerDialog;
import app.com.thetechnocafe.cyberoamclient.R;
import app.com.thetechnocafe.cyberoamclient.Utils.ValueUtils;
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
    @BindView(R.id.clear_logs_button)
    Button mClearLogsButton;
    @BindView(R.id.continuous_login_switch)
    Switch mContinuousLoginSwitch;
    @BindView(R.id.auto_login_switch)
    Switch mWifiAutoLoginSwitch;
    @BindView(R.id.theme_color_change_button)
    Button mThemeColorChangeButton;

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
    public void setUpSettingsState(String ipAddress, String port, boolean notificationsEnabled, boolean isActivityLogEnabled, boolean continuousLogin) {
        mIPAddressTextView.setText(ipAddress);
        mPortTextView.setText(port);
        mNotificationsSwitch.setChecked(notificationsEnabled);
        mActivityLogSwitch.setChecked(isActivityLogEnabled);
        mContinuousLoginSwitch.setChecked(continuousLogin);
    }

    @Override
    public void setUpGeneralSettingsState(boolean isAutoLoginEnabled) {
        mWifiAutoLoginSwitch.setChecked(isAutoLoginEnabled);
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
                mPresenter.changeActivityLogState(b);
            }
        });

        mClearLogsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.cleanActivityLogs();
                Snackbar.make(mClearLogsButton, R.string.all_logs_cleared, Snackbar.LENGTH_SHORT).show();
            }
        });

        mContinuousLoginSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mPresenter.changeContinuousLogin(b);
            }
        });

        mWifiAutoLoginSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mPresenter.changeWifiAutoLogin(b);
            }
        });

        mIPAddressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditTextDialog(ValueUtils.IP_ADDRESS);
            }
        });

        mPortTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditTextDialog(ValueUtils.PORT);
            }
        });

        mThemeColorChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Launch the color picker dialog
                DialogFragment colorPickerDialog = ColorPickerDialog.getInstance();
                colorPickerDialog.show(getSupportFragmentManager(), "");
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

    /**
     * Show an dialog box with edit text
     */
    private void showEditTextDialog(final String mode) {
        //Create an alert dialog box with edit text
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_text, null);

        //Get edit text and text view
        final EditText editText = (EditText) view.findViewById(R.id.edit_text);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(lp);

        TextView titleText = (TextView) view.findViewById(R.id.title_text_view);
        TextView mCancelTextView = (TextView) view.findViewById(R.id.cancel_button);
        TextView mSaveTextView = (TextView) view.findViewById(R.id.save_button);

        //Configure according to mode
        switch (mode) {
            case ValueUtils.IP_ADDRESS: {
                titleText.setText(R.string.ip_address);
                editText.setText(mIPAddressTextView.getText());
                break;
            }
            case ValueUtils.PORT: {
                titleText.setText(R.string.port);
                editText.setText(mPortTextView.getText());
                break;
            }
        }

        //Set the edit text
        builder.setView(view);

        //Create the dialog box
        final AlertDialog dialog = builder.create();

        mCancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        mSaveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mode) {
                    case ValueUtils.IP_ADDRESS: {
                        mPresenter.changeIPAddress(editText.getText().toString());
                        break;
                    }
                    case ValueUtils.PORT: {
                        mPresenter.changePort(editText.getText().toString());
                        break;
                    }
                }

                //Dismiss the dialog
                dialog.dismiss();

                //Refersh the settings
                mPresenter.onViewReady();
            }
        });

        dialog.show();
    }
}
