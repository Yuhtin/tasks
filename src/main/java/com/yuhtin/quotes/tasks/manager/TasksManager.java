package com.yuhtin.quotes.tasks.manager;

import com.yuhtin.quotes.tasks.TasksPlugin;
import com.yuhtin.quotes.tasks.cache.TasksCache;
import com.yuhtin.quotes.tasks.model.User;
import com.yuhtin.quotes.tasks.sql.TasksDAO;
import com.yuhtin.quotes.tasks.sql.connection.SQLConnection;
import com.yuhtin.quotes.tasks.sql.connection.mysql.MySQLConnection;
import com.yuhtin.quotes.tasks.sql.connection.sqlite.SQLiteConnection;
import lombok.Getter;
import lombok.val;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

@Getter
public class TasksManager {

    private final TasksCache tasksCache = new TasksCache();
    private TasksDAO tasksDAO;

    public void init() {
        ConfigurationSection connectionSection = TasksPlugin.getInstance().getConfig().getConfigurationSection("connection");
        SQLConnection sql = new MySQLConnection();
        if (!sql.configure(connectionSection.getConfigurationSection("mysql"))) {
            sql = new SQLiteConnection();
            sql.configure(connectionSection.getConfigurationSection("sqlite"));
        }

        tasksDAO = new TasksDAO();
        tasksDAO.setSqlConnection(sql);
        tasksDAO.createTable();

        tasksCache.init();
    }

    public User getByPlayer(Player player) {
        User cached = tasksCache.getCache().getOrDefault(player.getName(), null);
        if (cached != null) return cached;

        User farm = tasksDAO.find(player.getName());
        if (farm == null) {
            farm = new User(player.getName());
            tasksDAO.save(player.getName(), farm);
        }

        tasksCache.getCache().put(player.getName(), farm);
        return farm;
    }

    public void save() {
        for (val entry : tasksCache.getCache().entrySet()) {
            tasksDAO.save(entry.getKey(), entry.getValue());
        }
    }
}
