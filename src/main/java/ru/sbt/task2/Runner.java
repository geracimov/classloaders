package ru.sbt.task2;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Runner {
    public static void main( String[] args ) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String className = "ru.sbt.examples.reflection.TestClass";
        String classFilename = className.replace( ".", "/" ) + ".class";
        File dirClass = new File( "../annotation-reflection-example/target/classes" );

        File dirEnc = new File( "encrypted" );
        CryptoUtils crypt = new CryptoUtils( "Ключ!Ключ!Ключ!" );

        crypt.encodeDecodeFile(
                new File( dirClass.toPath().resolve( classFilename ).toString() ),
                new File( dirEnc.toPath().resolve( classFilename ).toString() )
        );

        EncryptedClassLoader loader = new EncryptedClassLoader( crypt, dirEnc, ClassLoader.getSystemClassLoader() );
        Class clazz = loader.loadClass( className );
        System.out.println( clazz.getDeclaredConstructor().newInstance() );
    }
}
