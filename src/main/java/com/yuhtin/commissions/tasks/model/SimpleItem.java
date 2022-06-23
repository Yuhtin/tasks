package com.yuhtin.commissions.tasks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SimpleItem {

    private final String customName;
    private final int id;
    private final short data;
    private final int quantity;

}
