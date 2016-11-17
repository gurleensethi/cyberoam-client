package app.com.thetechnocafe.cyberoamclient.SessionLog;

import android.content.Context;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Models.SessionLogModel;

/**
 * Created by gurleensethi on 17/11/16.
 */

public interface ISessionLogView {
    Context getContext();

    void onViewReady();

    void onSessionDataReceived(List<SessionLogModel> list, boolean isActivatedInSettings);
}
