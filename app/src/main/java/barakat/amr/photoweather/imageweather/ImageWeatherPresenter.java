package barakat.amr.photoweather.imageweather;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.FileNotFoundException;

import barakat.amr.photoweather.Constants;
import barakat.amr.photoweather.data.AppDataManager;
import barakat.amr.photoweather.data.model.Weather;

public class ImageWeatherPresenter implements ImageWeatherContract.Presenter {
    private ImageWeatherContract.View view;

    public ImageWeatherPresenter() {
        AppDataManager.getInstance().attachWeatherPresenter(this);
    }

    @Override
    public void attachView(ImageWeatherContract.View view) {
        this.view = view;
    }

    @Override
    public void getLocation(Activity activity) {
        view.showLoading(true);
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.PERMISSION_ACCESS_COARSE_LOCATION);
        }
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(activity,
                (GoogleApiClient.ConnectionCallbacks) activity, (GoogleApiClient.OnConnectionFailedListener) activity).addApi(LocationServices.API).build();

        view.onLocationClient(mGoogleApiClient);
    }

    @Override
    public void getWeatherOf(double lat, double lon) {
        AppDataManager.getInstance().getWeather(lat, lon);
    }

    @Override
    public void drawOnImage(Context context, final Uri fileUri, Weather weather) throws FileNotFoundException {
        view.showLoading(true);
        AppDataManager.getInstance().drawOnImage(context, weather, fileUri);
    }

    @Override
    public void onWeatherUpdate(Weather weather) {
        if (weather != null) {
            view.onWeatherUpdate(weather);
        } else {
            view.showLoading(false);
            view.onWeatherFailed("Failed Response");
        }
    }

    @Override
    public void returnAfterDraw(Uri fileUri) {
        view.showLoading(false);
        view.onTextDrawn(fileUri);
    }
}
