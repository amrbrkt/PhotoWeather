package barakat.amr.photoweather.capture;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import java.util.List;

public interface CaptureImageContract {

    interface Presenter {
        void attachView(View view);

        void captureImageRequest(Context context);

        void openCamera(Activity activity, Uri fileUri);

        void startWeatherActivity(Activity activity, Uri fileUri);

        void getSavedImages(Context context);
    }

    interface View {
        void onCaptureReady(boolean isReady, Uri fileUri);

        void onLocalDataLoaded(List<String> paths);
    }
}

