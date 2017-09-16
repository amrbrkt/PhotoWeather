package barakat.amr.photoweather.imagecapture;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import barakat.amr.photoweather.Constants;
import barakat.amr.photoweather.R;
import barakat.amr.photoweather.base.BaseActivity;
import butterknife.BindView;


public class ImageViewActivity extends BaseActivity {
    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected boolean isUpEnabled() {
        return true;
    }

    @Override
    protected int getActivityTitle() {
        return 0;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_view;
    }

    @Override
    protected void afterActivityInflation() {

        if (getIntent() != null) {
            Uri fileUri = Uri.parse(getIntent().getStringExtra(Constants.IMAGE_URI));
            Glide.with(this)
                    .load(fileUri)
                    .into(imageView);
        }
    }
}
