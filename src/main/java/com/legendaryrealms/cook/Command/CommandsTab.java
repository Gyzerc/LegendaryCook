package com.legendaryrealms.cook.Command;

import com.legendaryrealms.cook.Manager.CooksManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandsTab implements TabCompleter {
    private List<String> filter(List<String> list, String latest) {
        if (list.isEmpty() || latest == null)
            return list;
        String list2 = latest.toLowerCase();
        list.removeIf(k -> !k.toLowerCase().startsWith(list2));
        return list;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String latest = null;
        List<String> list = new ArrayList<>();
        if (args.length != 0)
            latest = args[args.length - 1];
        if (args.length == 1) {
            list.add("set");
            list.add("admin");
        }
        else if (args.length == 2 )
        {
            if (args[0].equals("admin") && sender.isOp())
            {
                list.add("unlock");
                list.add("create");
                list.add("edit");
                list.add("saveitem");
                list.add("exp");
                list.add("level");
                list.add("reload");
            }
        }
        else if (args.length == 3)
        {
            List<String> sub1= Arrays.asList("exp","level");
            if (args[0].equals("admin") && sender.isOp())
            {
                if (sub1.contains(args[1]))
                {
                    list.add("give");
                    list.add("take");
                }
                else if (args[1].equals("create") || args[1].equals("saveitem"))
                {
                    list.add("ID");
                }
                else if (args[1].equals("edit"))
                {
                    for (String id: CooksManager.getFiles("./plugins/LegendaryCook/Cooks"))
                    {
                        list.add(id.replace(".yml",""));
                    }
                }
                else if (args[1].equals("unlock"))
                {
                    for (Player p:Bukkit.getOnlinePlayers())
                    {
                        list.add(p.getName());
                    }
                }
            }

        }
        else if (args.length == 4) {
            List<String> sub1 = Arrays.asList("exp", "level");
            if (args[0].equals("admin") && sender.isOp()) {
                if (sub1.contains(args[1]))
                {
                    for (Player p: Bukkit.getOnlinePlayers())
                    {
                        list.add(p.getName());
                    }
                }
                else if (args[1].equals("unlock"))
                {
                    for (String cook:CooksManager.getFiles("./plugins/LegendaryCook/Cooks"))
                    {
                        list.add(cook.replace(".yml",""));
                    }
                }
            }
        }
        else if (args.length == 5) {
            List<String> sub1 = Arrays.asList("exp", "level");
            if (args[0].equals("admin") && sender.isOp()) {
                if (sub1.contains(args[1])) {
                    list.add("数量");
                }

            }
        }
        return filter(list, latest);
    }
}
