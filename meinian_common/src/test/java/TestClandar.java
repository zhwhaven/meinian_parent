import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestClandar {
    @Test
    public void test(){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
        for (int i = 0; i <12 ; i++) {
            calendar.add(Calendar.MONTH,1);
            String month = sdf.format(calendar.getTime());
            System.out.println(month);
        }
    }
}
