package app.com.thetechnocafe.cyberoamclient.SessionLog;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.RealmDatabase;
import app.com.thetechnocafe.cyberoamclient.Models.SessionLogModel;

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
        mView.onViewReady();
    }

    @Override
    public void requestSessionData() {
        //Get list from realm
        List<SessionLogModel> list = RealmDatabase.getInstance(mView.getContext()).getSessionLogs();

        //Give data to view
        mView.onSessionDataReceived(list);
    }
}
