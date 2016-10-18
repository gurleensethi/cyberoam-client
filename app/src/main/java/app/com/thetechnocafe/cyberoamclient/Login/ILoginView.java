package app.com.thetechnocafe.cyberoamclient.Login;

import android.content.Context;

/**
 * Created by gurleensethi on 18/10/16.
 */

public interface ILoginView {
    void isLoginSuccessful(boolean success, int errorCode);

    void setUpOnClickListeners();

    Context getContext();
}
