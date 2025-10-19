import java.io.*;
import java.util.*;

public class driver
{
    public static void main(String[] args)
    {
        // Check for argument
        if (args.length < 1)
        {
            System.out.println("Usage: java Driver <logFileName>");
                    return;
        }

        String logFileName = args[0];
        Scanner scanner = new Scanner(System.in);
        List<String> history = new ArrayList<>();
        String currentPassword = null;

        BufferedWriter loggerWriter = null;
        BufferedWriter encryptionWriter = null;
        BufferedReader encryptionReader = null;

        try
        {
            // Logger Process
            ProcessBuilder loggerBuilder = new ProcessBuilder("java", "Logger", logFileName);
            Process loggerProcess = loggerBuilder.start();
            loggerWriter = new BufferedWriter(new OutputStreamWriter(loggerProcess.getOutputStream()));

            // Encryption Process
            ProcessBuilder encryptionBuilder = new ProcessBuilder("java", "Encryption");
            Process encryptionProcess = encryptionBuilder.start();
            encryptionWriter = new BufferedWriter(new OutputStreamWriter(encryptionProcess.getOutputStream()));
            encryptionReader = new BufferedReader(new InputStreamReader(encryptionProcess.getInputStream()));

            // Log that Driver has started
            loggerWriter.write("START Driver Started\n");
            loggerWriter.flush();

            boolean running = true;
            while (running)
            {
                // Display menu
                System.out.println("\nMenu: password | encrypt | decrypt | history | quit");
                System.out.print("Enter command: ");
                String input = scanner.nextLine().trim().toLowerCase();

                switch (input)
                {
                    case "history" :
                        if (history.isEmpty())
                        {
                            System.out.println("History is empty.");
                        }
                        else
                        {
                            System.out.println("History: ");
                            for (int i = 0; i < history.size(); i++)
                            {
                                System.out.println((i + 1) + ": " + history.get(i));
                            }
                        }
                        break;

                    case "password":
                        boolean useHistory = false;
                        if (!history.isEmpty())
                        {
                            System.out.print("Use string from history? (y/n): ");
                            String choice = scanner.nextLine().trim().toLowerCase();
                            useHistory = choice.equals("y");

                            if (useHistory)
                            {
                                for (int i = 0; i < history.size(); i++)
                                {
                                    System.out.println((i + 1) + ": " + history.get(i));
                                }
                                System.out.println("Select number or 0 to enter new password: ");

                                try
                                {
                                    int select = Integer.parseInt(scanner.nextLine().trim());
                                    if (select > 0 && select <= history.size())
                                    {
                                        currentPassword = history.get(select - 1);
                                    }
                                    else
                                    {
                                        // If invalid
                                        currentPassword = null;
                                    }
                                }
                                catch(NumberFormatException exception)
                                {
                                    System.out.println("Invalid selection. Entering new password. ");
                                }
                            }

                            if (currentPassword == null || !useHistory)
                            {
                                System.out.print("Enter password: ");
                                currentPassword = scanner.nextLine().trim();
                            }

                            if (!currentPassword.matches("[A-Za-z]+"))
                            {
                                System.out.println("ERROR Password must contain letters only");
                            }
                            else
                            {
                                // Send passkey to encryption program
                                encryptionWriter.write("PASS " + currentPassword + "\n");
                                encryptionWriter.flush();
                                // Encryption process reads
                                String readResponse = encryptionReader.readLine();
                                System.out.println(readResponse);
                                // Log action
                                loggerWriter.write("PASSWORD set\n");
                                loggerWriter.flush();
                            }
                        }
                        break;

                    case "encrypt":
                        System.out.print("Enter text to encrypt: ");
                        String plainText = scanner.nextLine().trim();

                        // If input is invalid
                        if (!plainText.matches("[A-Za-z]+"))
                        {
                            System.out.println("ERROR Invalid input (letters only)");
                            break;
                        }

                        // Send encrypt command to encryption process
                        encryptionWriter.write("ENCRYPT " + plainText + "\n");
                        encryptionWriter.flush();

                        // Encryption reads response and prints
                        String encryptResponse = encryptionReader.readLine();
                        System.out.println(encryptResponse);

                        // Adds to history
                        if (encryptResponse.startsWith("RESULT"))
                        {
                            String result = encryptResponse.split(" ", 2)[1];
                            history.add(result);
                        }

                        // Logs action
                        loggerWriter.write("ENCRYPT " + plainText + "\n");
                        loggerWriter.flush();
                        break;

                    case "decrypt" :
                        System.out.print("Enter text to decrypt: ");
                        String cipherText = scanner.nextLine().trim();

                        // If input is invalid
                        if (!cipherText.matches("[A-Za-z]+"))
                        {
                            System.out.println("ERROR Invalid input (letters only)");
                            break;
                        }

                        // Send decrypt command to decrypt process
                        encryptionWriter.write("DECRYPT " + cipherText + "\n");
                        encryptionWriter.flush();

                        // Read response and print
                        String decryptResponse = encryptionReader.readLine();
                        System.out.println(decryptResponse);

                        // Add to history
                        if (decryptResponse.startsWith("RESULT"))
                        {
                            String result = decryptResponse.split(" ", 2)[1];
                            history.add(result);
                        }

                        // Log action
                        loggerWriter.write("DECRYPT " + cipherText + "\n");
                        loggerWriter.flush();
                        break;

                    case "quit" :
                        // Send to both processes
                        encryptionWriter.write("QUIT\n");
                        encryptionWriter.flush();
                        loggerWriter.write("QUIT Driver quitting\n");
                        loggerWriter.flush();

                        // Exits loop
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
        finally
        {
            try
            {
                // Close everything
                if (encryptionWriter != null) encryptionWriter.close();
                if (encryptionReader != null) encryptionReader.close();
                if (loggerWriter != null) loggerWriter.close();
            }
            catch (IOException exception)
            {
                System.out.println("ERROR closing resources: " + exception.getMessage());
            }
            scanner.close();
        }
    }
}