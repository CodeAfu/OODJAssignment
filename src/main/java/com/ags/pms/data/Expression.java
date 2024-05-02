package com.ags.pms.data;

@FunctionalInterface
public interface Expression<T> {
    boolean action(T t);
}
