import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate {
    @Test
    public void test01(){
//        int month = new Date().getMonth();
//        int year = new Date().getYear();
//      String x=  year+"-"+month;
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        String format = df.format(new Date());
        System.out.println(format);
    }
}
