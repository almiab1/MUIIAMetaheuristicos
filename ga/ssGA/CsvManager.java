/*
* Csv.java
*
* Created on 11 de december de 2022
*/

package ga.ssGA;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author  Alejandro
 * @version 1.0
 */

public class CsvManager {

  private String SRC_PATH;

  public CsvManager()  {
    SRC_PATH = "ga/../src/";
  }

  public void overwriteData(List<String[]> data, String folder_path, String file_name) throws IOException {
    // Define file path
    String filePath = SRC_PATH + folder_path + file_name + ".csv";

    try (
      // Declare file writer
      Writer writer = Files.newBufferedWriter(Paths.get(filePath));
      // Declare csv writer
      CSVWriter csvWriter = new CSVWriter(writer,
              CSVWriter.DEFAULT_SEPARATOR,
              CSVWriter.NO_QUOTE_CHARACTER,
              CSVWriter.DEFAULT_ESCAPE_CHARACTER,
              CSVWriter.DEFAULT_LINE_END);
    ) {
      // Write data into csv
      csvWriter.writeAll(data);
      // csvWriter.flush();
      // csvWriter.close();
    }
  } // end overwriteData

  public List<String[]> readAllDataAtOnce(String folder_path, String file_name) throws IOException
  {
    // Define file path
    String file_path = SRC_PATH + folder_path + file_name + ".csv";
    List<String[]> allData = new ArrayList<String[]>();

    String line = "";
    String cvsSplitBy = ","; // Separador de valores en el archivo CSV

    try (BufferedReader br = new BufferedReader(new FileReader(file_path))) {
      while ((line = br.readLine()) != null) {
        // Usa el separador para dividir la línea en valores individuales
        String[] values = line.split(cvsSplitBy);

        allData.add(values);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return allData;
  }
} // END OF CLASS: CsvManager


