package com.yuhtin.commissions.tasks.view;

import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import com.yuhtin.commissions.tasks.TasksPlugin;
import com.yuhtin.commissions.tasks.model.Task;
import com.yuhtin.commissions.tasks.model.User;
import com.yuhtin.commissions.tasks.util.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

public class TasksView extends PagedInventory {

    private static TasksView instance;

    public TasksView() {
        super("tasks.main", "Missões", 3 * 9);
    }

    public static TasksView singleton() {
        if (instance == null) instance = new TasksView().init();
        return instance;
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(PagedViewer viewer) {
        List<InventoryItemSupplier> items = new ArrayList<>();

        User user = viewer.getPropertyMap().get("user");
        for (Task task : TasksPlugin.getInstance().getTasksCache().getTasks()) {
            boolean completed = user.getCompletedTasksIdList().contains(task.getIdentifier());

            ItemBuilder iconBuilder = new ItemBuilder(task.getIcon());
            iconBuilder.glow(completed);

            items.add(() -> InventoryItem.of(iconBuilder.wrap()).defaultCallback(callback -> {
                if (completed) return;

                // TODO
            }));
        }

        return items;
    }
}
