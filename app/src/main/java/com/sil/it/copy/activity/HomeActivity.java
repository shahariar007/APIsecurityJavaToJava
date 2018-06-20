package com.sil.it.copy.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;

import com.sil.it.copy.R;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class HomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private String privateKeyString, publicKeyString;
    private String encryptedSecretKey;
    private String cipherTextString;

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();

    }

    @Override
    protected void onStart() {
        super.onStart();
//        viewPager.setCurrentItem(0);
//        ViewPagerAdapters viewPagerAdapters = new ViewPagerAdapters(getSupportFragmentManager());
//        viewPager.setPageTransformer(true, new TransFormView());
//        viewPager.setAdapter(viewPagerAdapters);
        generateKey();
        Enp();
        Dec();
    }

    public void Enp() {
        try {

            // 1. generate secret key using AES
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128); // AES is currently available in three key sizes: 128, 192 and 256 bits.The design and strength of all key lengths of the AES algorithm are sufficient to protect classified information up to the SECRET level
            SecretKey secretKey = keyGenerator.generateKey();

            // 2. get string which needs to be encrypted
            String text = "University of Asia Pacific";

            // 3. encrypt string using secret key
            byte[] raw = secretKey.getEncoded();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
            cipherTextString = Base64.encodeToString(cipher.doFinal(text.getBytes(Charset.forName("UTF-8"))), Base64.DEFAULT);

            // 4. get public key
            X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(Base64.decode(publicKeyString, Base64.DEFAULT));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(publicSpec);

            // 6. encrypt secret key using public key
            Cipher cipher2 = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            cipher2.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedSecretKey = Base64.encodeToString(cipher2.doFinal(secretKey.getEncoded()), Base64.DEFAULT);

            // 7. pass cipherTextString (encypted sensitive data) and encryptedSecretKey to your server via your preferred way.
            // Tips:
            // You may use JSON to combine both the strings under 1 object.
            // You may use a volley call to send this data to your server.

        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    public void generateKey() {
        try {

            // 1. generate public key and private key
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024); // key length
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            privateKeyString = Base64.encodeToString(keyPair.getPrivate().getEncoded(), Base64.DEFAULT);
            publicKeyString = Base64.encodeToString(keyPair.getPublic().getEncoded(), Base64.DEFAULT);

            // 2. print both keys
            System.out.println("rsa key pair generated\n");
            System.out.println("privateKey\n" + privateKeyString + "\n");
            System.out.println("publicKey\n" + publicKeyString + "\n\n");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void Dec() {
        try {
            // 1. Get private key
            PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString, Base64.DEFAULT));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(privateSpec);

            // 2. Decrypt encrypted secret key using private key
            Cipher cipher1 = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            cipher1.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] secretKeyBytes = cipher1.doFinal(Base64.decode(encryptedSecretKey, Base64.DEFAULT));
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, 0, secretKeyBytes.length, "AES");

            // 3. Decrypt encrypted text using secret key
            byte[] raw = secretKey.getEncoded();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
            byte[] original = cipher.doFinal(Base64.decode(cipherTextString, Base64.DEFAULT));
            String text = new String(original, Charset.forName("UTF-8"));

            // 4. Print the original text sent by client
            System.out.println("text\n" + text + "\n\n");

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }
}
