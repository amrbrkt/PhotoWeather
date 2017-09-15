package barakat.amr.photoweather.weather;

import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;

import barakat.amr.photoweather.Constants;
import barakat.amr.photoweather.R;
import barakat.amr.photoweather.base.BaseActivity;
import butterknife.BindView;


public class ImageWeatherActivity extends BaseActivity {
    @BindView(R.id.imageView)
    SimpleDraweeView imageView;

    @Override
    protected int getActivityTitle() {
        return 0;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_weather;
    }

    @Override
    protected boolean isUpEnabled() {
        return true;
    }

    @Override
    protected void afterActivityInflation() {
        if (getIntent() != null) {
            Uri fileUri = Uri.parse(getIntent().getStringExtra(Constants.IMAGE_URI));
            imageView.setImageURI(fileUri);
        }
    }
}
