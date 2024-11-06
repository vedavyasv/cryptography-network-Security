 import java.io.*;
import java.util.*;

public class SubstitutionCipher {

    static Scanner sc = new Scanner(System.in);
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        // TODO: code application logic here
        String a = "abcdefghijklmnopqrstuvwxyz";
        String b = "zyxwvutsrqponmlkjihgfedcba";
        System.out.print("Enter any string: ");
        String str = br.readLine();
        String decrypt = "";
        char c;

       
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            int j = a.indexOf(c);
            if (j != -1) {
                decrypt += b.charAt(j);
            } else {
                decrypt += c; // Preserve characters not in the alphabet
            }
        }
       
        System.out.println("The encrypted data is: " + decrypt);
    }
}