package parser;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Entities.Serial;

/**
 * Created by asd on 13/12/15.
 */
public class DAO {
    private static final String fileName = "file";

    public ArrayList<Serial> getFollowSerials(Context context) {
        ArrayList<Serial> serials = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            serials = (ArrayList<Serial>) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serials;
    }

    public void setFollowSerials(ArrayList<Serial> serials, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(serials);
            os.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
