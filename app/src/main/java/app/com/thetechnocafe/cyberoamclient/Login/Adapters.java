package app.com.thetechnocafe.cyberoamclient.Login;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.AccountsModel;
import app.com.thetechnocafe.cyberoamclient.R;
import app.com.thetechnocafe.cyberoamclient.SavedAccounts.SavedAccountsActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 30/10/16.
 */

public class Adapters {
    public class NavigationRecyclerAdapter extends RecyclerView.Adapter<NavigationRecyclerAdapter.NavigationViewHolder> {
        private String[] navigationItems;
        private Context mContext;

        public NavigationRecyclerAdapter(Context context) {
            mContext = context;
            navigationItems = context.getResources().getStringArray(R.array.navigation_options);
        }

        //Inner view holder
        class NavigationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            @BindView(R.id.option_name_text_view)
            TextView mOptionsText;
            @BindView(R.id.options_image_view)
            ImageView mOptionsImage;

            public NavigationViewHolder(View view) {
                super(view);
                //Bind with butter knife
                ButterKnife.bind(this, view);
                view.setOnClickListener(this);
            }

            public void bindData(int position) {
                //Set the text
                mOptionsText.setText(navigationItems[position]);

                //Set corresponding image
                switch (navigationItems[position]) {
                    case "Saved Accounts": {
                        mOptionsImage.setImageResource(R.drawable.ic_book);
                        break;
                    }
                    case "Settings": {
                        mOptionsImage.setImageResource(R.drawable.ic_settings);
                        break;
                    }
                    case "About": {
                        mOptionsImage.setImageResource(R.drawable.ic_info);
                        break;
                    }
                }
            }

            @Override
            public void onClick(View v) {
                switch (mOptionsText.getText().toString()) {
                    case "Saved Accounts": {
                        Intent intent = new Intent(mContext, SavedAccountsActivity.class);
                        mContext.startActivity(intent);
                        break;
                    }
                }
            }
        }

        @Override
        public NavigationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_navigation_recycler, parent, false);
            return new NavigationViewHolder(view);
        }

        @Override
        public void onBindViewHolder(NavigationViewHolder holder, int position) {
            holder.bindData(position);
        }

        @Override
        public int getItemCount() {
            return navigationItems.length;
        }
    }

    /**
     * Recycler ViewHolder and Adapter for saved accounts in SavedAccountsDialog
     */
    public class DialogSavedAccountsRecyclerAdapter extends RecyclerView.Adapter<DialogSavedAccountsRecyclerAdapter.DialogSavedAccountsViewHolder> {
        private Context mContext;
        private List<AccountsModel> mList;
        private ILoginPresenter mILoginPresenter;
        private DialogFragment mDialogFragment;

        public DialogSavedAccountsRecyclerAdapter(Context context, ILoginPresenter presenter, DialogFragment fragment) {
            mContext = context;
            mList = presenter.getSavedAccounts();
            mILoginPresenter = presenter;
            mDialogFragment = fragment;
        }

        //Inner ViewHolder class
        class DialogSavedAccountsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            @BindView(R.id.account_username_text_view)
            TextView mAccountUsernameTextView;

            public DialogSavedAccountsViewHolder(View view) {
                super(view);

                view.setOnClickListener(this);

                //Butter knife bind
                ButterKnife.bind(this, view);
            }

            public void bindData(int position) {
                mAccountUsernameTextView.setText(mList.get(position).getUsername());
            }

            @Override
            public void onClick(View v) {
                mILoginPresenter.changeSharedUsernameAndPassword(mAccountUsernameTextView.getText().toString());
                mDialogFragment.dismiss();
            }
        }

        @Override
        public DialogSavedAccountsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_saved_accounts, parent, false);
            return new DialogSavedAccountsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DialogSavedAccountsViewHolder holder, int position) {
            holder.bindData(position);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
}
