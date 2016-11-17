package app.com.thetechnocafe.cyberoamclient.SessionLog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Models.SessionLogModel;
import app.com.thetechnocafe.cyberoamclient.R;
import app.com.thetechnocafe.cyberoamclient.Utils.TimeUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 17/11/16.
 */

public class Adapters {
    public class SessionLogRecyclerAdapter extends RecyclerView.Adapter<SessionLogRecyclerAdapter.SessionLogRecyclerViewHolder> {
        private Context mContext;
        private List<SessionLogModel> mList;

        public SessionLogRecyclerAdapter(Context context, List<SessionLogModel> list) {
            mContext = context;
            mList = list;
        }

        class SessionLogRecyclerViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.enrollment_text_view)
            TextView mEnrollmentTextView;
            @BindView(R.id.data_consumed_text_view)
            TextView mDataConsumedTextView;
            @BindView(R.id.logged_in_time_text_view)
            TextView mLoggedInTimeTextView;
            @BindView(R.id.duration_text_view)
            TextView mDurationTextView;

            public SessionLogRecyclerViewHolder(View view) {
                super(view);

                //Bind butter knife
                ButterKnife.bind(this, view);
            }

            public void bindData(int position) {
                mEnrollmentTextView.setText(mList.get(position).getUsername());
                mDataConsumedTextView.setText(String.valueOf(mList.get(position).getDataConsumed()));
                mLoggedInTimeTextView.setText(TimeUtils.convertLongToString(mList.get(position).getLoggedInTime()));
                mDurationTextView.setText(TimeUtils.convertLongToDuration(mList.get(position).getLoggedInDuration()));
            }
        }

        @Override
        public SessionLogRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_activity_log, parent, false);
            return new SessionLogRecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SessionLogRecyclerViewHolder holder, int position) {
            holder.bindData(position);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
}
