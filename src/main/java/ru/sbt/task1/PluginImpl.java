package ru.sbt.task1;

public class PluginImpl implements Plugin {
    private static int counter = 0;

    public PluginImpl( ) {
        System.out.println( "Plugin 'PluginImpl' -> loaded! (" + ( ++counter ) + ")" );
    }

    @Override
    public void doUsefull( ) {

    }
}
