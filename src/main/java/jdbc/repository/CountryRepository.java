package jdbc.repository;

import jdbc.model.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryRepository {

    private final static String url = "jdbc:mysql://localhost:3306/world?serverTimezone=UTC";
    private final static String username = "root";
    private final static String password = "Password.123";

    public static List<Country> getCountries () throws SQLException {
        List<Country> countries = new ArrayList<Country>();

        //Step 1. Connect to DB server
        Connection conn = DriverManager.getConnection(url, username, password);

        //Step2. Create a statement from that connection
        Statement stmt = conn.createStatement();

        //Step3. Execute this statement
        ResultSet rs = stmt.executeQuery("SELECT * FROM country LIMIT 50");

        //Step4. View results (loop through result set)
        while (rs.next()) {
            String code = rs.getString("code");
            String name = rs.getString("name");
            String continent = rs.getString("continent");
            String region = rs.getString("region");
            double surfaceArea = rs.getDouble("surfacearea");
            int indepYear = rs.getInt("indepyear");
            long population = rs.getLong("population");
            double lifeExpectancy = rs.getDouble("lifeexpectancy");
            double gnp = rs.getDouble("gnp");
            double gnpOld = rs.getDouble("gnpold");
            String localName = rs.getString("localname");
            String governmentForm = rs.getString("governmentform");
            String headOfState = rs.getString("headofstate");
            int capital = rs.getInt("capital");
            String code2 = rs.getString("code2");

            Country country = new Country(code, name, continent, region, surfaceArea, indepYear, population, lifeExpectancy,
                    gnp, gnpOld, localName, governmentForm, headOfState, capital, code2);

            countries.add(country);
        }
        rs.close();
        stmt.close();
        conn.close();

        return countries;
    }

    public static List<Country> getCountriesByName (String countryName) throws SQLException {
        List<Country> countries = new ArrayList<Country>();

        //Step 1. Connect to DB server
        Connection conn = DriverManager.getConnection(url, username, password);

        //Step2. Preparing statement. Good practice:
        PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM country WHERE name = ?");
        stmt2.setString(1, countryName); //index is number of "?" above

        //Step3. Execute this statement
        ResultSet rs = stmt2.executeQuery();

        //Step4. View results (loop through result set)
        countries = parseResultsSetToCountries(rs);

        rs.close();
        stmt2.close();
        conn.close();

        return countries;
    }

    public static List<Country> getCountriesByCountryCode (String countryCode) throws SQLException {
        List<Country> countries = new ArrayList<Country>();

        //Step 1. Connect to DB server
        Connection conn = DriverManager.getConnection(url, username, password);

        //Step2. Preparing statement. Good practice:
        PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM country WHERE code = ?");
        stmt2.setString(1, countryCode); //index is number of "?" above

        //Step3. Execute this statement
        ResultSet rs = stmt2.executeQuery();

        //Step4. View results (loop through result set)
        countries = parseResultsSetToCountries(rs);

        rs.close();
        stmt2.close();
        conn.close();

        return countries;
    }

    private static List<Country> parseResultsSetToCountries (ResultSet rs) throws SQLException {
        List<Country> countries = new ArrayList<Country>();

        while (rs.next()) {
            String code = rs.getString("code");
            String name = rs.getString("name");
            String continent = rs.getString("continent");
            String region = rs.getString("region");
            double surfaceArea = rs.getDouble("surfacearea");
            int indepYear = rs.getInt("indepyear");
            long population = rs.getLong("population");
            double lifeExpectancy = rs.getDouble("lifeexpectancy");
            double gnp = rs.getDouble("gnp");
            double gnpOld = rs.getDouble("gnpold");
            String localName = rs.getString("localname");
            String governmentForm = rs.getString("governmentform");
            String headOfState = rs.getString("headofstate");
            int capital = rs.getInt("capital");
            String code2 = rs.getString("code2");

            Country country = new Country(code, name, continent, region, surfaceArea, indepYear, population, lifeExpectancy,
                    gnp, gnpOld, localName, governmentForm, headOfState, capital, code2);

            countries.add(country);
        }
        return countries;
    }
}
