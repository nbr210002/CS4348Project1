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
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            pw.println(timeStamp + " " + action + " " + message); // Print to logfile
        }

       // If cannot be written to logfile
        catch (IOException exception)
        {
            System.out.println("ERROR Could not write to log file ");
        }
    }

    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Logger <logFileName>");
            return;
        }

        String logFileName = args[0];
        Logger logger = new Logger(logFileName);
        Scanner scan = new Scanner(System.in);

        while (true)
        {
            String line = scan.nextLine().trim();
            if(line.equalsIgnoreCase("QUIT"))
            {
                break;
            }
            if (!line.isEmpty())
            {
                String[] parts = line.split("\\s+", 2);
                String action = parts[0];
                String message = (parts.length > 1) ? parts[1] : "";
                logger.log(action, message);
            }
        }
        scan.close();
    }
}