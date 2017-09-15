package barakat.amr.photoweather.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;

import barakat.amr.photoweather.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {
    protected Activity activity;
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        activity = this;
        ButterKnife.bind(this);
        if (!Fresco.hasBeenInitialized()) {
            Fresco.initialize(this);
        }

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getActivityTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(isUpEnabled());
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        afterActivityInflation();
    }

    protected abstract boolean isUpEnabled();

    protected abstract
    @StringRes
    int getActivityTitle();

    protected abstract
    @LayoutRes
    int getLayoutResource();

    protected abstract void afterActivityInflation();


}
