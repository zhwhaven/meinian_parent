import com.microsoft.schemas.office.visio.x2012.main.CellType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.*;

public class TestPoi {
    @Test
    public void test01(){
        String filename="D:\\test.xlsx";
        try (InputStream inp = new FileInputStream(filename)) {
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 0; i <=lastRowNum ; i++) {
                Row row = sheet.getRow(i);
                short lastCellNum = row.getLastCellNum();
                for (int j = 0; j <lastCellNum ; j++) {
                    System.out.print(row.getCell(j)+"  ");
                }
                System.out.println();
            }

//             Write the output to a file

            try (OutputStream fileOut = new FileOutputStream("D:\\workbook.xlsx")) {
                wb.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test02() {
    }
}
