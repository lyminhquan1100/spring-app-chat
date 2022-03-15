package spring.boot.core.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spring.boot.core.exception.BaseException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelUtil {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final static String XLS_EXTENSION = "xls";
  private final static String XLSX_EXTENSION = "xlsx";
  private final static String XLSM_EXTENSION = "xlsm";

  public static Path appendLog(Path path, int sheetNo, int startLineNo, List<String> data) {
    String extension = "";
    if (path != null) {
      extension = FilenameUtils.getExtension(path.getFileName().toString()).toLowerCase();
    }
    if (!XLS_EXTENSION.equals(extension)
        && !XLSX_EXTENSION.equals(extension)
        && !XLSM_EXTENSION.equals(extension)) {
      throw new BaseException(400, "File extension invalid");
    }

    FileInputStream inputStream = null;
    Workbook workbook = null;
    FileOutputStream outputStream = null;
    Sheet sheet;
    try {
      inputStream = new FileInputStream(path.toFile());
      workbook = extension.equals(XLSX_EXTENSION) || extension.equals(XLSM_EXTENSION)
          ? new XSSFWorkbook(inputStream) : new HSSFWorkbook(inputStream);

      sheet = workbook.getSheetAt(sheetNo - 1);

      Iterator<Row> rowIterator = sheet.iterator();

      int columnCount = sheet.getRow(startLineNo).getPhysicalNumberOfCells();
      int rowCount = 0;
      int dataSize = data.size();
      while (rowIterator.hasNext()) {
        Row row = rowIterator.next();

        if (row.getRowNum() < startLineNo + 1) {
          continue;
        }

        Cell cell = row.createCell(columnCount + 2);
        cell.setCellValue(data.get(rowCount));
        rowCount++;

        if (rowCount >= dataSize) {
          break;
        }
      }
      Path logFile = path.toAbsolutePath().getParent();
      if (path != null) {
        String fileName = path.getFileName().toString();
        logFile.resolve(FilenameUtils.removeExtension(fileName) + "_log" + "." + FilenameUtils
            .getExtension(fileName));
      }
      outputStream = new FileOutputStream(logFile.toFile());
      workbook.write(outputStream);

      return logFile;
    } catch (IOException e) {
      throw new BaseException(500, "Error");
    } finally {
      try {
        if (outputStream != null) {
          outputStream.close();
        }

        if (workbook != null) {
          workbook.close();
        }

        if (inputStream != null) {
          inputStream.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static List<List<String>> readFile(Path path, int sheetNo, int startLineNo) {
    String extension = "";
    if (path != null) {
      extension = FilenameUtils.getExtension(path.getFileName().toString()).toLowerCase();
    }
    if (!XLS_EXTENSION.equals(extension)
        && !XLSX_EXTENSION.equals(extension)
        && !XLSM_EXTENSION.equals(extension)) {
      throw new BaseException(400, "File extension invalid");
    }

    List<List<String>> result = new ArrayList<>();

    FileInputStream inputStream = null;
    Workbook workbook = null;
    try {
      String pathUri = path.toFile().toString().substring(1);
      inputStream = new FileInputStream(pathUri);
      workbook = extension.equals(XLSX_EXTENSION) || extension.equals(XLSM_EXTENSION)
          ? new XSSFWorkbook(inputStream) : new HSSFWorkbook(inputStream);

      Sheet sheet = workbook.getSheetAt(sheetNo - 1);

      int rowNo = sheet.getPhysicalNumberOfRows();
      int columnNo = sheet.getRow(startLineNo).getPhysicalNumberOfCells();

      boolean dataExists = false;
      for (int i = rowNo; i >= 0; i--) {
        if (i < startLineNo - 1) {
          continue;
        }

        Row row = sheet.getRow(i);

        if (row == null) {
          if (dataExists) {
            result.add(new ArrayList<>());
          }
          continue;
        }

        List<String> rowObject = new ArrayList<>();

        for (int j = 0; j < columnNo; j++) {
          Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
          CellType cellType = cell.getCellType();

          String value = null;
          if (CellType.STRING.equals(cellType)) {
            value = cell.getStringCellValue().trim();
          } else if (CellType.NUMERIC.equals(cellType)) {
            value = new DecimalFormat("0.####################").format(cell.getNumericCellValue());
          } else if (CellType.BOOLEAN.equals(cellType)) {
            value = String.valueOf(cell.getBooleanCellValue());
          }

          if (!StringUtils.isBlank(value)) {
            rowObject.add(value);
            dataExists = true;
          } else {
            rowObject.add(null);
          }
        }

        result.add(rowObject);
      }

      if (result.size() <= 0) {
        return new ArrayList<>();
      }

      // Add null property to object if not exists in excel
      int column = Collections.max(result.stream().map(List::size).collect(Collectors.toList()));
      for (List<String> rowObject : result) {
        for (int i = rowObject.size(); i < column; i++) {
          rowObject.add(null);
        }
      }

      Collections.reverse(result);

      return result;
    } catch (IOException e) {
      throw new BaseException("Error can't read file");
    } finally {
      try {
        if (workbook != null) {
          workbook.close();
        }

        Files.deleteIfExists(path);

        if (inputStream != null) {
          inputStream.close();
        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

