package app.com.thetechnocafe.cyberoamclient.Login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import app.com.thetechnocafe.cyberoamclient.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Set up LoginFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.loginFragmentContainer);

        if (fragment == null) {
            fragment = LoginFragment.getInstance();
            fragmentManager.beginTransaction().add(R.id.loginFragmentContainer, fragment).commit();
        }
    }
}
