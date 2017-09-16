package barakat.amr.photoweather.imagecapture;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class ImageCaptureUtils {

    private static final String IMAGE_DIRECTORY_NAME = "PhotoWeather";

    public static boolean isDeviceSupportCamera(Context context) {
        return context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }

    public static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    public static File getImageDirectory(){
        return new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = getImageDirectory();

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    public static List<String> getPaths() {
        File path = getImageDirectory();
        if (path.exists()) {
            String[] fileNames = path.list();
            return Arrays.asList(fileNames);
        }
        return null;
    }
}
