package app.com.thetechnocafe.cyberoamclient.Dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gurleensethi on 13/11/16.
 */

public class SimpleMessageDialogFragment extends DialogFragment {
    private static final String TITLE_TEXT_CODE = 'title_text_code';
    private static final String MESSAGE_TEXT_CODE = "message_text_code";


    public static SimpleMessageDialogFragment getInstance(String title, String message) {
        //Create arguments
        Bundle args = new Bundle();
        args.putString(TITLE_TEXT_CODE, title);
        args.putString(MESSAGE_TEXT_CODE, message);

        //Set arguments
        SimpleMessageDialogFragment fragment = new SimpleMessageDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
