package app.com.thetechnocafe.cyberoamclient.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import app.com.thetechnocafe.cyberoamclient.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gurleensethi on 13/02/17.
 */

public class ColorPickerDialog extends DialogFragment {
    private static final String MESSAGE_TEXT_CODE = "message_text_code";
    private static final String BACKGROUND_COLOR_CODE = "background_color_code";
    @BindView(R.id.dialog_saved_accounts_recycler_view)
    RecyclerView mDialogColorPickerRecyclerView;

    public static ColorPickerDialog getInstance() {
        return new ColorPickerDialog();
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
