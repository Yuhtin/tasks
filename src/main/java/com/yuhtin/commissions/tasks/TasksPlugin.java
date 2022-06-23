package com.yuhtin.commissions.tasks;

import com.yuhtin.commissions.tasks.cache.TasksCache;
import com.yuhtin.commissions.tasks.command.TasksCommand;
import com.yuhtin.commissions.tasks.manager.TasksManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class TasksPlugin extends JavaPlugin {

    private TasksCache tasksCache;
    private TasksManager tasksManager;

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        tasksCache = new TasksCache();
        tasksManager = new TasksManager();

        getCommand("tasks").setExecutor(new TasksCommand());
    }

    @Override
    public void onDisable() {
        tasksManager.save();
    }

    public static TasksPlugin getInstance() {
        return getPlugin(TasksPlugin.class);
    }

}
