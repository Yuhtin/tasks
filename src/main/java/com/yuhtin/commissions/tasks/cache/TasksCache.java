package com.yuhtin.commissions.tasks.cache;

import com.yuhtin.commissions.tasks.TasksPlugin;
import com.yuhtin.commissions.tasks.model.Task;
import com.yuhtin.commissions.tasks.model.User;
import com.yuhtin.commissions.tasks.util.ItemBuilder;
import lombok.Getter;
import lombok.val;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class TasksCache {

    private final HashMap<String, User> cache = new HashMap<>();
    private final List<Task> tasks = new ArrayList<>();

    public void init() {
        FileConfiguration config = TasksPlugin.getInstance().getConfig();
        ConfigurationSection section = config.getConfigurationSection("tasks");
        for (String identifier : section.getKeys(false)) {
            ConfigurationSection taskSection = section.getConfigurationSection(identifier);
            ConfigurationSection costsSection = taskSection.getConfigurationSection("costs");

            Task task = Task.builder()
                    .identifier(identifier)
                    .icon(parse(taskSection.getConfigurationSection("icon")))
                    .rewardCommandsList(taskSection.getStringList("rewards"))
                    .itemCostList()
                    .coinsCost(costsSection.getDouble("coins", 0))
                    .build();

            this.tasks.add(task);
        }
    }

    public ItemStack parse(ConfigurationSection section) {
        val builder = new ItemBuilder(
                section.getInt("id", 1),
                section.getInt("data", 0)
        );

        return builder.name(section.getString("displayName"))
                .setLore(section.getStringList("lore"))
                .wrap();
    }

}
