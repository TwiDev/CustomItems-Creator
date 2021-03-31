package net.twidev.itemsCreator;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    private final FileConfiguration fileConfiguration;

    public Config(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    public void set(String path, Object object) {
        this.fileConfiguration.set(path, object);
    }

    public Object get(String path) {
        return this.fileConfiguration.get(path);
    }

    public String getString(String path) {
        return this.fileConfiguration.getString(path);
    }

    public int getInt(String path) {
        return this.fileConfiguration.getInt(path);
    }

}
