package app.com.thetechnocafe.cyberoamclient.Dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.thetechnocafe.cyberoamclient.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 13/11/16.
 */

public class SimpleMessageDialogFragment extends DialogFragment {
    private static final String TITLE_TEXT_CODE = "title_text_code";
    private static final String MESSAGE_TEXT_CODE = "message_text_code";
    private static final String IMAGE_ID_CODE = "image_id_code";
    @BindView(R.id.title_text_view)
    TextView mTitleTextView;
    @BindView(R.id.message_text_view)
    TextView mMessageTextView;
    @BindView(R.id.alert_image_view)
    ImageView mAlertImageView;
    @BindView(R.id.ok_button)
    Button mOkButton;

    public static SimpleMessageDialogFragment getInstance(int imageResId, String title, String message) {
        //Create arguments
        Bundle args = new Bundle();
        args.putString(TITLE_TEXT_CODE, title);
        args.putString(MESSAGE_TEXT_CODE, message);
        args.putInt(IMAGE_ID_CODE, imageResId);

        //Set arguments
        SimpleMessageDialogFragment fragment = new SimpleMessageDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_simple_message, container, false);

        //Bind butter knife
        ButterKnife.bind(this, view);

        setCancelable(false);

        setUpOnClickListeners();
        setUpData();

        return view;
    }

    //Set up onClickListeners
    private void setUpOnClickListeners() {
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    //Set the required data to views
    private void setUpData() {
        //Check for title
        if (getArguments().getString(TITLE_TEXT_CODE) == null) {
            mTitleTextView.setVisibility(View.GONE);
        } else {
            mTitleTextView.setText(getArguments().getString(TITLE_TEXT_CODE));
        }

        //Check for message
        if (getArguments().getString(MESSAGE_TEXT_CODE) == null) {
            mMessageTextView.setVisibility(View.GONE);
        } else {
            mMessageTextView.setText(getArguments().getString(MESSAGE_TEXT_CODE));
        }

        //Check if image is required
        if (getArguments().getInt(IMAGE_ID_CODE) == 0) {
            mAlertImageView.setVisibility(View.GONE);
        } else {
            mAlertImageView.setImageResource(getArguments().getInt(IMAGE_ID_CODE));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //Make dialog match parent
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
