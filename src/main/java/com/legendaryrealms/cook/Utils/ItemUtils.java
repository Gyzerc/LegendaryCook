package com.legendaryrealms.cook.Utils;

import com.legendaryrealms.cook.Data.PlayerData;
import com.legendaryrealms.cook.LegendaryCook;
import com.legendaryrealms.cook.Loots.Loots;
import com.legendaryrealms.cook.Manager.MessageManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ItemUtils {

    public HashMap<String, ItemStack> cache=new HashMap<>();

    public ItemUtils()
    {
        File file=new File("./plugins/LegendaryCook","Items.yml");
        YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
        if (yml.getConfigurationSection("Items")!=null)
        {
            for (String id:yml.getConfigurationSection("Items").getKeys(false))
            {
                cache.put(id, yml.getItemStack("Items."+id));
            }
        }
    }

    public ItemStack getSaveItem(String id,int amount)
    {
        ItemStack i=cache.get(id);
        if (i==null)
        {
            return null;
        }
        i.setAmount(amount);
        return i;
    }

    public boolean hasSaveItem(String id)
    {
        return cache.containsKey(id);
    }
    public void saveItem(ItemStack i,String id)
    {
        File file=new File("./plugins/LegendaryCook","Items.yml");
        YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
        yml.set("Items."+id,i);
        try {
            yml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cache.put(id,i);
    }
    public ItemStack getItemArg(Player p,String cookId,String arg)
    {
        if (arg.contains(";"))
        {
            String[] args=arg.split(";");
            List<String> tags= Arrays.asList("mi","ia","mm","default","loot","save","ni");
            if (tags.contains(args[0]))
            {
                String tag=args[0];
                switch (tag)
                {
                    case "loot":
                        if (cookId!=null) {
                            return LegendaryCook.getInstance().getLootsManager().getGroupItem(p,PlayerData.getPlayerData(p).getRecipe_Experience(cookId),args[1]);
                        }
                        break;
                    case "default":
                        if (Material.getMaterial(args[1])!=null)
                        {
                            ItemStack i=new ItemStack(Material.getMaterial(args[1]),getAmount(args[3]),(short)Integer.parseInt(args[2]));
                            return i;
                        }
                        break;
                    case "mm":
                        if (LegendaryCook.getInstance().getHooks().getMythicMobHook()==null)
                        {
                            return null;
                        }
                        if (LegendaryCook.getInstance().getHooks().getMythicMobHook().isExists(args[1]))
                        {
                            ItemStack i=LegendaryCook.getInstance().getHooks().getMythicMobHook().getItem(args[1],getAmount(args[2]));

                            return i;
                        }
                        break;
                    case "save":
                        if (LegendaryCook.getInstance().getItemUtils().hasSaveItem(args[1]))
                        {
                            return getSaveItem(args[1],getAmount(args[2]));
                        }
                        break;
                    case "ni":
                        if (LegendaryCook.getInstance().getHooks().getNeigeItemsHook()==null)
                        {
                            return null;
                        }
                        if (LegendaryCook.getInstance().getHooks().getNeigeItemsHook().hasItem(args[1]))
                        {
                            ItemStack i=LegendaryCook.getInstance().getHooks().getNeigeItemsHook().getItem(args[1]);
                            i.setAmount(getAmount(args[2]));
                            return i;
                        }
                    default:
                        return null;
                }
            }
        }
        return  null;
    }
    public void giveItemArg(Player p,String cookId,String arg)
    {
        if (arg.contains(";"))
        {
            String[] args=arg.split(";");
            List<String> tags= Arrays.asList("mi","ia","mm","default","loot","save","ni");
            if (tags.contains(args[0]))
            {
                String tag=args[0];
                switch (tag)
                {

                    case "loot":
                        if (cookId!=null) {
                            LegendaryCook.getInstance().getLootsManager().runGroup(p, PlayerData.getPlayerData(p).getRecipe_Experience(cookId),args[1]);
                            return;
                        }
                        break;
                    case "default":
                        if (Material.getMaterial(args[1])!=null)
                        {
                           ItemStack i=new ItemStack(Material.getMaterial(args[1]),getAmount(args[3]),(short)Integer.parseInt(args[2]));
                           p.getInventory().addItem(i);
                            return;
                        }
                        return;
                    case "mm":
                        if (LegendaryCook.getInstance().getHooks().getMythicMobHook()==null)
                        {
                            return;
                        }
                        if (LegendaryCook.getInstance().getHooks().getMythicMobHook().isExists(args[1]))
                        {
                            ItemStack i=LegendaryCook.getInstance().getHooks().getMythicMobHook().getItem(args[1],getAmount(args[2]));
                            p.getInventory().addItem(i);
                            return;
                        }
                        return;
                    case "save":
                        if (LegendaryCook.getInstance().getItemUtils().hasSaveItem(args[1]))
                        {
                            p.getInventory().addItem(getSaveItem(args[1],getAmount(args[2])));
                            return;
                        }
                        return;
                    case "ni":
                        if (LegendaryCook.getInstance().getHooks().getNeigeItemsHook()==null)
                        {
                            return;
                        }
                        if (LegendaryCook.getInstance().getHooks().getNeigeItemsHook().hasItem(args[1]))
                        {
                            ItemStack i=LegendaryCook.getInstance().getHooks().getNeigeItemsHook().getItem(args[1]);
                            i.setAmount(getAmount(args[2]));
                            p.getInventory().addItem(i);
                            return;
                        }
                    default:
                        return;
                }
            }
        }
    }
    public boolean checkIsItemArg(Player p,String arg)
    {
        if (arg.contains(";"))
        {
            String[] args=arg.split(";");
            List<String> tags= Arrays.asList("mi","ia","mm","default","loot","save","ni");
            if (tags.contains(args[0]))
            {
                String tag=args[0];
                switch (tag)
                {
                    case "loot":
                        if (Loots.getLoots(args[1])!=null)
                        {
                            return true;
                        }
                        p.sendMessage(MessageManager.plugin+"没有该奖励组");
                        return false;
                    case "default":
                        if (Material.getMaterial(args[1])!=null)
                        {
                            if (!StringUtils.isNum(args[3])||!StringUtils.isNum(args[2]))
                            {
                                p.sendMessage(MessageManager.plugin+"请输入一个正确的数字！");
                                return false;
                            }
                            return true;
                        }
                        p.sendMessage(MessageManager.plugin+"该材质 "+args[1]+" 不存在！");
                        return false;
                    case "mm":
                        if (LegendaryCook.getInstance().getHooks().getMythicMobHook()==null)
                        {
                            p.sendMessage(MessageManager.plugin+"未检测到有安装 MythicMobs");
                            return false;
                        }
                        if (LegendaryCook.getInstance().getHooks().getMythicMobHook().isExists(args[1]))
                        {
                            if (!StringUtils.isNum(args[2]))
                            {
                                p.sendMessage(MessageManager.plugin+"请输入一个正确的数字！");
                                return false;
                            }
                            return true;
                        }
                        p.sendMessage(MessageManager.plugin+"MythicMobs插件内并不存在 "+args[1]+" 的物品！");
                        return false;
                    case "save":
                        if (LegendaryCook.getInstance().getItemUtils().hasSaveItem(args[1]))
                        {
                            if (!StringUtils.isNum(args[2]))
                            {
                                p.sendMessage(MessageManager.plugin+"请输入一个正确的数字！");
                                return false;
                            }
                            return true;
                        }
                        p.sendMessage(MessageManager.plugin+"插件内并未保存ID为: "+args[1]+" 的物品！");
                        return false;
                    case "ni":
                        if (LegendaryCook.getInstance().getHooks().getNeigeItemsHook()==null)
                        {
                            p.sendMessage(MessageManager.plugin+"未检测到有安装 NeigeItems");
                            return false;
                        }
                        if (LegendaryCook.getInstance().getHooks().getNeigeItemsHook().hasItem(args[1]))
                        {
                            if (!StringUtils.isNum(args[2]))
                            {
                                p.sendMessage(MessageManager.plugin+"请输入一个正确的数字！");
                                return false;
                            }
                            return true;
                        }
                        p.sendMessage(MessageManager.plugin+"NeigeItems 并未保存ID为: "+args[2]+" 的物品！");
                        return false;
                    default:
                        return true;
                }
            }
            p.sendMessage(MessageManager.plugin+"该标识 "+args[0]+" 不存在！");
            return false;
        }
        return false;
    }

    public int getAmount(String arg)
    {
        try {
            if (arg.contains("-")) {
                int min = Integer.parseInt(arg.substring(0, arg.indexOf("-")));
                int max = Integer.parseInt(arg.substring(arg.indexOf("-") + 1));
                return (new Random()).nextInt(max - min) + min;
            }
            int amount=Integer.parseInt(arg);
            return amount;
        }
        catch (Exception e)
        {
            return 1;
        }
    }
}
