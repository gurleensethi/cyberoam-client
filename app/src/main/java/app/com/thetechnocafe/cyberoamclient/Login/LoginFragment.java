package app.com.thetechnocafe.cyberoamclient.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import app.com.thetechnocafe.cyberoamclient.Account.AccountActivity;
import app.com.thetechnocafe.cyberoamclient.Dialogs.SavedAccountsSelectDialogFragment;
import app.com.thetechnocafe.cyberoamclient.Dialogs.SimpleMessageDialogFragment;
import app.com.thetechnocafe.cyberoamclient.R;
import app.com.thetechnocafe.cyberoamclient.Utils.SharedPreferenceUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.ValueUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 18/10/16.
 */

public class LoginFragment extends Fragment implements ILoginView {

    private static final String TAG = "LoginFragment";
    private ILoginPresenter mLoginPresenter;
    private static final String SAVED_ACCOUNTS_DIALOG_TAG = "saved_accounts_dialog";
    private static final String SIMPLE_DIALOG_TAG = "simple_dialog";

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
    @BindView(R.id.saved_accounts_select_image_button)
    ImageButton mSavedAccountsImageButton;
    @BindView(R.id.continuous_login_text_view)
    TextView mContinuousLoginTextView;

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
                toggleViewStates(false, true);

                //Change Error text
                mErrorTextView.setText(getString(R.string.loggin_in));

                //Send login request to presenter
                mLoginPresenter.login(mEnrollmentEditText.getText().toString(), mPasswordEditText.getText().toString());
            }
        });

        mSavedAccountsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedAccountsSelectDialogFragment dialogFragment = SavedAccountsSelectDialogFragment.getInstance(mLoginPresenter);
                dialogFragment.show(getActivity().getFragmentManager(), SAVED_ACCOUNTS_DIALOG_TAG);
            }
        });
    }

    /**
     * Handle the login response from presenter
     */
    @Override
    public void isLoginSuccessful(boolean success, String message) {
        Log.d("LoginFragment", success + " login fragment " + message);
        //Check if login is successful
        if (success) {
            mErrorTextView.setText("");

            //Set up alarm manager for repeated checks for login
            mLoginPresenter.setUpAlarmManager();

            //Move to Account Activity
            Intent intent = new Intent(getContext(), AccountActivity.class);
            startActivity(intent);
        } else {
            switch (message) {
                case ValueUtils.ERROR_USERNAME_EMPTY: {
                    mErrorTextView.setText(message);
                    mEnrollmentEditText.requestFocus();
                    break;
                }
                case ValueUtils.ERROR_PASSWORD_EMPTY: {
                    mErrorTextView.setText(message);
                    mPasswordEditText.requestFocus();
                    break;
                }
                default: {
                    mErrorTextView.setText(message);
                    break;
                }
            }
        }

        //Change the login state in shared preferences
        mLoginPresenter.setLoginState(success);

        //Set the progress bar gone and login button visible and enable edit texts
        toggleViewStates(success, false);
    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    /**
     * Disable Both edit texts and login, button on login button pressed
     * Enable them back on result received, disable progress bar
     */
    private void toggleViewStates(boolean isLoggedIn, boolean isLoggingIn) {
        if (isLoggedIn) {
            mEnrollmentEditText.setEnabled(false);
            mPasswordEditText.setEnabled(false);
            mLoadingProgressBar.setVisibility(View.GONE);
            mLoginButton.setVisibility(View.GONE);
            mSavedAccountsImageButton.setEnabled(false);
        } else {
            if (isLoggingIn) {
                mEnrollmentEditText.setEnabled(false);
                mPasswordEditText.setEnabled(false);
                mLoadingProgressBar.setVisibility(View.VISIBLE);
                mLoginButton.setVisibility(View.GONE);
                mSavedAccountsImageButton.setEnabled(false);
            } else {
                mEnrollmentEditText.setEnabled(true);
                mPasswordEditText.setEnabled(true);
                mLoadingProgressBar.setVisibility(View.GONE);
                mLoginButton.setVisibility(View.VISIBLE);
                mSavedAccountsImageButton.setEnabled(true);
            }
        }
    }

    /**
     * Called by the presenter
     * Get the saved username password in shared preferences
     */
    @Override
    public void setUpSavedState(String username, String password) {
        mEnrollmentEditText.setText(username);
        mPasswordEditText.setText(password);
        if (SharedPreferenceUtils.getLoginState(getContext()).equals(ValueUtils.STATE_LOGGED_IN)) {
            toggleViewStates(true, false);
        }
    }

    /**
     * Show a dialog on first run (Beta alert)
     */
    @Override
    public void completeFirstRunSetup() {
        SimpleMessageDialogFragment fragment = SimpleMessageDialogFragment.getInstance(R.drawable.ic_welcome, null, getString(R.string.welcome_message));
        fragment.show(getActivity().getFragmentManager(), SIMPLE_DIALOG_TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLoginPresenter.refreshState();
    }

    @Override
    public void onRefreshState(boolean isLoggedIn) {
        toggleViewStates(isLoggedIn, false);

        //If logged in go to account activity
        if (isLoggedIn) {
            Intent intent = new Intent(getContext(), AccountActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void isContinuousLoginEnabled(boolean isEnabled) {
        if (isEnabled) {
            mContinuousLoginTextView.setVisibility(View.VISIBLE);
        } else {
            mContinuousLoginTextView.setVisibility(View.GONE);
        }
    }
}
