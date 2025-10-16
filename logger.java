import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.*;
import java.util.Scanner;

public class Logger
{
    private String logFilePath;

    // Set logFilePath to filePath
    public Logger(String filepath)
    {
        logFilePath = filepath;
    }

    // Logs actions and message
    public void log(String action, String message)
    {
       try(FileWriter fw = new FileWriter(logFilePath, true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter pw = new PrintWriter(bw))
        {
            // Timestamp format
            String timeStamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            pw.println(timeStamp + " " + action + " " + message); // Print to logfile
        }

       // If cannot be written to logfile
        catch (IOException exception)
        {
            System.out.println("ERROR Could not write to log file ");
        }
    }
}