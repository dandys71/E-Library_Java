package cz.uhk.fim.student.schmid.java.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookItemUtil {
    public static String authorsToString(List<String> authors, Boolean getShortedNames){
        StringBuilder res = new StringBuilder();
        for (String a: authors) {
            StringBuilder name = new StringBuilder();
            if(getShortedNames){
                String[] part = a.split(" ");

                for(int i = 0; i < part.length - 2; i++){
                    name.append(part[i].substring(0, 1)).append(". ");
                }
                name.append(part[part.length-1]);
            }else{
                name.append(a);
            }

            res.append((!a.equals(authors.get(authors.size() - 1))) ? a + ", " : a);
        }
        return res.toString();
    }

    public static Date getDateFromNow(int days){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    public static Date addDaysToDate(Date date, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
}
