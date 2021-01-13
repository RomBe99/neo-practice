package ru.rombe.neopractice.manager.data;

import ru.rombe.neopractice.manager.Updatable;

public interface DataManager<R> extends Updatable {
    R getData();
}