package app.com.thetechnocafe.cyberoamclient.SavedAccounts;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.AccountsModel;
import app.com.thetechnocafe.cyberoamclient.Dialogs.NewAccountDialogFragment;
import app.com.thetechnocafe.cyberoamclient.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedAccountsActivity extends AppCompatActivity implements ISavedAccountsView, NewAccountDialogFragment.IDialogCommunicator {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.new_account_fab)
    FloatingActionButton mNewAccountFAB;
    @BindView(R.id.accounts_recycler_view)
    RecyclerView mSavedAccountsRecyclerView;

    private Adapters.SavedAccountsRecyclerAdapter mSavedAccountsRecyclerAdapter;
    private ISavedAccountsPresenter mPresenter;
    private static final String NEW_ACCOUNT_DIALOG_TAG = "newaccountdialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_accounts);

        //Butterknife bind
        ButterKnife.bind(this);

        mPresenter = new SavedAccountsPresenter(this);
    }

    @Override
    public void setUpView() {
        //Set up toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.saved_accounts));
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));


        //Set up onclick listeners
        mNewAccountFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open up the dialog
                android.app.FragmentManager fragmentManager = getFragmentManager();
                NewAccountDialogFragment dialogFragment = NewAccountDialogFragment.getInstance();
                dialogFragment.show(fragmentManager, NEW_ACCOUNT_DIALOG_TAG);
            }
        });
    }

    @Override
    public void onListDataReceived(List<AccountsModel> list) {

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

    @Override
    public void setUpOrRefreshRecyclerView(List<AccountsModel> list) {
        if (mSavedAccountsRecyclerAdapter == null) {
            mSavedAccountsRecyclerAdapter = new Adapters().new SavedAccountsRecyclerAdapter(getApplicationContext(), list);
            mSavedAccountsRecyclerView.setAdapter(mSavedAccountsRecyclerAdapter);
            mSavedAccountsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        } else {
            //If there is change in data, set the new list and
            // notify recycler view about data change
            mSavedAccountsRecyclerAdapter.updateList(list);
            mSavedAccountsRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onDialogSaveClicked(String username, String password) {
        mPresenter.addNewAccount(username, password);
    }
}
