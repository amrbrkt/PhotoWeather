package barakat.amr.photoweather.weather;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import barakat.amr.photoweather.Constants;
import barakat.amr.photoweather.data.model.Weather;
import barakat.amr.photoweather.data.remote.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageWeatherPresenter implements ImageWeatherContract.Presenter {
    private ImageWeatherContract.View view;

    @Override
    public void attachView(ImageWeatherContract.View view) {
        this.view = view;
    }

    @Override
    public void getLocation(Activity activity) {
        //view.showLoading(true);
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
        view.showLoading(true);
        RestClient.getInstance().getWebService().getWeather(lat, lon, Constants.APP_ID).enqueue(
                new Callback<Weather>() {
                    @Override
                    public void onResponse(Call<Weather> call, Response<Weather> response) {
                        view.showLoading(false);
                        if (response.body() != null) {
                            view.onWeatherUpdate(response.body());
                        } else {
                            view.onWeatherFailed("Failed Response");
                        }
                    }

                    @Override
                    public void onFailure(Call<Weather> call, Throwable t) {
                        view.showLoading(false);
                        view.onWeatherFailed(t.getMessage());
                    }
                }
        );
    }
}
