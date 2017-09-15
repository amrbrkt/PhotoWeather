package barakat.amr.photoweather.capture;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

public interface CaptureImageContract {

    interface Presenter {
        void attachView(View view);

        void captureImageRequest(Context context);

        void openCamera(Activity activity, Uri fileUri);

        void startWeatherActivity(Activity activity, Uri fileUri);
    }

    interface View {
        void onCaptureReady(boolean isReady, Uri fileUri);
    }
}

