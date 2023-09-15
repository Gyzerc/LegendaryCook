package com.legendaryrealms.cook.Command;

import com.legendaryrealms.cook.Data.Cook.Cook;
import com.legendaryrealms.cook.Data.Cook.CookRecipe;
import com.legendaryrealms.cook.Data.Database.Config;
import com.legendaryrealms.cook.Data.Pot.PotBlock;
import com.legendaryrealms.cook.Game.CookGame;
import com.legendaryrealms.cook.Manager.CooksManager;
import com.legendaryrealms.cook.Menu.Categorize.CategorizesMenu;
import com.legendaryrealms.cook.Menu.Editor.EditorMenu;
import com.legendaryrealms.cook.Data.PlayerData;
import com.legendaryrealms.cook.Data.Cook.CookPot;
import com.legendaryrealms.cook.LegendaryCook;
import com.legendaryrealms.cook.Manager.MessageManager;
import com.legendaryrealms.cook.Menu.MaterialPut.MaterialPutMenu;
import com.legendaryrealms.cook.Utils.MsgUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand( CommandSender sender,  Command cmd, String s,  String[] args) {

        if (args.length==0) {
            sender.sendMessage(MessageManager.plugin + MsgUtils.msg("&e&l 指令帮助"));
            sender.sendMessage(MsgUtils.msg("&f &f &7/legendarycook put &f—— &3放置烹饪锅"));
            if (sender.isOp()) {
                sender.sendMessage(MsgUtils.msg("&f &f &7/legendarycook admin &f—— &3管理员指令"));

            }
        }
        else if (args.length==1)
        {
            if (args[0].equals("put"))
            {
                if (sender instanceof Player) {
                    Player p=(Player) sender;
                    if (CookPot.getCookPot(p)==null) {
                        if (PotBlock.canPut(p)) {
                            Location loc = p.getTargetBlock(null, Config.pot_block_max_put_distance).getLocation().clone().add(0,1,0);
                            CookPot cookPot = new CookPot(p, loc);
                            return true;
                        }
                    }
                    else {
                        if (CookPot.getCookPot(p).isInGame())
                        {
                            p.sendMessage(MessageManager.plugin+MessageManager.cook_back_gaming);
                            return true;
                        }
                        CookPot.getCookPot(p).removePot();
                        p.sendMessage(MessageManager.plugin+MessageManager.pot_remove);
                        return true;
                    }
                }
            }
            if (args[0].equals("admin") && sender.isOp())
            {
                sender.sendMessage(MessageManager.plugin + MsgUtils.msg("&c&l 管理员指令帮助"));
                sender.sendMessage(MsgUtils.msg("&f &f &7/legendarycook admin unlock 玩家 配方ID &f—— &3为玩家解锁一个配方"));
                sender.sendMessage(MsgUtils.msg("&f &f &7/legendarycook admin create ID &f—— &3创建一个新的配方并打开编辑"));
                sender.sendMessage(MsgUtils.msg("&f &f &7/legendarycook admin edit ID &f—— &3打开编辑"));
                sender.sendMessage(MsgUtils.msg("&f &f &7/legendarycook admin saveitem ID &f—— &3将手上物品保存到插件内"));
                sender.sendMessage(MsgUtils.msg("&f &f &7/legendarycook admin exp give/take 玩家 数量 &f—— &3给与/扣除玩家烹饪经验"));
                sender.sendMessage(MsgUtils.msg("&f &f &7/legendarycook admin level give/take 玩家 数量 &f—— &3给与/扣除玩家烹饪经验"));
            }
        }
        else if (args.length==2)
        {
            if (args[0].equals("admin") && sender.isOp())
            {
                if (args[1].equals("reload"))
                {
                    long t=System.currentTimeMillis();
                    LegendaryCook.getInstance().reload();
                    sender.sendMessage(MessageManager.plugin + "插件重载成功！耗时 "+MsgUtils.msg("&e"+(System.currentTimeMillis()-t)+"ms"));
                    return true;
                }
            }
        }
        else if (args.length==3)
        {
            if (args[0].equals("admin") && sender.isOp())
            {
                if (checkCmd(sender,args,new String[] {"sub;1;saveitem"},true)) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(MessageManager.plugin + "该指令只能由玩家执行.");
                        return true;
                    }
                    if (LegendaryCook.getInstance().getItemUtils().hasSaveItem(args[2])) {
                        sender.sendMessage(MessageManager.plugin + MsgUtils.msg("&e ")+"该ID的物品已经存在了！输入 " + MsgUtils.msg("&a/cook admin saveitem " + args[2] + " cover&e ") + " 可覆盖");
                        return true;
                    }
                    Player p = (Player) sender;
                    if (p.getInventory().getItemInMainHand() == null|| p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                        sender.sendMessage(MessageManager.plugin+"请手持需要保存到插件内的物品.");
                        return true;
                    }
                        LegendaryCook.getInstance().getItemUtils().saveItem(p.getInventory().getItemInMainHand(),args[2]);
                        sender.sendMessage(MessageManager.plugin + "成功保存物品: " + MsgUtils.msg("&e" + args[2]));
                        return true;
                }
                if (checkCmd(sender,args,new String[] {"sub;1;create"},true))
                {
                    if (Cook.getCook(args[2])!=null)
                    {
                        sender.sendMessage(MessageManager.plugin+MessageManager.cook_alreadyexists);
                        return true;
                    }
                    EditorMenu menu=new EditorMenu(args[2],false);
                    menu.LoadMenu();
                    menu.updataIcons();
                    Player p=(Player)sender;
                    p.openInventory(menu.getInventory());
                    return true;
                }
                if (checkCmd(sender,args,new String[] {"sub;1;edit","exists_cook;2"},true))
                {
                    EditorMenu menu=new EditorMenu(args[2],false);
                    menu.LoadMenu();
                    menu.updataIcons();
                    Player p=(Player)sender;
                    p.openInventory(menu.getInventory());
                    return true;
                }
            }
        }
        else if (args.length==4)
        {
            if (checkCmd(sender,args,new String[] {"sub;1;unlock","online;2","exists;2","exists_cook;3"},true))
            {
                Player p=Bukkit.getPlayer(args[2]);
                PlayerData data=PlayerData.getPlayerData(p);
                if (data!=null)
                {
                    sender.sendMessage(MessageManager.plugin+MessageManager.other_unlock_admin.replace("%player%",args[2]).replace("%cook%", Cook.getCook(args[3]).getName()));
                    if (data.getUnlocks().contains(args[3]))
                    {
                        return true;
                    }
                    data.unlock(args[3]);
                    p.sendMessage(MessageManager.plugin+MessageManager.other_unlock.replace("%cook%",Cook.getCook(args[3]).getName()));
                }
            }
            if (checkCmd(sender,args,new String[] {"sub;1;saveitem","sub;3;cover"},true)) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(MessageManager.plugin + "该指令只能由玩家执行.");
                    return true;
                }
                Player p = (Player) sender;

                if (p.getInventory().getItemInMainHand() == null|| p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                    sender.sendMessage(MessageManager.plugin+"请手持需要保存到插件内的物品.");
                    return true;
                }
                    LegendaryCook.getInstance().getItemUtils().saveItem(p.getInventory().getItemInMainHand(),args[2]);
                    sender.sendMessage(MessageManager.plugin + "成功保存物品: " + MsgUtils.msg("&e" + args[2]));
                    return true;
            }
        }
        else if (args.length==5)
        {
            if (checkCmd(sender,args,new String[] {"sub;1;exp","online;3","math;4"},true))
            {
                boolean give=true;
                if (args[2].equals("take"))
                {
                    give=false;
                }
                double amount=Double.parseDouble(args[4]);
                PlayerData data= PlayerData.getPlayerData(Bukkit.getPlayer(args[3]));
                if (give)
                {
                    data.addCookExp(amount);
                }
                else {
                    data.takeCookExp(amount);
                }
                data.save();

                return true;
            }
            if (checkCmd(sender,args,new String[] {"sub;1;level","online;3","math;4"},true))
            {
                boolean give=true;
                if (args[2].equals("take"))
                {
                    give=false;
                }
                int amount=Integer.parseInt(args[4]);
                PlayerData data= PlayerData.getPlayerData(Bukkit.getPlayer(args[3]));
                if (give)
                {
                    data.addCookLevel(amount);
                }
                else {
                    data.takeCookLevel(amount);
                }
                data.save();

                return true;
            }
        }
        return true;
    }

    public boolean checkCmd(CommandSender sender,String[] args,String[] require,boolean needAdmin) {

        if (needAdmin && sender.isOp()) {
            for (String arg:require)
            {
                String[] split=arg.split(";");
                if (split[0].equals("sub"))
                {
                    if (!args[Integer.parseInt(split[1])].equals(split[2]))
                    {
                        return false;
                    }
                }
                if (split[0].equals("online"))
                {
                    if ((Bukkit.getPlayer(args[Integer.parseInt(split[1])]) == null))
                    {
                        sender.sendMessage(MessageManager.plugin+MessageManager.other_online);
                        return false;
                    }
                }
                if (split[0].equals("exists_cook"))
                {
                    if (Cook.getCook(args[Integer.parseInt(split[1])]) == null)
                    {
                        sender.sendMessage(MessageManager.plugin+MessageManager.cook_noexists);
                        return false;
                    }
                }
                if (split[0].equals("exists"))
                {
                    if (!PlayerData.hasData(args[Integer.parseInt(split[1])]))
                    {
                        sender.sendMessage(MessageManager.plugin+MessageManager.other_exists);
                        return false;
                    }
                }
                if (split[0].equals("math"))
                {
                    if (!isMath(args[Integer.parseInt(split[1])]))
                    {
                        sender.sendMessage(MessageManager.plugin+MessageManager.other_math);
                        return false;
                    }
                }
            }
        }
        else {
            return false;
        }
        return true;
    }

    public static boolean isMath(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i)))
                return false;
        }
        return true;
    }
}
