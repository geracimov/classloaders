package ru.sbt.task1;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.NotDirectoryException;

public class Runner {
    public static void main( String[] args ) throws NotDirectoryException
            , IllegalAccessException
            , ClassNotFoundException
            , MalformedURLException
            , InvocationTargetException
            , InstantiationException
            , NoSuchMethodException {
        PluginManager pm = new PluginManager( "E:\\GDrive\\Education\\JavaTmn\\classloaders\\target" );
        pm.load( "classes", "ru.sbt.task1.PluginImpl" );
        pm.load( "classes", "ru.sbt.task1.PluginImpl" );
        pm.load( "classes", "ru.sbt.task1.PluginImpl" );
        pm.load( "classes", "ru.sbt.task1.PluginImpl" );
        pm.load( "classes", "ru.sbt.task1.PluginImpl" );
        pm.load( "classes", "ru.sbt.task1.PluginImpl" );
        pm.load( "classes", "ru.sbt.task1.PluginImpl" );
    }
}
