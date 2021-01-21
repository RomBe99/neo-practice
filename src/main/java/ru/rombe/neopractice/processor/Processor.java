package ru.rombe.neopractice.processor;

import ru.rombe.neopractice.manager.Updatable;
import ru.rombe.neopractice.manager.data.DataManager;
import ru.rombe.neopractice.manager.filter.FiltersManager;
import ru.rombe.neopractice.manager.property.PropertiesManager;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * This class provides interaction between managers, allowing analysis of data received from managers.
 *
 * @param <P>  type of property stored in PropertiesManager
 * @param <V>  type of value stored in PropertiesManager
 * @param <FI> type of filter id stored in FiltersManager
 * @see Updatable
 * @see PropertiesManager
 * @see DataManager
 * @see FiltersManager
 */
public class Processor<P, V, FI> implements Updatable {
    private PropertiesManager<P, V> propertiesManager;
    private DataManager<List<Map<P, V>>> dataManager;
    private FiltersManager<FI, Predicate<Map<P, V>>> filtersManager;

    /**
     * @param propertiesManager properties manager
     * @param dataManager       data manager
     * @param filtersManager    filters manager
     * @see PropertiesManager
     * @see DataManager
     * @see FiltersManager
     */
    public Processor(PropertiesManager<P, V> propertiesManager,
                     DataManager<List<Map<P, V>>> dataManager,
                     FiltersManager<FI, Predicate<Map<P, V>>> filtersManager) {
        this.propertiesManager = propertiesManager;
        this.dataManager = dataManager;
        this.filtersManager = filtersManager;
    }

    /**
     * Return amount of data filtered by a given filter.
     *
     * @param filterId filter identifier for analysis
     * @return amount of data filtered by a given filter
     */
    public int countDataByFilterId(FI filterId) {
        return (int) dataManager.getData().stream()
                .filter(m -> {
                    if (m.isEmpty()) {
                        return false;
                    }

                    for (P p : m.keySet()) {
                        if (!propertiesManager.containsPropertyValue(p, m.get(p))) {
                            return false;
                        }
                    }

                    return true;
                })
                .filter(filtersManager.getFilter(filterId))
                .count();
    }

    public void setPropertiesManager(PropertiesManager<P, V> propertiesManager) {
        this.propertiesManager = propertiesManager;
    }

    public void setDataManager(DataManager<List<Map<P, V>>> dataManager) {
        this.dataManager = dataManager;
    }

    public void setFiltersManager(FiltersManager<FI, Predicate<Map<P, V>>> filtersManager) {
        this.filtersManager = filtersManager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Processor<?, ?, ?> processor = (Processor<?, ?, ?>) o;
        return Objects.equals(propertiesManager, processor.propertiesManager) && Objects.equals(dataManager, processor.dataManager) && Objects.equals(filtersManager, processor.filtersManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertiesManager, dataManager, filtersManager);
    }

    /**
     * Update all managers implemented interface Updatable.
     *
     * @throws Exception If all managers implementing Updatable cannot successfully complete updates
     * @see Updatable
     */
    @Override
    public void update() throws Exception {
        this.propertiesManager.update();
        this.filtersManager.update();
        this.dataManager.update();
    }
}