package app.com.thetechnocafe.cyberoamclient.Login;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.thetechnocafe.cyberoamclient.R;
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
        class NavigationViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.option_name_text_view)
            TextView mOptionsText;
            @BindView(R.id.options_image_view)
            ImageView mOptionsImage;

            public NavigationViewHolder(View view) {
                super(view);
                //Bind with butterknife
                ButterKnife.bind(this, view);
            }

            public void bindData(int position) {
                //Set the text
                mOptionsText.setText(navigationItems[position]);

                //Set corresponding image
                switch (navigationItems[position]) {
                    case "Saved Accounts": {
                        mOptionsImage.setImageResource(R.drawable.ic_account_balance_wallet_black_24dp);
                        break;
                    }
                    case "Settings": {
                        mOptionsImage.setImageResource(R.drawable.ic_settings_black_24dp);
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
}
