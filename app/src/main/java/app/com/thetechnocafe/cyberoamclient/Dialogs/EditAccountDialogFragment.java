package app.com.thetechnocafe.cyberoamclient.Dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import app.com.thetechnocafe.cyberoamclient.Common.RealmDatabase;
import app.com.thetechnocafe.cyberoamclient.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 22/11/16.
 */

public class EditAccountDialogFragment extends DialogFragment {
    @BindView(R.id.cancel_button)
    TextView mCancelTextView;
    @BindView(R.id.save_button)
    TextView mSaveTextView;
    @BindView(R.id.enrollment_edit_text)
    EditText mEnrollmentEditText;
    @BindView(R.id.password_edit_text)
    EditText mPasswordEditText;
    @BindView(R.id.title_text_view)
    TextView mTitleTextView;

    private static final String TAG = "DialogFragmnet";
    private static final String USERNAME_ARGS_TAG = "username_args";

    public static EditAccountDialogFragment getInstance(String username) {
        EditAccountDialogFragment fragment = new EditAccountDialogFragment();

        Bundle args = new Bundle();
        args.putString(USERNAME_ARGS_TAG, username);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        setUpData();

        return view;
    }

    private void setUpData() {
        mSaveTextView.setText(getString(R.string.save));
        mTitleTextView.setText(getString(R.string.update_account));
        mEnrollmentEditText.setText(getArguments().getString(USERNAME_ARGS_TAG));
        mEnrollmentEditText.setEnabled(false);

        mPasswordEditText.setText(RealmDatabase.getInstance(getActivity()).getPassword(getArguments().getString(USERNAME_ARGS_TAG)));
    }

    private void setUpOnClickListeners() {
        mCancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cancel the dialog
                getDialog().dismiss();
            }
        });

        mSaveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if enrollment or password if not empty
                if (mEnrollmentEditText.getText().toString().equals("")) {
                    mEnrollmentEditText.requestFocus();
                    return;
                }
                if (mPasswordEditText.getText().toString().equals("")) {
                    mPasswordEditText.requestFocus();
                    mPasswordEditText.setError(getString(R.string.password_error));
                    return;
                }

                //Update the password in database
                RealmDatabase.getInstance(getActivity()).updateAccount(mEnrollmentEditText.getText().toString(), mPasswordEditText.getText().toString());

                //Dismiss the dialog
                getDialog().dismiss();
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
