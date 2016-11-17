package app.com.thetechnocafe.cyberoamclient.SessionLog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

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
    public void onSessionDataReceived(List<SessionLogModel> list) {
        setUpOrRefreshRecyclerView(list);
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
        if (mSessionLogRecyclerAdapter == null) {
            mSessionLogRecyclerAdapter = new Adapters().new SessionLogRecyclerAdapter(getContext(), list);
            mSessionLogRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mSessionLogRecyclerView.setAdapter(mSessionLogRecyclerAdapter);
        }
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
