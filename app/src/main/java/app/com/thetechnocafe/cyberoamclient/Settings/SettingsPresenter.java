package app.com.thetechnocafe.cyberoamclient.Settings;

/**
 * Created by gurleensethi on 14/11/16.
 */

public class SettingsPresenter implements ISettingsPresenter{
    private ISettingsView mView;

    public SettingsPresenter(ISettingsView view) {
        mView = view;
    }

    @Override
    public void onViewReady() {
        mView.setUpView();
    }
}
