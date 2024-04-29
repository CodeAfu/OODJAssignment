package com.ags.pms.data;

import com.ags.pms.models.Student;

@FunctionalInterface
public <T extends User> interface Expression<T> {
    boolean action(T t);
}
