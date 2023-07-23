package com.legendaryrealms.cook.Command;

import com.legendaryrealms.cook.Data.Database.Config;
import com.legendaryrealms.cook.Data.Database.MysqlManager;
import com.legendaryrealms.cook.Data.Database.PlayerDataYamlManager;
import com.legendaryrealms.cook.Manager.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ConvertCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.isOp()) {
            if (!Bukkit.getPluginManager().isPluginEnabled("LegendaryAlchemy"))
            {
                commandSender.sendMessage(MessageManager.plugin+"请先确保安装了 LegendaryAlchemy");
                return true;
            }
            commandSender.sendMessage(MessageManager.plugin+"开始转换玩家数据..请查看后台");
            if (Config.useMysql) {
                MysqlManager.convert();
            } else {
                PlayerDataYamlManager.convert();
            }
        }
        return true;
    }
}
