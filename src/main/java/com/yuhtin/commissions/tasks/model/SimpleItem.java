package com.yuhtin.commissions.tasks.model;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Builder
@Getter
public class SimpleItem {

    private final ItemStack item;
    private final int quantity;

}
