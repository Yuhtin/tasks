package com.yuhtin.commissions.tasks.command;

import com.yuhtin.commissions.tasks.TasksPlugin;
import com.yuhtin.commissions.tasks.manager.TasksManager;
import com.yuhtin.commissions.tasks.model.User;
import com.yuhtin.commissions.tasks.view.TasksView;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class TasksCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        if (sender instanceof ConsoleCommandSender) return false;

        Player player = (Player) sender;

        TasksManager tasksManager = TasksPlugin.getInstance().getTasksManager();
        User user = tasksManager.getByPlayer(player);
        TasksView.singleton().openInventory(player, viewer -> viewer.getPropertyMap().set("user", user));
        return true;
    }
}
