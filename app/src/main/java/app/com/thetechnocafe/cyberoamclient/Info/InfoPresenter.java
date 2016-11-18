package app.com.thetechnocafe.cyberoamclient.Info;

/**
 * Created by gurleensethi on 19/11/16.
 */

public class InfoPresenter implements IInfoPresenter {
    private IInfoView mView;

    public InfoPresenter(IInfoView view) {
        mView = view;
    }

    @Override
    public void onViewReady() {
        mView.onViewReady();
    }
}
