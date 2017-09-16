package barakat.amr.photoweather;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;

import java.io.File;
import java.io.InputStream;

public class ImageFileUtils {
    public static boolean isDeviceSupportCamera(Activity context) {
        return context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }


    public static File getImageDirectory() {
        return new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Constants.IMAGE_DIRECTORY_NAME);
    }

    public static Bitmap writeOnImage(InputStream stream, String temp, String condition, String location) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 2;

        Bitmap bm = BitmapFactory.decodeStream(stream, null, option);

        Bitmap.Config config = bm.getConfig();
        if (config == null) {
            config = android.graphics.Bitmap.Config.RGB_565;
        }

        Bitmap newImage = bm.copy(config, true);

        Canvas c = new Canvas(newImage);
        c.drawBitmap(bm, 0, 0, null);

        Paint tempPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tempPaint.setColor(Color.WHITE);
        tempPaint.setStyle(Paint.Style.FILL);
        tempPaint.setTextSize(newImage.getWidth() / 8);
        tempPaint.setShadowLayer(3f, -2f, 2f, Color.BLACK);

        Rect tempRect = new Rect();
        tempPaint.getTextBounds(temp, 0, temp.length(), tempRect);
        int x = 25;
        int y = (newImage.getHeight() - tempRect.height() * 3);
        c.drawText(temp + "\u00b0 C", x, y, tempPaint);


        Paint conditionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        conditionPaint.setColor(Color.WHITE);
        conditionPaint.setStyle(Paint.Style.FILL);
        conditionPaint.setTextSize(newImage.getWidth() / 16);
        conditionPaint.setShadowLayer(3f, -2f, 2f, Color.BLACK);

        Rect conditionRect = new Rect();
        conditionPaint.getTextBounds(condition, 0, condition.length(), conditionRect);
        int conditionX = 25;
        int conditionY = y + tempRect.height();
        c.drawText(condition, conditionX, conditionY, conditionPaint);


        Paint locationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        locationPaint.setColor(Color.WHITE);
        locationPaint.setStyle(Paint.Style.FILL);
        locationPaint.setTextSize(newImage.getWidth() / 16);
        locationPaint.setShadowLayer(3f, -2f, 2f, Color.BLACK);

        Rect locationRect = new Rect();

        locationPaint.getTextBounds(location, 0, location.length(), locationRect);
        int locationX = conditionX + conditionRect.width() + 25;
        int locationY = conditionY;
        c.drawText("@" + location, locationX, locationY, locationPaint);

        return newImage;
    }
}
