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
 * @version 0.0
 */
public class CsvManager {
  
  private String CSV_PATH; 

  public CsvManager() {
        CSV_PATH = "ga/ssGA/src/result.csv";
  }

  public void writeCSV(List<String[]> data) throws IOException {
      System.out.println(CSV_PATH);
      try (
          Writer writer = Files.newBufferedWriter(Paths.get(CSV_PATH));

          CSVWriter csvWriter = new CSVWriter(writer,
                  CSVWriter.DEFAULT_SEPARATOR,
                  CSVWriter.NO_QUOTE_CHARACTER,
                  CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                  CSVWriter.DEFAULT_LINE_END);
      ) {
        //   String[] headerRecord = {"Name", "Email", "Phone", "Country"};
        //   csvWriter.writeNext(headerRecord);

        //   csvWriter.writeNext(new String[]{"Sundar Pichai", "sundar.pichai@gmail.com", "+1-1111111111", "India"});
        //   csvWriter.writeNext(new String[]{"Satya Nadella", "satya.nadella@outlook.com", "+1-1111111112", "India"});
        csvWriter.writeAll(data);
    }
  }
}


