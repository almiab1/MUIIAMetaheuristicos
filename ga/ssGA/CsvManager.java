/*
* Csv.java
*
* Created on 11 de december de 2022
*/

package ga.ssGA;

import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.*;
import java.util.List;

/**
 *
 * @author  Alejandro
 * @version 1.0
 */

public class CsvManager {
  
  private String CSV_PATH;
  private String CSV_FILE_NAME;

  public CsvManager(String fileName, String path)  {
    CSV_PATH = "ga/ssGA/src/"+path;
    CSV_FILE_NAME = fileName + ".csv";
  }

  public void overwriteData(List<String[]> data) throws IOException {
    // Define file path
    String filePath = CSV_PATH + CSV_FILE_NAME;

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
      // csvWriter.close(); //close the writer
    }
  } // end overwriteData
} // END OF CLASS: CsvManager


