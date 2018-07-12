package ru.sbt.task2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;

/**
 * Данный класслоадер умеет загружать классы из файлов дешифрую их. Ваша задача переопределить метод findClass().
 * В нем лоадер считывает зашифрованный массив байт, дешифрует его и превращает в класс (с помощью метода defineClass).
 * <p>
 * На вход класслодер принимает ключ шифрования, рутовую папку, в которой будет искать классы, родительский класслодер.
 * Логика шифрования/дешифрования с использованием ключа может быть любой на ваш вкус
 * (например, каждый считаный байт класса увеличить на определение число).
 */
public class EncryptedClassLoader extends ClassLoader {
    private final CryptoUtils crypt;
    private final File dir;

    public EncryptedClassLoader( CryptoUtils crypt, File dir, ClassLoader parent ) throws NotDirectoryException {
        super( parent );
        if ( !( dir.isDirectory() && dir.exists() ) )
            throw new NotDirectoryException( String.format( "Directory '%s' is not exists or it isn't directory!", dir ) );
        this.crypt = crypt;
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass( String name ) throws ClassNotFoundException {
        try {
            byte[] bytes = getBytesFromFile( name );
            return defineClass( name, bytes, 0, bytes.length );
        } catch ( IOException e ) {
            e.printStackTrace( System.out );
        }
        return null;
    }

    private byte[] getBytesFromFile( String name ) throws IOException {
        File filename = dir.toPath().resolve( name.replace( ".","/" ) + ".class" ).toFile();
        if ( !filename.exists() ) throw new FileNotFoundException( "" );

        FileInputStream fis = new FileInputStream( filename );

        byte[] bytes = new byte[fis.available()];
        fis.read( bytes, 0, fis.available() );

        return crypt.encodeDecode( bytes );
    }
}

