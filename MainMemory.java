import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.*;
import java.util.Scanner;

public class MainMemory
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int memLoc = 0;

        String line;
        while ((line = br.readLine()) != null)
        {
            line = line.trim();
            if (line.equalsIgnoreCase("halt")) break;

            if (line.equalsIgnoreCase("read"))
            {
                System.out.println(memLoc);
                System.out.flush();
            }
            else if (line.equalsIgnoreCase("write")) {
                String valueLine = br.readLine();
                if (valueLine != null)
                {
                    memLoc = Integer.parseInt(valueLine.trim());
                }

            }
        }
    }
}