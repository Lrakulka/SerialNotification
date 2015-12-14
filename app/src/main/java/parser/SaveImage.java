package parser;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import Entities.Serial;

/**
 * Created by asd on 13/12/15.
 */
public class SaveImage {
    public void save(Serial serial, ImageView imageView) {
        try {
            serial.setImagePath(saveBitmap(loadBitmapFromView(imageView)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String saveBitmap(Bitmap bm) throws Exception {

        String tempFilePath = "image" + new Random().nextInt(1000) + ".jpg";
        File tempFile = new File(tempFilePath);
        if (!tempFile.exists()) {
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
        }

        tempFile.delete();
        tempFile.createNewFile();

        int quality = 100;
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);

        BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
        bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);

        bos.flush();
        bos.close();

        bm.recycle();

        return tempFilePath;
    }

    public Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        v.invalidate();
        return b;
    }
}
