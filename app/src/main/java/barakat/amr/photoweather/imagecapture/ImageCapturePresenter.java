package barakat.amr.photoweather.imagecapture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.List;

import barakat.amr.photoweather.Constants;
import barakat.amr.photoweather.ImageFileUtils;
import barakat.amr.photoweather.data.AppDataManager;
import barakat.amr.photoweather.imageweather.ImageWeatherActivity;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class ImageCapturePresenter implements ImageCaptureContract.Presenter {

    private ImageCaptureContract.View view;

    public ImageCapturePresenter() {
        AppDataManager.getInstance().attachCapturePresenter(this);
    }

    @Override
    public void attachView(ImageCaptureContract.View view) {
        this.view = view;
    }

    @Override
    public void captureImageRequest(Context context) {
        if (ImageFileUtils.isDeviceSupportCamera(context)) {
            AppDataManager.getInstance().writeImageFile(MEDIA_TYPE_IMAGE);
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

    @Override
    public void getSavedImages(Context context) {
        AppDataManager.getInstance().readSavedImages();
    }

    @Override
    public void onLocalDataLoaded(List<String> paths) {
        if (paths != null && paths.size() > 0) {
            view.onLocalDataLoaded(paths);
        } else {
            view.onLocalDataIsEmpty();
        }
    }

    @Override
    public void returnMediaFile(Uri uri) {
        if (uri != null) {
            view.onCaptureReady(true, uri);
        } else {
            view.onCaptureReady(true, null);
        }
    }
}
