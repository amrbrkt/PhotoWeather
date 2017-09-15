package barakat.amr.photoweather.weather;

import android.app.Activity;
import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;

import barakat.amr.photoweather.data.model.Weather;

public class ImageWeatherContract {

    interface Presenter {
        void attachView(View view);

        void getLocation(Activity activity);

        void getWeatherOf(double lat, double lon);
    }

    interface View {
        void showLoading(boolean isLoading);

        void onLocationClient(GoogleApiClient googleApiClient);

        void onWeatherUpdate(Weather weather);

        void onWeatherFailed(String cause);
    }
}
