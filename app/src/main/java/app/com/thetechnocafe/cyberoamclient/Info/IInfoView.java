package app.com.thetechnocafe.cyberoamclient.Info;

import android.content.Context;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;

/**
 * Created by gurleensethi on 19/11/16.
 */

public interface IInfoView {
    void onViewReady();

    Context getContext();

    void setInitialData(int timesLoggedIn, String durationLoggedIn, double dataConsumed);

    void setUpTodayChart(BarData barData, double dataConsumed);

    void setUpEnrollmentPieChart(PieData pieData, int totalEnrollment);
}
