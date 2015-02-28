package zac.cryptography;

public class AESDemo {
    
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
 
        AES d = new AES();
        
        String encryptedText = d.encrypt("Hello");
        
        System.out.println("Encrypted string:" + encryptedText);           
        
        String decyptKey = "test";
        
        System.out.println("Decrypted string:" + d.decrypt(encryptedText, decyptKey));         
 
    }
}