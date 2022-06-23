package com.yuhtin.quotes.tasks.view;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.border.Border;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import com.yuhtin.quotes.tasks.TasksPlugin;
import com.yuhtin.quotes.tasks.model.SimpleItem;
import com.yuhtin.quotes.tasks.model.Task;
import com.yuhtin.quotes.tasks.model.User;
import com.yuhtin.quotes.tasks.util.ColorUtil;
import com.yuhtin.quotes.tasks.util.EconomyHook;
import com.yuhtin.quotes.tasks.util.ItemBuilder;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TasksView extends PagedInventory {

    private static TasksView instance;

    public TasksView() {
        super("tasks.main", "Missões", 6 * 9);
    }

    @Override
    protected void configureViewer(PagedViewer viewer) {
        val pagedViewer = viewer.getConfiguration();

        pagedViewer.itemPageLimit(28);
        pagedViewer.border(Border.of(1, 1, 1, 1));
    }

    public static TasksView singleton() {
        if (instance == null) instance = new TasksView().init();
        return instance;
    }

    @Override
    protected void update(Viewer viewer, InventoryEditor editor) {
        super.update(viewer, editor);
        configureInventory(viewer, viewer.getEditor());
    }

    @Override
    protected void configureInventory(Viewer viewer, InventoryEditor editor) {

    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(PagedViewer viewer) {
        List<InventoryItemSupplier> items = new ArrayList<>();

        User user = viewer.getPropertyMap().get("user");
        for (Task task : TasksPlugin.getInstance().getTasksManager().getTasksCache().getTasks()) {
            TasksPlugin.getInstance().getLogger().info(task.getIdentifier());
            boolean completed = user.getCompletedTasksIdList().contains(task.getIdentifier());

            ItemBuilder iconBuilder = new ItemBuilder(task.getIcon());
            iconBuilder.glow(completed);

            items.add(() -> InventoryItem.of(iconBuilder.wrap()).defaultCallback(callback -> {
                if (completed) return;

                Player player = callback.getPlayer();
                EconomyHook economyHook = TasksPlugin.getInstance().getEconomyHook();
                if (!economyHook.has(player, task.getCoinsCost())) {
                    player.sendMessage(ColorUtil.colored("&cVocê não tem dinheiro suficiente para completar esta missão."));
                    return;
                }

                if (!economyHook.withdrawCoins(player, task.getCoinsCost()).transactionSuccess()) {
                    player.sendMessage(ColorUtil.colored("&cVocê não tem dinheiro suficiente para completar esta missão."));
                }

                for (SimpleItem simpleItem : task.getItemCostList()) {
                    if (!canConsume(player, simpleItem, false)) {
                        player.sendMessage(ColorUtil.colored("&cVocê não tem itens suficientes para completar esta missão."));
                        return;
                    }
                }

                for (SimpleItem simpleItem : task.getItemCostList()) {
                    canConsume(player, simpleItem, true);
                }

                for (String commands : task.getRewardCommandsList()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commands.replace("%player%", player.getName()));
                }

                user.getCompletedTasksIdList().add(task.getIdentifier());
                player.sendMessage(ColorUtil.colored("&aVocê completou a missão &6" + task.getIdentifier() + "&a."));

                callback.updateInventory();
            }));
        }

        return items;
    }

    public boolean canConsume(Player player, SimpleItem simpleItem, boolean consume) {
        int count = simpleItem.getQuantity();

        ItemStack[] items = player.getInventory().getContents();
        for (ItemStack item : items) {
            if (count < 1) break;
            if (item == null || item.getType() == Material.AIR) continue;
            if (item.getType().getId() != simpleItem.getItem().getTypeId()) continue;
            if (item.getData().getData() != simpleItem.getItem().getData().getData()) continue;
            if (!item.hasItemMeta()) continue;
            if (!item.getItemMeta().hasDisplayName()) continue;
            if (!item.getItemMeta().getDisplayName().equalsIgnoreCase(simpleItem.getItem().getItemMeta().getDisplayName()))
                continue;

            if (item.getAmount() > count) {
                item.setAmount(item.getAmount() - count);
                count = 0;

                break;
            } else {
                count -= item.getAmount();
                item.setAmount(0);
            }
        }

        if (count > 0) return false;

        if (consume) player.getInventory().setContents(items);
        return true;
    }
}
