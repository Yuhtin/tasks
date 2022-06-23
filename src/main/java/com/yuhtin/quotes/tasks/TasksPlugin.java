package com.yuhtin.quotes.tasks;

import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.yuhtin.quotes.tasks.cache.TasksCache;
import com.yuhtin.quotes.tasks.command.TasksCommand;
import com.yuhtin.quotes.tasks.manager.TasksManager;
import com.yuhtin.quotes.tasks.util.EconomyHook;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class TasksPlugin extends JavaPlugin {

    private TasksManager tasksManager;
    private EconomyHook economyHook;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        InventoryManager.enable(this);

        economyHook = new EconomyHook();
        economyHook.init();

        tasksManager = new TasksManager();
        tasksManager.init();

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
