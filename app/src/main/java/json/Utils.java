package json;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.IOException;

public class Utils {
    public static String leseBenutzer(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonString;
    }

    public static void erstelleBenutzer(Context context, String jsonString) throws IOException {
        File rootFolder = context.getExternalFilesDir("app\\src\\main\\assets");
        File jsonFile = new File(rootFolder, "user.json");
        FileWriter writer = new FileWriter(jsonFile);
        writer.write(jsonString);
        writer.close();
    }

    public static void updateBenutzer() {
        //schreibeBenutzerJSON();
    }
}