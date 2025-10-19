import java.lang.*;
import java.io.*;
import java.util.Scanner;
import java.util.*;

public class Encryption
{
    // Store passkey
    String pass = null;

    // Check if the String has letters only
    private static boolean lettersOnly(String x)
    {
        return x != null && x.matches("[A-Za-z]+");
    }

    // Normalize String
    public static String setToNormal(String x)
    {
        return x.toUpperCase();
    }

    // Encryption using Vigenere Cipher
    private static String vigenereCipherEncrypt(String text, String key)
    {
        StringBuilder result = new StringBuilder();
        text = setToNormal(text);
        key = setToNormal(key);

        int keyIndex = 0;
        for (char ch : text.toCharArray())
        {
            if (Character.isLetter(ch))
            {
                int shift = key.charAt(keyIndex % key.length()) - 'A';
                char encrypted = (char) ((ch - 'A' + shift)% 26 + 'A');
                result.append(encrypted);
                keyIndex++;
            }
            else
            {
                result.append(ch);
            }
        }
        return result.toString();
    }

    // Decryption using Vigenere Cipher
    private static String vigenereCipherDecrypt(String text, String key)
    {
        StringBuilder result = new StringBuilder();
        text = setToNormal(text);
        key = setToNormal(key);
        int keyIndex = 0;
        for (char ch : text.toCharArray())
        {
            if (Character.isLetter(ch))
            {
                int shift = key.charAt(keyIndex % key.length()) - 'A';
                char decrypted = (char) ((ch - 'A' - shift + 26) % 26 + 'A');
                result.append(decrypted);
                keyIndex++;
            }
            else
            {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static void main(String[] args)
    {
        Encryption encrypt = new Encryption();
        Scanner scan = new Scanner(System.in);

        while (scan.hasNextLine())
        {
            String line = scan.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+",2);
            String command = parts[0].toUpperCase();
            String argument = (parts.length > 1) ? parts[1] : "";

            switch (command)
            {
                case "PASS":
                    if (!lettersOnly(argument))
                    {
                        System.out.println("ERROR Passkey must only contain letters");
                    }
                    else
                    {
                        encrypt.pass = setToNormal(argument);
                        System.out.println("RESULT");
                    }
                    break;

                case "ENCRYPT" :
                    if (encrypt.pass == null)
                    {
                        System.out.println("ERROR Passkey not set");
                    }
                    else
                    {
                        if (!argument.matches("[A-Za-z]+"))
                        {
                            System.out.println("ERROR Invalid input (letters only)");
                        }
                        else
                        {
                            String out = vigenereCipherEncrypt(argument, encrypt.pass);
                            System.out.println("RESULT " + out);
                        }
                    }
                    break;

                case "DECRYPT" :
                    if (encrypt.pass == null)
                    {
                        System.out.println("ERROR Passkey not set");
                    }
                    else if (!argument.matches("[A-Za-z]+"))
                    {
                        System.out.println("ERROR Invalid input (letters only)");
                    }
                    else
                    {
                        String out = vigenereCipherDecrypt(argument, encrypt.pass);
                        System.out.println("RESULT " + out);
                    }
                    break;

                case "QUIT" :
                    scan.close();
                    return;

                default :
                    System.out.println("ERROR Unknown command");
            }
        }

        scan.close();
    }
}

