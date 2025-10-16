import java.lang.*;
import java.io.*;
import java.util.Scanner;
import java.util.*;

public class Encryption
{
    // Store passkey
    String passkey = null;

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

    // Set passkey if it is valid
    public void setPassKey(String key)
    {
        if(lettersOnly(key))
        {
            passkey = setToNormal(key);
            System.out.println("RESULT Passkey set");
        }
        else
        {
            System.out.println("ERROR Passkey must contain only letters.");
        }
    }

    //Encrypt using passkey
    public String encrypt(String text)
    {
        if (passkey == null)
        {
            return "ERROR Password not set";
        }
        return "RESULT " + vigenereCipherEncrypt(text, passkey);
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

    // Decrypt using the passkey
    public String decrypt(String text)
    {
        if (passkey == null)
        {
            return "ERROR Password not set";
        }
        return "RESULT " + vigenereCipherDecrypt(text, passkey);
    }

    // Decrption using Vigenere Cipher
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
}