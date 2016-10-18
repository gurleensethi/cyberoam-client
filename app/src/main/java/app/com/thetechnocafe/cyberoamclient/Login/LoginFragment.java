package app.com.thetechnocafe.cyberoamclient.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
        //Check if login is successful
        if (success) {
            //TODO:Handle login here
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
            }
        }
    }
}
