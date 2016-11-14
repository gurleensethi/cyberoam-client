package app.com.thetechnocafe.cyberoamclient.SavedAccounts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.AccountsModel;
import app.com.thetechnocafe.cyberoamclient.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 04/11/16.
 */

public class Adapters {
    public class SavedAccountsRecyclerAdapter extends RecyclerView.Adapter<SavedAccountsRecyclerAdapter.SavedAccountsViewHolder> {
        private List<AccountsModel> mAccountsModelList;
        private Context mContext;
        private ISavedAccountsPresenter mPresenter;

        public SavedAccountsRecyclerAdapter(Context context, List<AccountsModel> accountsModelList, ISavedAccountsPresenter presenter) {
            mContext = context;
            mAccountsModelList = accountsModelList;
            mPresenter = presenter;
        }

        class SavedAccountsViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.enrollment_text_view)
            TextView mEnrollmentTextView;
            @BindView(R.id.status_indicator_view)
            View mStatusIndicatorView;
            @BindView(R.id.edit_account_image_button)
            ImageButton mEditAccountImageButton;
            @BindView(R.id.delete_account_image_button)
            ImageButton mDeleteAccountImageButton;

            public SavedAccountsViewHolder(View view) {
                super(view);

                //Bind butter knife
                ButterKnife.bind(this, view);

                setOnClickListeners();
            }

            //Bind data
            public void bindData(int position) {
                //Get item from list and bind data
                mEnrollmentTextView.setText(mAccountsModelList.get(position).getUsername());

                //Change the account status
                if (!mAccountsModelList.get(position).isAccountValid()) {
                    mStatusIndicatorView.setBackground(mContext.getResources().getDrawable(R.drawable.circle_red));
                }
            }

            private void setOnClickListeners() {
                mEditAccountImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO:Add functionality here
                        Toast.makeText(mContext, "Add editing functionality here", Toast.LENGTH_SHORT).show();
                    }
                });

                mDeleteAccountImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.deleteAccount(mEnrollmentTextView.getText().toString());
                    }
                });
            }
        }

        @Override
        public SavedAccountsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_saved_accounts, parent, false);
            return new SavedAccountsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SavedAccountsViewHolder holder, int position) {
            holder.bindData(position);
        }

        @Override
        public int getItemCount() {
            return mAccountsModelList.size();
        }

        //Set new list whenever data is changed
        public void updateList(List<AccountsModel> list) {
            mAccountsModelList = list;
        }
    }
}
