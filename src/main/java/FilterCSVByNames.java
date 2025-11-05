import java.io.*;

public class FilterCSVByNames {
    public static void filterAndWriteModified(String inputFile) {
        String outputFile = generateModifiedFileName(inputFile);
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",", -1);
                if (columns.length > 0) {
                    String firstColumn = columns[0].trim();
                    if (firstColumn.equals("Michael Jackson") || firstColumn.equals("Elton John")
                    || firstColumn.equals("Cher")) {
                        bw.write(line);
                        bw.newLine();
                    }
                }
            }
            System.out.println("Filtered data written to: " + outputFile);
        } catch (IOException e) {
            System.err.println("Error processing the file: " + e.getMessage());
        }
    }

    private static String generateModifiedFileName(String inputFile) {
        File file = new File(inputFile);
        String name = file.getName();
        int dotIndex = name.lastIndexOf('.');
        String baseName = (dotIndex == -1) ? name : name.substring(0, dotIndex);
        String extension = (dotIndex == -1) ? "" : name.substring(dotIndex);
        return new File(file.getParent(), baseName + "Modified" + extension).getPath();
    }

    public static void main(String[] args) {
        filterAndWriteModified("DataFiles/ThirdPartyDataCSVFiles/TopSongs5000Edited.csv");
    }
}
