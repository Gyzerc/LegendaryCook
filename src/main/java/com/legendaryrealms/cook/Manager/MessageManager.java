package com.legendaryrealms.cook.Manager;

import com.legendaryrealms.cook.Utils.MsgUtils;
import eu.decentsoftware.holograms.api.utils.scheduler.S;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class MessageManager {

    public static String plugin;
    public static String level_up;
    public static String level_take;
    public static String exp_gain;
    public static String exp_lost;
    public static String other_online;
    public static String other_exists;
    public static String other_math;
    public static String cook_noexists;
    public static String cook_alreadyexists;
    public static String cook_put;
    public static String cook_cancel;
    public static String cook_back_gaming;
    public static String cook_timeout;
    public static String cook_timeout_title;
    public static String cook_locked;
    public static String cook_add_recipes_experience;
    public static String cook_take_recipes_experience;
    public static List<String> cook_message;
    public static String cook_begin_title;
    public static String pot_too_far;
    public static String pot_cant;
    public static String pot_cant_world;
    public static String pot_remove;
    public static String cook_cancel_title;
    public static String other_unlock;
    public static String other_unlock_admin;

    public static void load()
    {
        File file=new File("./plugins/LegendaryCook","message.yml");
        YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);

        plugin= MsgUtils.msg(yml.getString("plugin",""));
        level_up=MsgUtils.msg(yml.getString("level.up",""));
        level_take=MsgUtils.msg(yml.getString("level.take",""));

        exp_gain=MsgUtils.msg(yml.getString("exp.gain",""));
        exp_lost=MsgUtils.msg(yml.getString("exp.lost",""));

        other_online=MsgUtils.msg(yml.getString("other.online",""));
        other_exists=MsgUtils.msg(yml.getString("other.exists",""));
        other_math=MsgUtils.msg(yml.getString("other.math",""));
        other_unlock=MsgUtils.msg(yml.getString("other.unlock",""));
        other_unlock_admin=MsgUtils.msg(yml.getString("other.unlock_admin",""));

        cook_noexists=MsgUtils.msg(yml.getString("cook.noexists",""));
        cook_alreadyexists=MsgUtils.msg(yml.getString("cook.alreadyexists",""));
        cook_put=MsgUtils.msg(yml.getString("cook.put",""));
        cook_message=MsgUtils.msg(yml.getStringList("cook.message"));
        cook_back_gaming=MsgUtils.msg(yml.getString("cook.back_gaming",""));
        cook_begin_title=MsgUtils.msg(yml.getString("cook.begin_title",""));
        cook_cancel=MsgUtils.msg(yml.getString("cook.cancel",""));
        cook_cancel_title=MsgUtils.msg(yml.getString("cook.cancel_title",""));
        cook_add_recipes_experience=MsgUtils.msg(yml.getString("cook.add_recipes_experience",""));
        cook_take_recipes_experience=MsgUtils.msg(yml.getString("take_recipes_experience",""));
        cook_timeout =MsgUtils.msg(yml.getString("cook.timeout",""));
        cook_timeout_title=MsgUtils.msg(yml.getString("cook.timeout_title",""));
        cook_locked=MsgUtils.msg(yml.getString("cook.locked",""));

        pot_cant=MsgUtils.msg(yml.getString("pot.cant",""));
        pot_too_far=MsgUtils.msg(yml.getString("pot.too_far",""));
        pot_cant_world=MsgUtils.msg(yml.getString("pot.cant_world",""));
        pot_remove=MsgUtils.msg(yml.getString("pot.remove",""));
    }

}
