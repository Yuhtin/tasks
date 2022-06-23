package com.yuhtin.commissions.tasks.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class User {

    private final String nickname;
    private final List<String> completedTasksIdList = new ArrayList<>();

}
