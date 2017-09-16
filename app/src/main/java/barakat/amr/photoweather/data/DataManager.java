package barakat.amr.photoweather.data;

import android.content.Context;
import android.net.Uri;

import java.io.FileNotFoundException;

import barakat.amr.photoweather.data.model.Weather;
import barakat.amr.photoweather.imagecapture.ImageCaptureContract;
import barakat.amr.photoweather.imageweather.ImageWeatherContract;

public interface DataManager {
    void attachCapturePresenter(ImageCaptureContract.Presenter presenter);

    void attachWeatherPresenter(ImageWeatherContract.Presenter presenter);

    void readSavedImages();

    void getWeather(double lat, double lon);

    void writeImageFile(Context context, int type);

    void drawOnImage(Context context, Weather weather, Uri fileUri) throws FileNotFoundException;

    void onDrawnOut(Uri fileUri);
}
