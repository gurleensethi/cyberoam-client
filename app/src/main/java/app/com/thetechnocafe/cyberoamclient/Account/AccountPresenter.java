package app.com.thetechnocafe.cyberoamclient.Account;

/**
 * Created by gurleensethi on 15/11/16.
 */

public class AccountPresenter implements IAccountPresenter {
    private IAccountView mView;

    public AccountPresenter(IAccountView view) {
        mView = view;
    }

    @Override
    public void onViewReady() {

    }
}
