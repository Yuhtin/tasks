package com.yuhtin.commissions.tasks.model;

import lombok.Builder;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Builder
@Data
public class Task {

    private final String identifier;
    private final String displayName;
    private final ItemStack icon;

    // Reward list

    private final List<String> rewardCommandsList;

    // Cost list

    private final List<SimpleItem> itemCostList;
    private final double coinsCost;

}
