package app.com.thetechnocafe.cyberoamclient.About;

/**
 * Created by gurleensethi on 14/11/16.
 */

public class AboutPresenter implements IAboutPresenter {

    private IAboutView mView;

    public AboutPresenter(IAboutView view) {
        mView = view;
    }

    @Override
    public void onViewReady() {
        mView.setUpView();
    }
}
