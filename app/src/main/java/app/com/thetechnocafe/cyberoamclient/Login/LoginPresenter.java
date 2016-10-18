package app.com.thetechnocafe.cyberoamclient.Login;

import android.util.Log;

import app.com.thetechnocafe.cyberoamclient.Utils.NetworkUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.ValueUtils;

/**
 * Created by gurleensethi on 18/10/16.
 */

public class LoginPresenter implements ILoginPresenter {

    private ILoginView mainView;
    private NetworkUtils mNetworkUtils = new NetworkUtils() {
        @Override
        public void onResultReceived(boolean success, int errorCode) {
            Log.d("LoginPresenter", success + " " + errorCode);
            mainView.isLoginSuccessful(success, errorCode);
        }
    };

    /**
     * Constructor
     * Requires a class that implements ILoginView
     */
    public LoginPresenter(ILoginView view) {
        mainView = view;
        view.setUpOnClickListeners();
    }


    /**
     * View calls this function to login
     */
    @Override
    public void login(String username, String password) {
        //Check if fields are not empty
        if (username.equals("")) {
            mainView.isLoginSuccessful(false, ValueUtils.ERROR_USERNAME_EMPTY);
        } else if (password.equals("")) {
            mainView.isLoginSuccessful(false, ValueUtils.ERROR_PASSWORD_EMPTY);
        } else {
            mNetworkUtils.login(mainView.getContext(), username, password);
        }
    }
}
