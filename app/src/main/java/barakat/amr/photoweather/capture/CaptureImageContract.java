package barakat.amr.photoweather.capture;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

public interface CaptureImageContract {

    interface Presenter {
        void attachView(View view);

        void captureImageRequest(Context context);

        void captureImageOnUri(Uri fileUri);
    }

    interface View {
        void onCaptureReady(boolean isReady, Uri fileUri);

        void onCaptureSuccess(Bitmap bitmap);

        void onCaptureFailure(String cause);
    }
}

