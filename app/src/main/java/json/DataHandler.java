package json;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataHandler {
    private static List<Benutzer> benutzerList;

    private DataHandler() {
    }

    /**
     * reads all participant
     *
     * @return list of participant
     */
    public static List<Benutzer> leseBenutzer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            leseBenutzerJSON();
        }
        return null;
    }

    public static void erstelleBenutzer(Benutzer benutzer) {
        //getBenutzer().add(benutzer);
        //schreibeBenutzerJSON();
    }

    public static void updateBenutzer() {
        //schreibeBenutzerJSON();
    }

    /**
     * reads the disciplines from the JSON-File
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void leseBenutzerJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get("C:\\Users\\benja\\AndroidStudioProjects\\SecurityAgent\\app\\src\\main\\java\\json\\user.json"));
            ObjectMapper objectMapper = new ObjectMapper();
            Benutzer[] benutzer = objectMapper.readValue(jsonData, Benutzer[].class);
            for (Benutzer b : benutzer) {
                getBenutzer().add(b);
                System.out.println(Arrays.toString(benutzer));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void schreibeBenutzerJSON() {

    }

    private static List<Benutzer> getBenutzer() {
        if (benutzerList == null) {
            setBenutzer(new ArrayList<>());
            //leseBenutzerJSON();
        }
        return benutzerList;
    }

    private static void setBenutzer(List<Benutzer> benutzerList) {
        DataHandler.benutzerList = benutzerList;
    }
}