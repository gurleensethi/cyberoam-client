package app.com.thetechnocafe.cyberoamclient.Info;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;

import app.com.thetechnocafe.cyberoamclient.R;
import app.com.thetechnocafe.cyberoamclient.Utils.ValueUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoActivity extends AppCompatActivity implements IInfoView {

    private IInfoPresenter mPresenter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.total_data_used_text_view)
    TextView mTotalDataUsedTextView;
    @BindView(R.id.total_logged_in_time_text_view)
    TextView mTotalLoggedInTime;
    @BindView(R.id.times_logged_in_text_view)
    TextView mTimesLoggedInText;
    @BindView(R.id.total_data_used_today_text_view)
    TextView mTotalDataUsedToday;
    @BindView(R.id.data_used_today_bar_chart)
    BarChart mTodayDataBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //Bind butter knife
        ButterKnife.bind(this);

        mPresenter = new InfoPresenter(this);
        mPresenter.onViewReady();
    }

    @Override
    public void onViewReady() {
        //Set up toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.stats));
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void setInitialData(int timesLoggedIn, String durationLoggedIn, double dataConsumed) {
        mTotalDataUsedTextView.setText(String.valueOf(dataConsumed));
        mTimesLoggedInText.setText(String.valueOf(timesLoggedIn));
        mTotalLoggedInTime.setText(durationLoggedIn);
    }

    @Override
    public void setUpTodayChart(BarData barData, double dataConsumed) {
        mTotalDataUsedToday.setText(String.valueOf(dataConsumed));

        //Configure bar data
        barData.setBarWidth(1.0f);

        //Configure the bar chart
        mTodayDataBarChart.setData(barData);
        mTodayDataBarChart.setVisibleXRange(0, 7);
        Description description = new Description();
        description.setText("");
        mTodayDataBarChart.setDescription(description);

        //Remove the grid and axis line from X-Axis
        XAxis xAxis = mTodayDataBarChart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(false);

        //Remove the grid and axis line from left Y-Axis
        YAxis leftAxis = mTodayDataBarChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);

        //Remove the grid and axis line from right Y-Axis
        YAxis rightAxis = mTodayDataBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawAxisLine(false);

        //Animate the chart
        mTodayDataBarChart.animateXY(ValueUtils.CHART_ANIMATION_DURATION, ValueUtils.CHART_ANIMATION_DURATION);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
