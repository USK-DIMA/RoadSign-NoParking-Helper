package ru.uskov.dmitry.road.sign.parking.helper.util;

@FunctionalInterface
public interface Supplier<T> {

    T get();
}
