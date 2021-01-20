package ru.rombe.neopractice.manager.filter;

import ru.rombe.neopractice.manager.Updatable;

public interface FiltersManager<FI, F> extends Updatable {
    F getFilter(FI filterId);
}