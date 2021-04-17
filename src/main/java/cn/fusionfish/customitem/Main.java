package cn.fusionfish.customitem;

import cn.fusionfish.customitem.listeners.ItemListener;
import cn.fusionfish.customitem.manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main plugin;
    private final ItemManager manager = new ItemManager();

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getPluginManager().registerEvents(new ItemListener(), this);
    }

    public static Main getInstance() {
        return plugin;
    }

    public ItemManager getManager() {
        return manager;
    }
}
