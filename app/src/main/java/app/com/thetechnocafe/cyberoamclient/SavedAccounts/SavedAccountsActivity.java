package app.com.thetechnocafe.cyberoamclient.SavedAccounts;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import app.com.thetechnocafe.cyberoamclient.Dialogs.CustomProgressDialog;
import app.com.thetechnocafe.cyberoamclient.Dialogs.NewAccountDialogFragment;
import app.com.thetechnocafe.cyberoamclient.Models.AccountsModel;
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
    @BindView(R.id.no_saved_accounts_text_view)
    TextView mNoSavedAccountsTextView;
    @BindView(R.id.activity_saved_accounts)
    CoordinatorLayout mCoordinatorLayout;

    private Adapters.SavedAccountsRecyclerAdapter mSavedAccountsRecyclerAdapter;
    private ISavedAccountsPresenter mPresenter;
    private static final String NEW_ACCOUNT_DIALOG_TAG = "newaccountdialog";
    private static final String PROGRESS_DIALOG_TAG = "progress_dialog_tag";
    private CustomProgressDialog mProgressDialog;
    private boolean isSavedInstanceSaved;
    private boolean isProgressDissmised;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_accounts);

        //Butter knife bind
        ButterKnife.bind(this);

        mPresenter = new SavedAccountsPresenter(this);
        mPresenter.onViewReady();
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
            case R.id.menu_check_accounts_validity: {
                //Show the progress dialog
                setUpProgressDialog(getString(R.string.validating_accounts));
                isProgressDissmised = false;
                mPresenter.validateAccounts();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setUpOrRefreshRecyclerView(List<AccountsModel> list) {
        if (mSavedAccountsRecyclerAdapter == null) {
            mSavedAccountsRecyclerAdapter = new Adapters().new SavedAccountsRecyclerAdapter(getApplicationContext(), list, mPresenter);
            mSavedAccountsRecyclerView.setAdapter(mSavedAccountsRecyclerAdapter);
            mSavedAccountsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        } else {
            //If there is change in data, set the new list and
            // notify recycler view about data change
            mSavedAccountsRecyclerAdapter.updateList(list);
            mSavedAccountsRecyclerAdapter.notifyDataSetChanged();
        }

        //Hide the text view if accounts exists
        if (list.size() > 0) {
            mNoSavedAccountsTextView.setVisibility(View.GONE);
        } else {
            mNoSavedAccountsTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onValidationComplete(boolean isSuccessful) {
        //Dismiss the progress dialog
        if (!isSavedInstanceSaved)
            mProgressDialog.dismiss();

        isProgressDissmised = true;

        //Notify that cyberoam was unreachable
        if (!isSuccessful) {
            Snackbar.make(mNewAccountFAB, getString(R.string.cyberoam_unreachable), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDialogSaveClicked(String username, String password) {
        mPresenter.addNewAccount(username, password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Get menu inflater and inflate menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_saved_accounts, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //Configure the progress dialog and its properties
    private void setUpProgressDialog(String message) {
        //Show progress dialog
        mProgressDialog = CustomProgressDialog.getInstance(message, R.color.md_green_400);

        //Show the progress dialog
        mProgressDialog.show(getFragmentManager(), PROGRESS_DIALOG_TAG);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        isSavedInstanceSaved = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        isSavedInstanceSaved = false;

        //Dismiss dialog if already running
        if (isProgressDissmised) {
            mProgressDialog.dismiss();
        }
    }
}
