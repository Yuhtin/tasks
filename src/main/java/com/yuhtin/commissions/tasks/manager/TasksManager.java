package com.yuhtin.commissions.tasks.manager;

import com.yuhtin.commissions.tasks.cache.TasksCache;
import com.yuhtin.commissions.tasks.model.User;
import com.yuhtin.commissions.tasks.sql.TasksDAO;
import com.yuhtin.commissions.tasks.sql.connection.SQLConnection;
import com.yuhtin.commissions.tasks.sql.connection.mysql.MySQLConnection;
import com.yuhtin.commissions.tasks.sql.connection.sqlite.SQLiteConnection;
import lombok.Getter;
import lombok.val;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class TasksManager {

    private final TasksCache storageCache = new TasksCache();
    private TasksDAO tasksDAO;

    public void init(JavaPlugin plugin) {
        ConfigurationSection connectionSection = plugin.getConfig().getConfigurationSection("connection");
        SQLConnection sql = new MySQLConnection();
        if (!sql.configure(connectionSection.getConfigurationSection("mysql"))) {
            sql = new SQLiteConnection();
            sql.configure(connectionSection.getConfigurationSection("sqlite"));
        }

        tasksDAO = new TasksDAO();
        tasksDAO.setSqlConnection(sql);
        tasksDAO.createTable();

        storageCache.init();
    }

    public User getByPlayer(Player player) {
        User cached = storageCache.getCache().getOrDefault(player.getName(), null);
        if (cached != null) return cached;

        User farm = tasksDAO.find(player.getName());
        if (farm == null) {
            farm = new User(player.getName());
            tasksDAO.save(player.getName(), farm);
        }

        storageCache.getCache().put(player.getName(), farm);
        return farm;
    }

    public void save() {
        for (val entry : storageCache.getCache().entrySet()) {
            tasksDAO.save(entry.getKey(), entry.getValue());
        }
    }
}
