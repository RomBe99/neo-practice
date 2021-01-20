package ru.rombe.neopractice.manager;

/**
 * Classes implementing this interface can update them instances.
 */
public interface Updatable {
    /**
     * Update instance.
     *
     * @throws Exception if updating raised an exception
     */
    void update() throws Exception;
}