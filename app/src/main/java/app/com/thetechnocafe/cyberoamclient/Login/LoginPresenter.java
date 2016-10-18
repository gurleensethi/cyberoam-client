package app.com.thetechnocafe.cyberoamclient.Login;

/**
 * Created by gurleensethi on 18/10/16.
 */

public class LoginPresenter implements ILoginPresenter {

    private ILoginView mainView;


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

    }
}
