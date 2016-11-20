package app.com.thetechnocafe.cyberoamclient.SessionLog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Info.InfoActivity;
import app.com.thetechnocafe.cyberoamclient.Models.SessionLogModel;
import app.com.thetechnocafe.cyberoamclient.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SessionLogActivity extends AppCompatActivity implements ISessionLogView {
    private ISessionLogPresenter mPresenter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.session_log_recycler_view)
    RecyclerView mSessionLogRecyclerView;
    @BindView(R.id.session_log_text_view)
    TextView mSessionLogTextView;
    private Adapters.SessionLogRecyclerAdapter mSessionLogRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_log);

        //Bind butter knife
        ButterKnife.bind(this);

        //Set up toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.activity_log));
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));

        mPresenter = new SessionLogPresenter(this);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onViewReady() {
        setUpOnClickListeners();

        //Get data for recycler view
        mPresenter.requestSessionData();
    }

    @Override
    public void onSessionDataReceived(List<SessionLogModel> list, boolean isActivatedInSettings) {
        //If deactivated in settings show message
        if (!isActivatedInSettings) {
            mSessionLogTextView.setText(getString(R.string.activity_log_not_enabled));
        } else {
            setUpOrRefreshRecyclerView(list);
        }
    }

    //Set up onClickListeners
    private void setUpOnClickListeners() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onViewReady();
    }

    //Refresh recycler view
    private void setUpOrRefreshRecyclerView(List<SessionLogModel> list) {
        //Check if list is empty
        if (list.size() > 0) {
            if (mSessionLogRecyclerAdapter == null) {
                mSessionLogRecyclerAdapter = new Adapters().new SessionLogRecyclerAdapter(getContext(), list);
                mSessionLogRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mSessionLogRecyclerView.setAdapter(mSessionLogRecyclerAdapter);
            } else {
                mSessionLogRecyclerAdapter.notifyDataSetChanged();
            }

            //Toggle visibility
            mSessionLogRecyclerView.setVisibility(View.VISIBLE);
            mSessionLogTextView.setVisibility(View.GONE);

            //Add subtitle to toolbar
            getSupportActionBar().setSubtitle(String.format(getString(R.string.total_logs), String.valueOf(list.size())));
        } else {
            mSessionLogTextView.setText(getString(R.string.empty_activity_log));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
            case R.id.menu_info: {
                Intent intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_session_log, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
