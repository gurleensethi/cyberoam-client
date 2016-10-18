package app.com.thetechnocafe.cyberoamclient.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.thetechnocafe.cyberoamclient.R;

/**
 * Created by gurleensethi on 18/10/16.
 */

public class LoginFragment extends Fragment implements ILoginView {

    private LoginPresenter mLoginPresenter;


    public static LoginFragment getInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mLoginPresenter = new LoginPresenter(this);

        return view;
    }


    /**
     * All the on click listeners are set up here
     * LoginPresenter calls this function on view*/
    @Override
    public void setUpOnClickListeners() {

    }


    /**
     * Handle the login response from presenter
     */
    @Override
    public void isLoginSuccessful(boolean success, String responseToDisplay) {

    }
}
