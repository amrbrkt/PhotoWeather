package barakat.amr.photoweather;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import java.io.InputStream;

import barakat.amr.photoweather.base.BaseActivity;
import barakat.amr.photoweather.capture.CaptureImageContract;
import barakat.amr.photoweather.capture.CaptureImagePresenter;
import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements CaptureImageContract.View {
    @BindView(R.id.fab)
    FloatingActionButton addPhotoFAB;
    private int REQUEST_CODE = 555;

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputStream stream = null;
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            presenter.captureImageOnUri(fileUri);
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
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCaptureSuccess(Bitmap bitmap) {
        //imgPreview.setImageBitmap(bitmap);
        Toast.makeText(getApplicationContext(),
                "Preview Captured",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCaptureFailure(String cause) {
        Toast.makeText(getApplicationContext(),
                "Failed Captured " + cause,
                Toast.LENGTH_LONG).show();
    }
}
