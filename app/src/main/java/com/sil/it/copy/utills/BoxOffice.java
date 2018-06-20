package com.sil.it.copy.utills;

import android.util.Log;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class BoxOffice {
    private KeyGenerator keyGenerator=null;

    public BoxOffice() {
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128); // AES is currently available in three key sizes: 128, 192 and 256 bits.The design and strength of all key lengths of the AES algorithm are sufficient to protect classified information up to the SECRET level
            SecretKey secretKey = keyGenerator.generateKey();
            Log.d("SRC",secretKey.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
