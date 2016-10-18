package app.com.thetechnocafe.cyberoamclient.Login;

import android.content.Context;
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
import app.com.thetechnocafe.cyberoamclient.Utils.ValueUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 18/10/16.
 */

public class LoginFragment extends Fragment implements ILoginView {

    private LoginPresenter mLoginPresenter;

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
                //Enable progress bar and disable login button
                mLoadingProgressBar.setVisibility(View.VISIBLE);
                mLoginButton.setVisibility(View.GONE);
                mEnrollmentEditText.setEnabled(false);
                mPasswordEditText.setEnabled(false);

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
                    mErrorTextView.setText("Error in volley");
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

        //Set the progress bar gone and login button visible and enable edit texts
        mLoadingProgressBar.setVisibility(View.GONE);
        mLoginButton.setVisibility(View.VISIBLE);
        mEnrollmentEditText.setEnabled(true);
        mPasswordEditText.setEnabled(true);
    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }
}
