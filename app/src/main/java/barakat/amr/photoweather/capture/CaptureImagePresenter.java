package barakat.amr.photoweather.capture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import barakat.amr.photoweather.Constants;
import barakat.amr.photoweather.weather.ImageWeatherActivity;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class CaptureImagePresenter implements CaptureImageContract.Presenter {

    private CaptureImageContract.View view;

    @Override
    public void attachView(CaptureImageContract.View view) {
        this.view = view;
    }

    @Override
    public void captureImageRequest(Context context) {
        if (ImageUtils.isDeviceSupportCamera(context)) {
            Uri fileUri = ImageUtils.getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            view.onCaptureReady(true, fileUri);
        } else {
            view.onCaptureReady(true, null);
        }
    }

    @Override
    public void openCamera(Activity activity, Uri fileUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        activity.startActivityForResult(intent, Constants.CAMERA_REQUEST_CODE);
    }

    @Override
    public void startWeatherActivity(Activity activity, Uri fileUri) {
        Intent intent = new Intent(activity, ImageWeatherActivity.class);
        intent.putExtra(Constants.IMAGE_URI, fileUri.toString());
        activity.startActivity(intent);
    }
}
