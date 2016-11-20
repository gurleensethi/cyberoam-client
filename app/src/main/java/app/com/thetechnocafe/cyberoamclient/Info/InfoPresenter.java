package app.com.thetechnocafe.cyberoamclient.Info;

import app.com.thetechnocafe.cyberoamclient.Utils.StatsUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.TimeUtils;

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
                TimeUtils.convertLongToDuration(StatsUtils.getTotalDurationLoggedIn(mView.getContext())),
                StatsUtils.getTotalDataConsumed(mView.getContext())
        );

        //Set the Today data char
        mView.setUpTodayChart(
                null,
                StatsUtils.getTotalDataUsedToday(mView.getContext())
        );
    }
}
