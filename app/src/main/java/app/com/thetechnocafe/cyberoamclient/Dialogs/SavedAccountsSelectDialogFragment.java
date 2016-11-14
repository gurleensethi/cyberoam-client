package app.com.thetechnocafe.cyberoamclient.Dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.com.thetechnocafe.cyberoamclient.Login.Adapters;
import app.com.thetechnocafe.cyberoamclient.Login.ILoginPresenter;
import app.com.thetechnocafe.cyberoamclient.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 13/11/16.
 */

public class SavedAccountsSelectDialogFragment extends DialogFragment {
    @BindView(R.id.dialog_saved_accounts_recycler_view)
    RecyclerView mSavedAccountsDialogRecyclerView;
    @BindView(R.id.no_saved_accounts_text_view)
    TextView mNoSavedAccountsTextView;

    private Adapters.DialogSavedAccountsRecyclerAdapter mDialogSavedAccountsRecyclerAdapter;
    private static ILoginPresenter mILoginPresenter;

    public static SavedAccountsSelectDialogFragment getInstance(ILoginPresenter presenter) {
        mILoginPresenter = presenter;
        return new SavedAccountsSelectDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_saved_accounts, container, false);

        //Butter knife bind
        ButterKnife.bind(this, view);

        setUpOnClickListeners();
        setUpRecyclerView();

        return view;
    }

    private void setUpOnClickListeners() {

    }

    //Configure recycler view
    private void setUpRecyclerView() {
        mDialogSavedAccountsRecyclerAdapter = new Adapters().new DialogSavedAccountsRecyclerAdapter(getActivity(), mILoginPresenter, this);
        mSavedAccountsDialogRecyclerView.setAdapter(mDialogSavedAccountsRecyclerAdapter);
        mSavedAccountsDialogRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Hide the TextView if accounts exits
        if (mDialogSavedAccountsRecyclerAdapter.getItemCount() > 0) {
            mNoSavedAccountsTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
