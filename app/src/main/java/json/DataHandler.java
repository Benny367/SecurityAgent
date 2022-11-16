package json;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
        return getBenutzer();
    }

    public static void erstelleBenutzer(Benutzer benutzer) {
        getBenutzer().add(benutzer);
        schreibeBenutzerJSON();
    }

    public static void updateBenutzer() {
        schreibeBenutzerJSON();
    }

    /**
     * reads the disciplines from the JSON-File
     */
    private static void leseBenutzerJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get("path")
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Discipline[] disciplines = objectMapper.readValue(jsonData, Discipline[].class);
            for (Discipline d : disciplines) {
                getDisciplineList().add(d);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void schreibeBenutzerJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream;
        Writer fileWriter;

        String bookPath = Config.getProperty("disciplineJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getBenutzer());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static List<Benutzer> getBenutzer() {
        if (benutzerList == null) {
            setBenutzer(new ArrayList<>());
            leseBenutzerJSON();
        }
        return benutzerList;
    }

    private static void setBenutzer(List<Benutzer> benutzerList) {
        DataHandler.benutzerList = benutzerList;
    }
}