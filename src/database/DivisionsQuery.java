package database;

import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class handles all queries to the First_Level_Division table in the database.
 *
 * @author Tabish Abbasi
 */
public class DivisionsQuery {

    /**
     * Retrieves all divisions from the database matching the country ID and stores them in a list.
     *
     * @param countryId the matching country's ID
     * @param divisionList the list to store the division
     * @throws SQLException
     */
    public static void retrieveDivisionsByCountryId(int countryId, ObservableList<Division> divisionList) throws SQLException {
        divisionList.clear();
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            divisionList.add(new Division(divisionId, divisionName));
        }
    }

    /**
     * Retrieves a single division from the database with the matching ID.
     *
     * @param divisionId the division's ID
     * @return the matching division
     * @throws SQLException
     */
    public static Division retrieveDivisionById(int divisionId) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Division division = new Division(divisionId, rs.getString("Division"));
        return division;
    }

    /**
     * Retrieves a divisions country ID that matches the division's ID
     *
     * @param divisionId the division's ID
     * @return the matching division's country ID
     * @throws SQLException
     */
    public static int retrieveDivisionCountryId(int divisionId) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int countryId = rs.getInt("Country_ID");
        return countryId;
    }
}
