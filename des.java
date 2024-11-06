import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

class DESExample{
 Cipher ecipher;
 Cipher dcipher;

 DESExample(SecretKey key) throws Exception{
 ecipher=Cipher.getInstance("DES");
 dcipher=Cipher.getInstance("DES");
 ecipher.init(Cipher.ENCRYPT_MODE,key);
 dcipher.init(Cipher.DECRYPT_MODE,key);
 }
 
 public String encrypt(String str) throws Exception{
 //Encode the stirng into bytes using utf8
 byte[] utf8=str.getBytes("UTF-8");

 byte[] enc=ecipher.doFinal(utf8);
 return Base64.getEncoder().encodeToString(enc);
 }

public String decrypt(String str) throws Exception{
    byte[] dec=Base64.getDecoder().decode(str);
    byte[] utf8=dcipher.doFinal(dec);
    return new String(utf8,"UTF8");
}

public static void main(String[] argv) throws Exception {
    Scanner myObj= new Scanner(System.in);
    System.out.print("enter the plain text: ");
    final String secretText=myObj.nextLine();
    SecretKey key=KeyGenerator.getInstance("DES").generateKey();
    DESExample encrypter= new DESExample(key);
    String encrypted=encrypter.encrypt(secretText);
    System.out.println("Encrypted Value: " + encrypted);
    String decrypted=encrypter.decrypt(encrypted);
    System.out.println("Decrypted: "+decrypted);
}
}




