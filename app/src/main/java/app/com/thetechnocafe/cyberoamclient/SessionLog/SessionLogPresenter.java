package app.com.thetechnocafe.cyberoamclient.SessionLog;

/**
 * Created by gurleensethi on 17/11/16.
 */

public class SessionLogPresenter implements ISessionLogPresenter {
    private ISessionLogView mView;

    public SessionLogPresenter(ISessionLogView view) {
        mView = view;
    }

    @Override
    public void onViewReady() {
        mView.onViewRead();
    }
}
