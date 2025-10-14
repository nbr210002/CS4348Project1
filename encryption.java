import java.lang.*;
import java.io.*;
import java.util.Scanner;

public class Encryption
{
    int key = 3; // places to shift; beginning, middle, end

    //encryption method
    public String encrypt(String sentence)
    {
        StringBuilder cipher = new StringBuilder();
        for (char x : sentence.toCharArray())
        {
            if(Character.isLetter(x))
            {
                char base = Character.isUpperCase(x) ? 'A' : 'a'; // see if uppercase vs lowercase
                char shift = (char) ((x - base + key) % 26 + base); // 26 letters in alphabet
                cipher.append(shift);
            }
            else
            {
                cipher.append(x);
            }
        }
        return cipher.toString();
    }
}

// Pseudocode for what to do... maybe
/*
read from stdin
split line into command and argument

if command == quit then break
else comand == PASSKEY then print result
else command == ENCRYPT then print ERROR

else if command == DECRYPT
    if key == null then print ERROR
    else decrypted
        print result, decrypted
 */