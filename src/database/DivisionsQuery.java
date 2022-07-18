package database;

import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionsQuery {
    public static void retrieveDivisionsByCountryId(int countryId, ObservableList<Division> divisionList) throws SQLException {
        divisionList.clear();
        String sql = "SELECT FROM FIRST-LEVEL DIVISIONS WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            divisionList.add(new Division(divisionId, divisionName));
        }
    }

    public static Division retrieveDivisionById(int divisionId) throws SQLException {
        String sql = "SELECT FROM FIRST-LEVEL DIVISIONS WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        Division division = new Division(divisionId, rs.getString("Division"));
        return division;
    }

    public static int retrieveDivisionCountryId(int divisionId) throws SQLException {
        String sql = "SELECT FROM FIRST-LEVEL DIVISIONS WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        int countryId = rs.getInt("Country_ID");
        return countryId;
    }
}
