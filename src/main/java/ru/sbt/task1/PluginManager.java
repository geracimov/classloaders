package ru.sbt.task1;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.NotDirectoryException;
import java.util.HashMap;
import java.util.Map;

/**
 * Ваша задача написать загрузчик плагинов в вашу систему. Допустим вы пишите свой браузер и хотите,
 * чтобы люди имели имели возможность писать плагины для него.
 * Соответственно, разные разработчики могут назвать свои классы одинаковым именем,
 * ваш загрузчик должен корректно это обрабатывать.
 *
 * @see ru.sbt.task1.Plugin - это базовый интерфейс  для всех плагинов
 * <p>
 * PluginManager ищет скомпилированные классы плагина в папке pluginRootDirectory\pluginName\
 */
public class PluginManager {
    private final File pluginRootDirectory;
    private Map<String, URLClassLoader> loadedPlugins = new HashMap<>();

    public PluginManager( String pluginRootDirectory ) throws NotDirectoryException {
        File pluginRootDir = new File( pluginRootDirectory );
        if ( !( pluginRootDir.isDirectory() && pluginRootDir.exists() ) )
            throw new NotDirectoryException( String.format( "URL '%s' is not exists or it isn't directory!",
                    pluginRootDirectory ) );
        this.pluginRootDirectory = pluginRootDir;
    }

    public Plugin load( String pluginName, String pluginClassName )
            throws NotDirectoryException
            , MalformedURLException
            , ClassNotFoundException
            , IllegalAccessException
            , InstantiationException
            , NoSuchMethodException
            , InvocationTargetException {

        if ( pluginName == null
                || pluginClassName == null
                || pluginName.equals( "" )
                || pluginClassName.equals( "" ) )
            throw new IllegalArgumentException( String.format( "pluginName '%s' or pluginClassName '%s' is empty!"
                    , pluginName
                    , pluginClassName ) );

        if ( loadedPlugins.containsKey( pluginName ) )
            return (Plugin) loadedPlugins.get( pluginName )
                    .loadClass( pluginClassName )
                    .getDeclaredConstructor()
                    .newInstance();

        File pluginDir = pluginRootDirectory.toPath().resolve( pluginName ).toFile();
        if ( !( pluginDir.isDirectory() && pluginDir.exists() ) )
            throw new NotDirectoryException( String.format( "Plugin's directory '%s' is not exists or it isn't directory!"
                    , pluginRootDirectory ) );

        URLClassLoader classLoader = new URLClassLoader( new URL[]{ pluginDir.toURI().toURL() } );
        loadedPlugins.put( pluginName, classLoader );
        return (Plugin) classLoader.loadClass( pluginClassName ).getDeclaredConstructor().newInstance();
    }
}
