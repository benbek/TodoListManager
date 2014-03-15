package il.ac.huji.todolist;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/***
 * Represents an item in the Todo list.
 */
public class TodoItem {
    public String title;
    public Date dueDate;
    
    public boolean isOverdue() {
        return this.dueDate.before(getTodayDate());
    }
    
    public static Date getTodayDate() {
        // Get today    
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and milliseconds
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        
        return date.getTime();
    }
}