package barakat.amr.photoweather.capture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class CaptureImagePresenter implements CaptureImageContract.Presenter {

    private CaptureImageContract.View view;

    @Override
    public void attachView(CaptureImageContract.View view) {
        this.view = view;
    }

    @Override
    public void captureImageRequest(Context context) {
        if (ImageUtils.isDeviceSupportCamera(context)) {
            Uri fileUri = ImageUtils.getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            view.onCaptureReady(true, fileUri);
        } else {
            view.onCaptureReady(true, null);
        }
    }

    @Override
    public void captureImageOnUri(Uri fileUri) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
            view.onCaptureSuccess(bitmap);
        } catch (NullPointerException e) {
            view.onCaptureFailure(e.getMessage());
        }
    }
}
