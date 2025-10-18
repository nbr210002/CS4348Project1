import java.io.*;
import java.util.*;

public class Driver
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Driver <logFileName>");
                    return;
        }

        String logFileName = args[0];
        Scanner scanner = new Scanner(System.in);
        List<String> history = new ArrayList<>();
        String currentPassword = null;

        try
        {
            // Logger Process
            ProcessBuilder loggerBuilder = new ProcessBuilder("java", "Logger", logFileName);
            Process loggerProcess = loggerBuilder.start();
            BufferedWriter loggerWriter = new BufferedWriter(new OutputStreamWriter(loggerProcess.getOutputStream()));

            // Encryption Process
            ProcessBuilder encryptionBuilder = new ProcessBuilder("java", "Encryption");
            Process encryptionProcess = encryptionBuilder.start();
            BufferedWriter encryptionWriter = new BufferedWriter(new OutputStreamWriter(encryptionProcess.getOutputStream()));
            BufferedReader encryptionReader = new BufferedReader(new InputStreamReader(encryptionProcess.getInputStream()));

            // Start Logger Program
            loggerWriter.write("START Driver Started\n");
            loggerWriter.flush();

            boolean running = true;
            while (running)
            {
                System.out.println("\nMenu: password | encrypt | decrypt | history | quit");
                System.out.print("Enter command: ");
                String input = scanner.nextLine().trim().toLowerCase();

                switch (input)
                {
                    case "history":
                        System.out.println("History: ");
                        for (int i = 0; i < history.size(); i++)
                        {
                            System.out.println((i + 1) + ": " + history.get(i));
                        }
                        break;

                    case "password":
                        System.out.print("Enter password: ");
                        currentPassword = scanner.nextLine().trim();

                        // Send passkey to encryption program
                        encryptionWriter.write("PASS " + currentPassword + "\n");
                        encryptionWriter.flush();

                        // Encryption process reads
                        String readResponse = encryptionReader.readLine();
                        System.out.println(readResponse);

                        // Log action
                        loggerWriter.write("PASSWORD " + (currentPassword.isEmpty() ? "No password entered" : "Password set") + "\n");
                        loggerWriter.flush();
                        break;

                    case "encrypt":
                        System.out.print("Enter text to encrypt: ");
                        String plainText = scanner.nextLine().trim();

                        // Send encrypt command to the encryption process
                        encryptionWriter.write("ENCRYPT " + plainText + "\n");
                        encryptionWriter.flush();

                        // Encryption reads response
                        String encryptResponse = encryptionReader.readLine();
                        System.out.println(encryptResponse);

                        // Adds to history
                        if (encryptResponse.startsWith("RESULT"))
                        {
                            history.add(encryptResponse.substring(7));
                        }

                        // Log action
                        loggerWriter.write("ENCRYPT " + plainText + "\n");
                        loggerWriter.flush();
                        break;

                    case "decrypt" :
                        System.out.print("Enter text to decrypt: ");
                        String cipherText = scanner.nextLine().trim();

                        // Send decrypt command to the decrypt process
                        encryptionWriter.write("DECRYPT " + cipherText + "\n");
                        encryptionWriter.flush();

                        // Read response
                        String decryptResponse = encryptionReader.readLine();
                        System.out.println(decryptResponse);

                        // Add to history
                        if (decryptResponse.startsWith("RESULT"))
                        {
                            history.add(decryptResponse.substring(7));
                        }

                        loggerWriter.write("DECRYPT " + cipherText + "\n");
                        loggerWriter.flush();
                        break;

                    case "quit" :
                        // Send to both processes
                        encryptionWriter.write("QUIT\n");
                        encryptionWriter.flush();
                        loggerWriter.write("QUIT Driver quitting\n");
                        loggerWriter.flush();

                        // Close logger and encryption
                        encryptionWriter.close();
                        encryptionReader.close();
                        loggerWriter.close();

                        running = false;
                        break;

                    default :
                        System.out.println("Invalid command");
                        break;
                }
            }
        }

        // Exception handler
        catch (IOException exception)
        {
            System.out.println("ERROR: " + exception.getMessage());
            exception.printStackTrace();
        }
    }
}

/*
loggerProcess = start with logFileName
start encryption

use engryptionWriter and encryptionReader

give menu options
if userInput == password
    then give option to choose password
    or create new password

if userInput == encrypt
    then give option to choose
    send to encryption writer
    enryptionReader reads
    print
    if success then add output to history
    log

if userInput == decrypt
    then give option to choose
    send to encryption writer
    enryptionReader reads
    print
    if success then add output to history
    log

 if userInput == history
    display history

 if userInput = QUIT
    QUIT encryptionWriter
    QUIT loggerWriter

 if userInput = none of the options
    print "invalid"
 */