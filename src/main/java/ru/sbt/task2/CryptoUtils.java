package ru.sbt.task2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class CryptoUtils {
    private final String key;

    public CryptoUtils( String key ) {
        if ( key == null || key.length() == 0 )
            throw new IllegalArgumentException( "Key must not be empty!" );
        this.key = key;
    }

    public byte[] encodeDecode( byte[] bytesIn ) {
        byte[] bytesOut = new byte[bytesIn.length];
        int keyi = 0;
        int keylen = key.length();
        for ( int i = 0; i < bytesIn.length; i++ ) {
            bytesOut[i] = (byte) ( bytesIn[i] ^ key.charAt( keyi ) );
            keyi++;
            if ( keyi == keylen ) {
                keyi = 0;
            }
        }
        return bytesOut;
    }


    public void encodeDecodeFile( File filenameSrc, File filenameDst ) throws IOException {
        filenameDst.getParentFile().mkdirs();
        try (
                FileInputStream fis = new FileInputStream( filenameSrc );
                FileOutputStream fos = new FileOutputStream( filenameDst )
        ) {
            byte[] bytes = new byte[fis.available()];
            fis.read( bytes, 0, fis.available() );
            fos.write( encodeDecode( bytes ) );
        } catch ( IOException e ) {
            e.printStackTrace( System.out );
        }
    }

    public static byte[] encode64( byte[] bytes ) {
        return Base64.getEncoder().encode( bytes );
    }

    public static byte[] decode64( byte[] bytes ) {
        return Base64.getDecoder().decode( bytes );
    }
}
