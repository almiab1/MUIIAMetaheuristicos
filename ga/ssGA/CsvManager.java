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
 * @version 0.1
 */

public class CsvManager {
  
  private String CSV_PATH;
  private String CSV_FILE_NAME;

  public CsvManager(String fileName)  {
    CSV_PATH = "ga/ssGA/src/";
    CSV_FILE_NAME = fileName + ".csv";
  }

  public void writeCSV(List<String[]> data) throws IOException {
    String filePath = CSV_PATH + CSV_FILE_NAME;

    try (
        Writer writer = Files.newBufferedWriter(Paths.get(filePath));

        CSVWriter csvWriter = new CSVWriter(writer,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
    ) {
      csvWriter.writeAll(data);
    }
  }
}


