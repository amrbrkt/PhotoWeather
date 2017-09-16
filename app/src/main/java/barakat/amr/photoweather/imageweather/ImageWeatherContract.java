package barakat.amr.photoweather.imageweather;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileNotFoundException;

import barakat.amr.photoweather.data.model.Weather;

public class ImageWeatherContract {

    interface Presenter {
        void attachView(View view);

        void getLocation(Activity activity);

        void getWeatherOf(double lat, double lon);

        void drawOnImage(Context context, Uri fileUri, Weather weather) throws FileNotFoundException;
    }

    public interface View {
        void showLoading(boolean isLoading);

        void onLocationClient(GoogleApiClient googleApiClient);

        void onWeatherUpdate(Weather weather);

        void onWeatherFailed(String cause);

        void onTextDrawn(Uri fileUri);
    }
}
