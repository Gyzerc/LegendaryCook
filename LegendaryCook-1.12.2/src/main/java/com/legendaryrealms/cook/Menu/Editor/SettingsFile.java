package com.legendaryrealms.cook.Menu.Editor;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class SettingsFile {


    public static HashMap<String,String> editor_material=new HashMap<>();

    public static void load()
    {
        File file=new File("./plugins/LegendaryCook","settings.yml");
        YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
        for (String key:yml.getConfigurationSection("editor").getKeys(false))
        {
            editor_material.put(key,yml.getString("editor."+key));
        }

    }

}
