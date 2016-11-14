package app.com.thetechnocafe.cyberoamclient.Dialogs;

import android.app.DialogFragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.com.thetechnocafe.cyberoamclient.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 15/11/16.
 */

public class CustomProgressDialog extends DialogFragment {
    private static final String MESSAGE_TEXT_CODE = "message_text_code";
    private static final String BACKGROUND_COLOR_CODE = "background_color_code";
    @BindView(R.id.message_text_view)
    TextView mMessageTextView;
    @BindView(R.id.linear_layout)
    LinearLayout mMainLinearLayout;

    public static CustomProgressDialog getInstance(String message, int backgroundColor) {
        //Create arguments
        Bundle args = new Bundle();
        args.putString(MESSAGE_TEXT_CODE, message);
        args.putInt(BACKGROUND_COLOR_CODE, backgroundColor);

        //Set arguments
        CustomProgressDialog fragment = new CustomProgressDialog();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_custom_progress, container, false);

        //Bind butter knife
        ButterKnife.bind(this, view);

        setCancelable(false);

        setUpData();

        return view;
    }

    //Set the required data to views
    private void setUpData() {
        mMessageTextView.setText(getArguments().getString(MESSAGE_TEXT_CODE));
        Drawable drawable = getResources().getDrawable(getArguments().getInt(BACKGROUND_COLOR_CODE));
        mMainLinearLayout.setBackground(drawable);
    }

    @Override
    public void onStart() {
        super.onStart();
        //Make dialog match parent
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
