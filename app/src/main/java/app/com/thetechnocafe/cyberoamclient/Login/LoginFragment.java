package app.com.thetechnocafe.cyberoamclient.Login;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import app.com.thetechnocafe.cyberoamclient.R;
import app.com.thetechnocafe.cyberoamclient.Utils.NetworkUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.SharedPreferenceUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.ValueUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

import static app.com.thetechnocafe.cyberoamclient.Login.LoginPresenter.BROADCAST_REQUEST_CODE;

/**
 * Created by gurleensethi on 18/10/16.
 */

public class LoginFragment extends Fragment implements ILoginView {

    private static final String TAG = "LoginFragment";
    private ILoginPresenter mLoginPresenter;

    @BindView(R.id.enrollmentEditText)
    EditText mEnrollmentEditText;
    @BindView(R.id.passwordEditText)
    EditText mPasswordEditText;
    @BindView(R.id.loginButton)
    ImageView mLoginButton;
    @BindView(R.id.errorTextView)
    TextView mErrorTextView;
    @BindView(R.id.loadingProgressBar)
    ProgressBar mLoadingProgressBar;

    @BindView(R.id.logoutButton)
    Button mLogoutButton;

    public static LoginFragment getInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //Bind ButterKnife
        ButterKnife.bind(this, view);

        mLoginPresenter = new LoginPresenter(this);

        return view;
    }

    /**
     * All the on click listeners are set up here
     * LoginPresenter calls this function on view
     */
    @Override
    public void setUpOnClickListeners() {
        //Call login in presenter
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Login Button Clicked");
                //Enable progress bar and disable login button
                toggleEditTextStates(false, false);

                //Change Error text
                mErrorTextView.setText(getString(R.string.loggin_in));

                //Send login request to presenter
                mLoginPresenter.login(mEnrollmentEditText.getText().toString(), mPasswordEditText.getText().toString());
            }
        });

        //TODO:Remove this code from final production
        //Temporary logout button
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
                SharedPreferenceUtils.changeLoginState(getContext(), ValueUtils.STATE_LOGGED_OUT);
                toggleEditTextStates(true, false);
                new NetworkUtils() {
                    @Override
                    public void onResultReceived(boolean success, int errorCode) {
                        if (success) {
                            Toast.makeText(getContext(), "Successfully logged out", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error logging out", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.logout(getContext(), mEnrollmentEditText.getText().toString(), mPasswordEditText.getText().toString());
            }
        });
    }

    /**
     * Handle the login response from presenter
     */
    @Override
    public void isLoginSuccessful(boolean success, int errorCode) {
        Log.d("LoginFragment", success + " login fragment " + errorCode);
        //Check if login is successful
        if (success) {
            mErrorTextView.setText(getString(R.string.login_success));

            //Set up alarm manager for repeated checks for login
            mLoginPresenter.setUpAlarmManager();
        } else {
            switch (errorCode) {
                case ValueUtils.ERROR_USERNAME_EMPTY: {
                    mErrorTextView.setText(getString(R.string.username_error));
                    break;
                }
                case ValueUtils.ERROR_PASSWORD_EMPTY: {
                    mErrorTextView.setText(getString(R.string.password_error));
                    break;
                }
                case ValueUtils.ERROR_USERNAME_PASSWORD: {
                    mErrorTextView.setText(getString(R.string.wrong_password_username));
                    break;
                }
                case ValueUtils.ERROR_VOLLEY_ERROR: {
                    mErrorTextView.setText("Cannot reach cyberoam");
                    break;
                }
                case ValueUtils.ERROR_SERVER_ACCOUNT_LOCKED: {
                    mErrorTextView.setText(getString(R.string.account_locked));
                    break;
                }
                case ValueUtils.ERROR_MAXIMUM_LOGIN_LIMIT: {
                    mErrorTextView.setText(getString(R.string.maximum_login_limit));
                }
            }
        }

        //Change the login state in shared preferences
        mLoginPresenter.setLoginState(success);

        //Set the progress bar gone and login button visible and enable edit texts
        toggleEditTextStates(true, success);
    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    /**
     * Disable Both edit texts and login, button on login button pressed
     * Enable them back on result received, disable progress bar
     */
    private void toggleEditTextStates(boolean toggle, boolean isLoggedIn) {
        if (toggle) {
            mLoadingProgressBar.setVisibility(View.GONE);
            mLoginButton.setVisibility(View.VISIBLE);
            mEnrollmentEditText.setEnabled(true);
            mPasswordEditText.setEnabled(true);
        } else {
            mLoadingProgressBar.setVisibility(View.VISIBLE);
            mLoginButton.setVisibility(View.GONE);
            mEnrollmentEditText.setEnabled(false);
            mPasswordEditText.setEnabled(false);
        }

        if (isLoggedIn) {
            mLoadingProgressBar.setVisibility(View.GONE);
            mLoginButton.setEnabled(false);
            mLoginButton.setVisibility(View.GONE);
        } else {
            mLoginButton.setEnabled(true);
        }
    }

    /**
     * Called by the presenter
     * Get the saved username password in shared preferences
     */
    @Override
    public void setUpSavedState() {
        mEnrollmentEditText.setText(SharedPreferenceUtils.getUsername(getContext()));
        mPasswordEditText.setText(SharedPreferenceUtils.getPassword(getContext()));
        if (SharedPreferenceUtils.getLoginState(getContext()).equals(ValueUtils.STATE_LOGGED_IN)) {
            toggleEditTextStates(false, true);
        }
    }

    //TODO:Remove this code from final production
    public void cancelAlarm() {
        //Get alarm manager service
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        //Create a pending intent for broadcast receiver
        Intent intent = new Intent(getContext(), LoginBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), BROADCAST_REQUEST_CODE, intent, 0);

        //Cancel alarms
        alarmManager.cancel(pendingIntent);
    }
}
