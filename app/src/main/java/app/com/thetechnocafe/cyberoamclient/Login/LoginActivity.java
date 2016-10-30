package app.com.thetechnocafe.cyberoamclient.Login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import app.com.thetechnocafe.cyberoamclient.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.navigationDrawer)
    ImageButton mNavigationDrawerButton;
    @BindView(R.id.mainDrawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_recycler_view)
    RecyclerView mNavigationRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind with butterknife
        ButterKnife.bind(this);

        setUpNavigationDrawer();

        //Set up LoginFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.loginFragmentContainer);

        if (fragment == null) {
            fragment = LoginFragment.getInstance();
            fragmentManager.beginTransaction().add(R.id.loginFragmentContainer, fragment).commit();
        }
    }

    //Set up navigation drawer (Image button and ItemOnclickListener)
    private void setUpNavigationDrawer() {
        mNavigationDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open drawer if not open
                if (!mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        //Set up the recycler view for navigation options
        mNavigationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNavigationRecyclerView.setAdapter(new Adapters().new NavigationRecyclerAdapter(this));

    }
}
