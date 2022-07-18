package database;

import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesQuery {
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

    public static Country retrieveCountryObject(int countryId) throws SQLException {
        String sql = "SELECT FROM COUNTRIES WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        Country country = new Country(countryId, rs.getString("Country"));
        return country;
    }
}
