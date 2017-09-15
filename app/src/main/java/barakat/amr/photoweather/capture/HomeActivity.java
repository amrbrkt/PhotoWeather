package barakat.amr.photoweather.capture;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import barakat.amr.photoweather.Constants;
import barakat.amr.photoweather.R;
import barakat.amr.photoweather.base.BaseActivity;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements CaptureImageContract.View {

    private CaptureImagePresenter presenter = new CaptureImagePresenter();
    private Uri fileUri;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    protected int getActivityTitle() {
        return R.string.app_name;
    }

    @Override
    protected void afterActivityInflation() {
        presenter.attachView(this);
    }

    @Override
    protected boolean isUpEnabled() {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            presenter.startWeatherActivity(activity, fileUri);
        } else {
            Toast.makeText(activity, "An Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.fab)
    void captureImage() {
        presenter.captureImageRequest(this);
    }

    @Override
    public void onCaptureReady(boolean isReady, Uri fileUri) {
        if (isReady) {
            this.fileUri = fileUri;
            presenter.openCamera(this, fileUri);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
        }
    }
}
