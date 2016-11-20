package app.com.thetechnocafe.cyberoamclient.Info;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import app.com.thetechnocafe.cyberoamclient.R;
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
    TextView mTimesLogedInText;

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
        getSupportActionBar().setTitle(getString(R.string.activity_log));
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void setInitialData(int timesLoggedIn, String durationLoggedIn, double dataConsumed) {
        mTotalDataUsedTextView.setText(String.valueOf(dataConsumed));
        mTimesLogedInText.setText(String.valueOf(timesLoggedIn));
        mTotalLoggedInTime.setText(durationLoggedIn);
    }
}
