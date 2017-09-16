package barakat.amr.photoweather.imagecapture;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

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
    public void checkCamera(Activity activity) {
        if (ImageFileUtils.isDeviceSupportCamera(activity)) {
            AppDataManager.getInstance().writeImageFile(activity, MEDIA_TYPE_IMAGE);
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
            Log.d("URI", uri.toString());
            view.onCaptureReady(true, uri);
        } else {
            view.onCaptureReady(true, null);
        }
    }

    @Override
    public void requestPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE

                },
                Constants.PERMISSIONS);
    }
}
