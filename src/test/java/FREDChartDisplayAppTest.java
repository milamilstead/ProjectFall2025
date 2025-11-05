import static org.junit.jupiter.api.Assertions.*;
import edu.desu.answermap.AnswerMap;
import org.junit.jupiter.api.*;
import java.io.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import edu.desu.answermap.AnswerMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FREDChartDisplayAppTest {

    @Test
    void testFirstObservationInRSAFS() throws Exception {
        Path filePath = Path.of("DataFiles/FREDData-JSON", "RSAFS.json");
        assertTrue(Files.exists(filePath), "RSAFS.json file does not exist");

        // Load and parse the JSON data
        String jsonContent = Files.readString(filePath);
        JSONObject root = new JSONObject(jsonContent);
        JSONArray seriess = root.getJSONArray("seriess");
        assertFalse(seriess.isEmpty(), "seriess array is empty in the JSON document");

        JSONObject seriesObj = seriess.getJSONObject(0);
        assertTrue(seriesObj.has("observations"), "No observations field found in series");

        JSONArray observations = seriesObj.getJSONArray("observations");
        assertFalse(observations.isEmpty(), "observations array is empty");

        JSONObject firstObs = observations.getJSONObject(0);

        // Load expected result from AnswerMap (Answer1)
        AnswerMap answerMap = new AnswerMap();
        String expectedJsonString = answerMap.map.get("Answer1");
        assertNotNull(expectedJsonString, "Expected Answer1 not found in TestExpectedResults.txt");

        JSONObject expectedObj = new JSONObject(expectedJsonString);

        // Compare values from expected object and actual observation
        assertEquals(expectedObj.getString("date"), firstObs.getString("date"), "date does not match");
        assertEquals(expectedObj.getString("realtime_start"), firstObs.getString("realtime_start"), "realtime_start does not match");
        assertEquals(expectedObj.getString("realtime_end"), firstObs.getString("realtime_end"), "realtime_end does not match");
        assertEquals(expectedObj.getString("value"), firstObs.getString("value"), "value does not match");
    }

    @Test
    void testCsvFirstFiveLinesMatchAnswer2() throws Exception {
        AnswerMap answerMap = new AnswerMap();
        String expectedCsvSection = answerMap.map.get("Answer2");
        assertNotNull(expectedCsvSection, "Answer2 key is missing in AnswerMap");
        // Normalize line endings for consistency
        expectedCsvSection = expectedCsvSection.replace("\r\n", "\n").trim();
        // Read first five lines from CSV file
        Path csvPath = Path.of("DataFiles", "FREDData-CSV", "RSAFS.csv");
        assertTrue(Files.exists(csvPath), "CSV file RSAFS.csv does not exist");
        List<String> lines = Files.readAllLines(csvPath);
        assertTrue(lines.size() >= 5, "CSV file does not contain at least 5 lines");
        StringBuilder actualBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            actualBuilder.append(lines.get(i));
            if (i < 4) {
                actualBuilder.append("\n");
            }
        }
        String actualCsvSection = actualBuilder.toString().replace("\r\n", "\n").trim();
        assertEquals(expectedCsvSection, actualCsvSection, "CSV first five lines do not match Answer2 from AnswerMap");
    }
}
