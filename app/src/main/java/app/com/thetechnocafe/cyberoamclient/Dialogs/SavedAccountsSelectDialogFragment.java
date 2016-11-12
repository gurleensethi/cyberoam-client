package app.com.thetechnocafe.cyberoamclient.Dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.thetechnocafe.cyberoamclient.R;

/**
 * Created by gurleensethi on 13/11/16.
 */

public class SavedAccountsSelectDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_saved_accounts, container, false);

        return view;
    }
}
