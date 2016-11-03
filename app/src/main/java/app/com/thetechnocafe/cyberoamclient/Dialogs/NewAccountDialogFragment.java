package app.com.thetechnocafe.cyberoamclient.Dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import app.com.thetechnocafe.cyberoamclient.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 03/11/16.
 */

public class NewAccountDialogFragment extends DialogFragment {
    @BindView(R.id.cancel_button)
    TextView mCancelTextView;
    @BindView(R.id.save_button)
    TextView mSaveTextView;
    @BindView(R.id.enrollment_edit_text)
    EditText mEnrollmentEditText;
    @BindView(R.id.password_edit_text)
    EditText mPasswordEditText;

    public static NewAccountDialogFragment getInstance() {
        return new NewAccountDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create new View
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_new_account, null);

        //Bind butter knife
        ButterKnife.bind(this, view);

        //Set dialog un-cancelable
        setCancelable(false);

        setUpOnClickListeners();

        return view;
    }

    private void setUpOnClickListeners() {
        mCancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cancel the dialog
                getDialog().dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}