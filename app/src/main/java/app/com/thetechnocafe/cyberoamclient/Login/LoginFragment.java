package app.com.thetechnocafe.cyberoamclient.Login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.thetechnocafe.cyberoamclient.R;
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
                mLoginPresenter.login(mEnrollmentEditText.getText().toString(), mPasswordEditText.getText().toString());
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
                case ERROR_USERNAME_EMPTY: {
                    mErrorTextView.setText(getString(R.string.username_error));
                    break;
                }
                case ERROR_PASSWORD_EMPTY: {
                    mErrorTextView.setText(getString(R.string.password_error));
                    break;
                }
                case ERROR_USERNAME_PASSWORD: {
                    mErrorTextView.setText(getString(R.string.wrong_password_username));
                    break;
                }
                case ERROR_VOLLEY_ERROR: {
                    mErrorTextView.setText("Error in volley");
                    break;
                }
                case ERROR_SERVER_ACCOUNT_LOCKED: {
                    mErrorTextView.setText(getString(R.string.account_locked));
                    break;
                }
                case ERROR_MAXIMUM_LOGIN_LIMIT: {
                    mErrorTextView.setText(getString(R.string.maximum_login_limit));
                }
            }
        }
    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }
}
