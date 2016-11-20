package app.com.thetechnocafe.cyberoamclient.Info;

import app.com.thetechnocafe.cyberoamclient.Utils.StatsUtils;

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

        //Provide initial data
        mView.setInitialData(
                StatsUtils.getTotalTimesLoggedIn(mView.getContext()),
                StatsUtils.getTotalDurationLoggedIn(mView.getContext()),
                StatsUtils.getTotalDataConsumed(mView.getContext())
        );
    }
}
