package jdbc.repository;

import jdbc.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityRepository {

    private final static String url = "jdbc:mysql://localhost:3306/world?serverTimezone=UTC";
    private final static String username = "root";
    private final static String password = "Password.123";

    public static List<City> getCities () throws SQLException {
        List<City> cities = new ArrayList<City>();

        //Step 1. Connect to DB server
        Connection conn = DriverManager.getConnection(url, username, password);

        //Step2. Create a statement from that connection
        Statement stmt = conn.createStatement();

        //Step3. Execute this statement
        ResultSet rs = stmt.executeQuery("SELECT * FROM city LIMIT 50");

        //Step4. View results (loop through result set)
        while (rs.next()) {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String countryCode = rs.getString("countryCode");
            String district = rs.getString("district");
            long population = rs.getLong("population");
            City city = new City(id, name, countryCode, district, population);

            cities.add(city);
        }
        rs.close();
        stmt.close();
        conn.close();

        return cities;
    }

    public static List<City> getCitiesByName (String cityName) throws SQLException {
        List<City> cities = new ArrayList<City>();

        //Step 1. Connect to DB server
        Connection conn = DriverManager.getConnection(url, username, password);

        //Step2. Create a statement from that connection
        //Bad practice:
        Statement stmt = conn.createStatement();

        //Good practice:
        PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM city WHERE name = ?");
        stmt2.setString(1, cityName); //index is number of "?" above

        //Step3. Execute this statement
        //ATTENTION! This is very bad practice (SQL injection), never do it!!
        ResultSet rs = stmt.executeQuery("SELECT * FROM city WHERE name = '" + cityName + "'");

        //Step4. View results (loop through result set)
        cities = parseResultsSetToCities(rs);

        rs.close();
        stmt.close();
        conn.close();

        return cities;
    }

    private static List<City> parseResultsSetToCities (ResultSet rs) throws SQLException {
        List<City> cities = new ArrayList<City>();

        while (rs.next()) {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String countryCode = rs.getString("countryCode");
            String district = rs.getString("district");
            long population = rs.getLong("population");
            City city = new City(id, name, countryCode, district, population);

            cities.add(city);
        }
        return cities;
    }

    public static List<City> getCitiesByCountryCode(String countryCode) throws SQLException {
        List<City> cities = new ArrayList<City>();

        //Step 1. Connect to DB server
        Connection conn = DriverManager.getConnection(url, username, password);

        //Step2. Preparing statement. Good practice:
        PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM city WHERE countrycode = ?");
        stmt2.setString(1, countryCode); //index is number of "?" above

        //Step3. Execute this statement
        ResultSet rs = stmt2.executeQuery();

        //Step4. View results (loop through result set)
        cities = parseResultsSetToCities(rs);

        rs.close();
        stmt2.close();
        conn.close();

        return cities;
    }

    public static void addCity (City city) throws SQLException {
        //Step 1. Connect to DB server
        Connection conn = DriverManager.getConnection(url, username, password);

        //Step2. Preparing statement. Good practice:
        PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO city (name, countryCode, district, population) " +
                "VALUES (?, ?, ?, ?)");
        stmt2.setString(1, city.getName());
        stmt2.setString(2, city.getCountryCode());
        stmt2.setString(3, city.getDistrict());
        stmt2.setLong(4, city.getPopulation());

        //Step3. Executing statement
        int rowsAffected = stmt2.executeUpdate();
        System.out.println("Number of rows affected: " + rowsAffected);

        stmt2.close();
        conn.close();
    }
}
