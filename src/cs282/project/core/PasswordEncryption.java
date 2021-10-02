/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs282.project.core;

/**
 *
 * @author Jur Yel
 */
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;
public class PasswordEncryption {
    public static String getHash(byte[] inputBytes, String algorithm){
        String hashValue = "";
        try{
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(inputBytes);
            byte[] digestedBytes = messageDigest.digest();
            hashValue = DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        return hashValue;
    }
    
    public static void main(String[] args){
        String password = "qwerty182";
        String password2 = "qwerty182";
        
        System.out.println("Password: " + password);
        System.out.println("Encrypted: " + getHash(password2.getBytes(),"MD5"));
        
        System.out.println();
        if(getHash(password.getBytes(),"MD5").equals(getHash(password.getBytes(),"MD5"))){
            System.out.println("match");
        }
        else{
            System.out.println("error");
        }
        
    }
}
