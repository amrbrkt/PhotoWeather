package barakat.amr.photoweather.imagecapture;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.util.List;

public interface ImageCaptureContract {

    interface Presenter {
        void attachView(View view);

        void checkCamera(Activity activity);

        void openCamera(Activity activity, Uri fileUri);

        void startWeatherActivity(Activity activity, Uri fileUri);

        void getSavedImages(Context context);

        void onLocalDataLoaded(List<String> paths);

        void returnMediaFile(Uri uri);

        void requestPermissions(Activity activity);
    }

    interface View {
        void onCaptureReady(boolean isReady, Uri fileUri);

        void onLocalDataLoaded(List<String> paths);

        void onLocalDataIsEmpty();
    }
}

