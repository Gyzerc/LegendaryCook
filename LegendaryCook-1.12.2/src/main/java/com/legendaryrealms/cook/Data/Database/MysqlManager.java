package com.legendaryrealms.cook.Data.Database;

import com.gyzer.lianyao.legendaryalchemy.API.PlayerAPI;
import com.gyzer.lianyao.legendaryalchemy.LegendaryAlchemy;
import com.legendaryrealms.cook.Data.PlayerData;
import com.legendaryrealms.cook.LegendaryCook;
import com.legendaryrealms.cook.Manager.MessageManager;
import io.lumine.xikage.mythicmobs.utils.storage.sql.hikari.HikariConfig;
import io.lumine.xikage.mythicmobs.utils.storage.sql.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlManager {

    private static HikariDataSource connectPool;

    public static void setConnectPool()
    {
        HikariConfig hikariConfig=new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        ConfigurationSection section= LegendaryCook.getInstance().getConfig().getConfigurationSection("HikariCP");
        hikariConfig.setConnectionTimeout(section.getLong("connectionTimeout"));
        hikariConfig.setMinimumIdle(section.getInt("minimumIdle"));
        hikariConfig.setMaximumPoolSize(section.getInt("maximumPoolSize"));

        section=LegendaryCook.getInstance().getConfig().getConfigurationSection("Mysql");
        String url="jdbc:mysql://"+
                section.getString("address")+":"+
                section.getString("port")+"/"+
                section.getString("database")+"?useUnicode=treu&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(section.getString("user"));
        hikariConfig.setPassword(section.getString("password"));
        hikariConfig.setAutoCommit(true);

        connectPool=new HikariDataSource(hikariConfig);

        enableMysql();
    }
    public Connection getConnection()
    {
        try {
            return connectPool.getConnection();

        } catch (Exception ex)
        {
            LegendaryCook.getInstance().getLogger().warning("数据库连接失败！");
        }
        return null;
    }

    public static void enableMysql()
    {
        try {
            Connection connection= connectPool.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS `LegendaryCook_data` (" +
                    "`player` VARCHAR(100) NOT NULL," +
                    "`data` TEXT DEFAULT NULL," +
                    "PRIMARY KEY (`player`))");
            statement.close();
            connection.close();
            LegendaryCook.getInstance().getLogger().info("成功与数据库建立连接！");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static synchronized boolean hasPlayerData(String player) {
        try {
            Connection connection = connectPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `data` FROM `LegendaryCook_data` WHERE player=?;",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String res="";
            preparedStatement.setString(1, player);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
            preparedStatement.close();
            connection.close();
            return false;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }


    public static synchronized String getPlayerStringData(String player) {
        try {
            Connection connection = connectPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `data` FROM `LegendaryCook_data` WHERE player=?;",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String res="";
            preparedStatement.setString(1, player);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                rs.first();
                res = rs.getString("data");
            }
            preparedStatement.close();
            connection.close();
            return res;
        } catch (SQLException e) {

            e.printStackTrace();
            return "";
        }
    }

    public static synchronized void setPlayerString(String player, String data) {
        try {
            Connection connection = connectPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `LegendaryCook_data` " +
                    "(`player`, `data`)" +
                    "VALUES (?, ?) ON DUPLICATE KEY UPDATE `data`=?;",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, player);
            preparedStatement.setString(2, data);
            preparedStatement.setString(3, data);
            preparedStatement.execute();
            preparedStatement.close();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void convert()
    {
        String hostname = LegendaryAlchemy.LegendaryAlchemy.getConfig().getString("Store.mysql.hostname");
        String databaseName = LegendaryAlchemy.LegendaryAlchemy.getConfig().getString("Store.mysql.databasename");
        String userName = LegendaryAlchemy.LegendaryAlchemy.getConfig().getString("Store.mysql.user");
        String userPassword = LegendaryAlchemy.LegendaryAlchemy.getConfig().getString("Store.mysql.password");
        int port = LegendaryAlchemy.LegendaryAlchemy.getConfig().getInt("Store.mysql.port");
        String db_url = "jdbc:mysql://"+hostname+":" + port + "/" + databaseName + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        List<String> res = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(db_url, userName, userPassword);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `player` FROM `LegendaryAlchemy`;");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
                res.add(rs.getString("guild"));
            preparedStatement.close();
            connection.close();
            for (String p:res)
            {
                Map<String , Object> stats=new HashMap<>();
                stats.put("level", PlayerAPI.getLevel(p));
                stats.put("exp", PlayerAPI.getExp(p));
                stats.put("unlock",PlayerAPI.getUnlocks(p));
                PlayerData.create(p,stats);
                Bukkit.getConsoleSender().sendMessage(MessageManager.plugin+"已转换玩家 "+p+" 的数据...");
            }

            return;
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }


    }
}
