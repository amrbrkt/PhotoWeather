package barakat.amr.photoweather.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import barakat.amr.photoweather.Constants;
import barakat.amr.photoweather.DateUtils;
import barakat.amr.photoweather.ImageFileUtils;
import barakat.amr.photoweather.data.model.Weather;
import barakat.amr.photoweather.data.remote.RestClient;
import barakat.amr.photoweather.imagecapture.ImageCaptureContract;
import barakat.amr.photoweather.imageweather.ImageWeatherContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class AppDataManager implements DataManager {
    private static final String TAG = "AppDataManager";
    private static AppDataManager instance;
    private ImageCaptureContract.Presenter capturePresenter;
    private ImageWeatherContract.Presenter weatherPresenter;

    public static AppDataManager getInstance() {
        if (instance == null) {
            instance = new AppDataManager();
        }
        return instance;
    }

    @Override
    public void attachCapturePresenter(ImageCaptureContract.Presenter presenter) {
        capturePresenter = presenter;
    }

    @Override
    public void attachWeatherPresenter(ImageWeatherContract.Presenter presenter) {
        weatherPresenter = presenter;
    }

    @Override
    public void readSavedImages() {
        List<String> paths = new ArrayList<>();
        File path = ImageFileUtils.getImageDirectory();
        if (path.exists()) {
            String[] fileNames = path.list();
            paths = Arrays.asList(fileNames);
        }
        capturePresenter.onLocalDataLoaded(paths);
    }

    @Override
    public void getWeather(double lat, double lon) {
        RestClient.getInstance().getWebService().getWeather(lat, lon, Constants.APP_ID).enqueue(
                new Callback<Weather>() {
                    @Override
                    public void onResponse(Call<Weather> call, Response<Weather> response) {
                        weatherPresenter.onWeatherUpdate(response.body());
                    }

                    @Override
                    public void onFailure(Call<Weather> call, Throwable t) {
                        weatherPresenter.onWeatherUpdate(null);
                        Log.w(TAG, "getWeather onFailure: " + t.getMessage());
                    }
                }
        );
    }

    @Override
    public void writeImageFile(int type) {

        // External sdcard location
        File mediaStorageDir = ImageFileUtils.getImageDirectory();

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(Constants.IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + Constants.IMAGE_DIRECTORY_NAME + " directory");
                capturePresenter.returnMediaFile(null);
            }
        }

        File mediaFile = null;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + DateUtils.getTimeStamp() + ".jpg");
        } else {
            capturePresenter.returnMediaFile(null);
        }

        capturePresenter.returnMediaFile(Uri.fromFile(mediaFile));

    }

    @Override
    public void drawOnImage(Context context, Weather weather, Uri fileUri) throws FileNotFoundException {
        final Bitmap newBitmap = ImageFileUtils.writeOnImage(context.getContentResolver().openInputStream(fileUri),
                String.valueOf(weather.getMain().getTempMax()),
                weather.getWeather().get(0).getMain(),
                weather.getSys().getCountry());
        try {
            BitmapWorkerTask task = new BitmapWorkerTask(fileUri, this);
            task.execute(newBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDrawnOut(Uri fileUri) {
        weatherPresenter.returnAfterDraw(fileUri);
    }
}
