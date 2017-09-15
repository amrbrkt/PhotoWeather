package barakat.amr.photoweather.weather;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class ImageWeatherUtils {
    public Bitmap combineImages(Activity context, Bitmap background, Bitmap foreground) {

        int width = 0, height = 0;
        Bitmap cs;

        width = context.getWindowManager().getDefaultDisplay().getWidth();
        height = context.getWindowManager().getDefaultDisplay().getHeight();
        Matrix matrix = null;
        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas comboImage = new Canvas(cs);
        background = Bitmap.createScaledBitmap(background, width, height, true);
        comboImage.drawBitmap(background, 0, 0, null);
        comboImage.drawBitmap(foreground, matrix, null);

        return cs;
    }
}
