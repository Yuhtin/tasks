package com.yuhtin.commissions.tasks.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SimpleItem {

    private final String customName;
    private final int id;
    private final int data;
    private final int quantity;

}
