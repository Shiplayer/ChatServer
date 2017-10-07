import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogList {
    private final static String fileName = "log.txt";
    private static PrintWriter pw;
    private final static Date date = new Date();
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public LogList() throws FileNotFoundException {
        pw = new PrintWriter(new FileOutputStream(new File(fileName), true));
    }

    public void log(Object object, String tag){
        pw.println(dateFormat.format(date) + ": " + object.toString());
        pw.flush();
    }

}
