package com.yuhtin.commissions.tasks.view;

import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;

import java.util.List;

public class TasksView extends PagedInventory {

    private static TasksView instance;

    public TasksView() {
        super("", "title", 3 * 9);
    }

    public static TasksView singleton() {
        if (instance == null) instance = new TasksView().init();
        return instance;
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(PagedViewer viewer) {
        return null;
    }
}
