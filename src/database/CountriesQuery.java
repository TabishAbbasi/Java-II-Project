package database;

import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class handles all queries to the Countries table in the database.
 *
 * @author Tabish Abbasi
 */
public class CountriesQuery {

    /**
     * Retrieves all countries from the database and stores them in a list.
     *
     * @param countryList the list to store all the countries
     * @throws SQLException
     */
    public static void retrieveAllCountries(ObservableList<Country> countryList) throws SQLException {
        countryList.clear();
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("Country_ID");
            String name = rs.getString("Country");
            countryList.add(new Country(id, name));
        }
    }

    /**
     * Retrieves a single country matching the ID.
     *
     * @param countryId the country's ID
     * @return the country with the matching ID
     * @throws SQLException
     */
    public static Country retrieveCountryObject(int countryId) throws SQLException {
        String sql = "SELECT * FROM COUNTRIES WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Country country = new Country(countryId, rs.getString("Country"));
        return country;
    }
}
