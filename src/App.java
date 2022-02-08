import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class App {
    public static void main(String[] args) {
        try {
            /*
                Zastosujemy algorytm RSA z wykorzystaniem klucza publicznego oraz prywatnego
                do szyfrowania napisu.
                Klucz publiczny użyjemy do szyfrowania danych.
                Klucz prywatny użyjemy do odszyfrowania danych.
            */

            final var ALGORITHM = "RSA";

            // Generowanie pary klucz prywatny oraz klucz publiczny
            var generator = KeyPairGenerator.getInstance(ALGORITHM);
            generator.initialize(2048);
            var pair = generator.generateKeyPair();
            var publicKey = pair.getPublic();
            var privateKey = pair.getPrivate();

            // Napis do zaszyfrowania
            var message = "km-programs.pl";

            // Szyfrowanie napisu
            var encryptCipher = Cipher.getInstance(ALGORITHM);
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            var messageBytes = message.getBytes(StandardCharsets.UTF_8);
            var encryptedMessageBytes = encryptCipher.doFinal(messageBytes);
            var encryptedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
            System.out.println("ENCRYPTED: " + encryptedMessage);

            // Odszyfrowanie napisu
            var decryptCipher = Cipher.getInstance(ALGORITHM);
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
            var decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);
            var decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
            System.out.println("DECRYPTED: " + decryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
