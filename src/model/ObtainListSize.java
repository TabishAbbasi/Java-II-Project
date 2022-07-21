package model;

import javafx.collections.ObservableList;

/**
 * Helps to obtain the size of the customers in the database.
 *
 * @author Tabish Abbasi
 */
@FunctionalInterface
public interface ObtainListSize {
    public abstract String sizeOf(ObservableList<Customer> list);
}
