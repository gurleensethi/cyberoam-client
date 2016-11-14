package app.com.thetechnocafe.cyberoamclient.About;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import app.com.thetechnocafe.cyberoamclient.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity implements IAboutView {
    @BindView(R.id.flaticon_credit)
    TextView mFlaticonTextView;
    @BindView(R.id.thetechnocafe_text_view)
    TextView mTheTechnoCafeTextView;

    private IAboutPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //Bind butter knife
        ButterKnife.bind(this);

        mPresenter = new AboutPresenter(this);
        mPresenter.onViewReady();
    }

    @Override
    public void setUpView() {
        //Set up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));


        mFlaticonTextView.setText(
                Html.fromHtml("<div>Icons</a> from <a href=\"http://www.flaticon.com\" title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>")
        );

        setUpOnClickListeners();
    }

    private void setUpOnClickListeners() {
        mFlaticonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.flaticon.com"));
                startActivity(intent);
            }
        });

        mTheTechnoCafeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://thetechnocafe.com"));
                startActivity(intent);
            }
        });
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
