import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import org.json.*;

public class FREDChartDisplayApp {

    private String apiKey;
    private JFrame mainFrame;
    private final String JSON_SAVE_DIR = "DataFiles/FREDData-JSON";
    private final String CSV_SAVE_DIR = "DataFiles/FREDData-CSV";

    public FREDChartDisplayApp() {
        promptForApiKey();
        setupMainWindow();
    }

    private void promptForApiKey() {
        apiKey = JOptionPane.showInputDialog(
                null,
                "Enter your FRED API Key:",
                "FRED API Key Required",
                JOptionPane.QUESTION_MESSAGE
        );
        if (apiKey == null || apiKey.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "API key required to continue.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void setupMainWindow() {
        mainFrame = new JFrame("FREDChartDisplayApp");
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");

        JMenuItem fetchFREDItem = new JMenuItem("Fetch FRED Data");
        JMenuItem loadJSONItem = new JMenuItem("Display Data from File");
        JMenuItem exitItem = new JMenuItem("Exit");

        menu.add(fetchFREDItem);
        menu.add(loadJSONItem);
        menu.addSeparator();
        menu.add(exitItem);

        menuBar.add(menu);
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setSize(450, 180);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel("Select an option from the File menu to begin.", JLabel.CENTER));
        mainFrame.add(panel);

        fetchFREDItem.addActionListener(e -> doFetchAndDisplay());
        loadJSONItem.addActionListener(e -> doLoadAndDisplay());
        exitItem.addActionListener(e -> System.exit(0));

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void doFetchAndDisplay() {
        String seriesId = JOptionPane.showInputDialog(
                mainFrame, "Enter FRED Series ID (e.g., RSAFS):", "Fetch FRED Data", JOptionPane.QUESTION_MESSAGE);

        if (seriesId == null || seriesId.trim().isEmpty()) return;

        try {
            JSONObject seriesMeta = fetchJsonFromFRED("/series", seriesId);
            JSONArray observations = fetchJsonFromFRED("/series/observations", seriesId).getJSONArray("observations");

            // Merge metadata and observations as required
            JSONObject topLevel = new JSONObject();
            JSONArray seriessArr = new JSONArray();
            JSONObject mergedObj = new JSONObject(seriesMeta.toString());
            mergedObj.put("observations", observations);
            seriessArr.put(mergedObj);
            topLevel.put("seriess", seriessArr);

            showTableFromObservations(observations, seriesId);
            saveMerges(seriesId, topLevel, observations);

            JOptionPane.showMessageDialog(mainFrame, "Data fetched and saved as JSON and CSV files.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainFrame,
                    "Error during fetch/merge: " + ex.getMessage(),
                    "Fetch Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JSONObject fetchJsonFromFRED(String endpoint, String seriesId) throws IOException, JSONException {
        String baseUrl = "https://api.stlouisfed.org/fred" + endpoint +
                "?series_id=" + URLEncoder.encode(seriesId, "UTF-8") +
                "&api_key=" + URLEncoder.encode(apiKey, "UTF-8") + "&file_type=json";
        HttpURLConnection conn = (HttpURLConnection) new URL(baseUrl).openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            return new JSONObject(sb.toString());
        }
    }

    private void showTableFromObservations(JSONArray data, String seriesId) {
        String[] columns = {"Date", "Value"};
        String[][] rows = new String[data.length()][2];
        for (int i = 0; i < data.length(); i++) {
            JSONObject obs = data.getJSONObject(i);
            rows[i][0] = obs.optString("date", "");
            rows[i][1] = obs.optString("value", "");
        }
        JTable table = new JTable(rows, columns);

        JFrame frame = new JFrame("FRED Series: " + seriesId);
        frame.add(new JScrollPane(table));
        frame.setSize(650, 400);
        frame.setLocationRelativeTo(mainFrame);
        frame.setVisible(true);
    }

    private void saveMerges(String seriesId, JSONObject merged, JSONArray observations) throws IOException {
        Files.createDirectories(Paths.get(JSON_SAVE_DIR));
        Files.createDirectories(Paths.get(CSV_SAVE_DIR));

        String jsonFile = Paths.get(JSON_SAVE_DIR, seriesId + ".json").toString();
        try (PrintWriter out = new PrintWriter(jsonFile)) {
            out.println(merged.toString(2));
        }

        String csvFile = Paths.get(CSV_SAVE_DIR, seriesId + ".csv").toString();
        try (PrintWriter out = new PrintWriter(csvFile)) {
            out.println("Date," + seriesId + "_Value");
            for (int i = 0; i < observations.length(); i++) {
                JSONObject obj = observations.getJSONObject(i);
                out.println(obj.optString("date", "") + "," + obj.optString("value", ""));
            }
        }
    }

    private void doLoadAndDisplay() {
        JFileChooser chooser = new JFileChooser(JSON_SAVE_DIR);
        if (chooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
            File selFile = chooser.getSelectedFile();
            if (selFile != null && selFile.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(selFile))) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) sb.append(line);
                    JSONObject loaded = new JSONObject(sb.toString());
                    JSONArray seriess = loaded.getJSONArray("seriess");
                    if (seriess.length() > 0) {
                        JSONObject first = seriess.getJSONObject(0);
                        JSONArray obs = first.getJSONArray("observations");
                        String seriesId = first.optString("id", selFile.getName().replace(".json", ""));
                        showTableFromObservations(obs, seriesId);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainFrame,
                            "Error loading/displaying: " + ex.getMessage(),
                            "File Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FREDChartDisplayApp::new);
    }
}