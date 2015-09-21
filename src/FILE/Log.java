package FILE;

import java.io.*;
import java.text.DateFormat;
import java.util.GregorianCalendar;

/**
 * Created by Marco on 19.09.2015.
 */
public class Log {

    private static boolean isDir = false;
    private static File dir = null;
    private static final String NEW_LINE_SEPERATOR = System.lineSeparator();
    private static String log = "";

    private static File logFile = null;

    public static void initalize(){
        dir = new File(System.getProperty("user.home")  + System.getProperty("file.separator") + "Documents" + System.getProperty("file.separator") + "vcfConverter");
        if(dir.exists()){
            isDir = true;
            logFile = new File(dir + System.getProperty("file.separator") + "log.txt");
        } else{
            dir.mkdir();
            logFile = new File(dir + System.getProperty("file.separator") + "log.txt");
            writeFirstLog();
            isDir = true;
        }
    }

    public static void writeLog(String input) {
        GregorianCalendar now = new GregorianCalendar();
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);
        log = log + df.format(now.getTime()) + NEW_LINE_SEPERATOR + input + NEW_LINE_SEPERATOR;
    }

    public static void closeLog(){
        writeLog("Program closed!" + NEW_LINE_SEPERATOR + "---------------------------------");
        PrintWriter twe = null;
        try {
            twe = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));
            twe.print(log);
            twe.flush();
            twe.close();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFirstLog(){
        PrintWriter pw = null;
        GregorianCalendar now = new GregorianCalendar();
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);

        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(logFile)));
            pw.print("LOG FILE | vcf Converter 0.1 | by Marco Combosch (2015)");
            pw.print(NEW_LINE_SEPERATOR);
            pw.print("-------------------------------------------------------");
            pw.print(NEW_LINE_SEPERATOR);
            pw.print("File created: " + df.format(now.getTime()));
            pw.print(NEW_LINE_SEPERATOR);
            pw.print("-------------------------------------------------------");
            pw.print(NEW_LINE_SEPERATOR);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pw.close();
        }
    }
}
