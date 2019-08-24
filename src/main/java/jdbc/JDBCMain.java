package jdbc;

import jdbc.model.City;
import jdbc.model.Country;
import jdbc.repository.CityRepository;
import jdbc.repository.CountryRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class JDBCMain {
    public static void main(String[] args) throws SQLException {
        System.out.println("Enter one of the following options: \n1. View all cities \n2. View cities by name " +
                "\n3. Add a new city \n4. Delete City by name \n5. Update city \n6. View all countries " +
                "\n7. View countries by name \n8. View country and its cities \n9. Add a new country");

        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();

        switch (option){
            case 1:
                List<City> cities = CityRepository.getCities();
                printCities(cities);
                break;
            case 2:
                scanner.nextLine();
                System.out.println("Please enter city name:");
                String cityName = scanner.nextLine();
                cities = CityRepository.getCitiesByName(cityName);
                printCities(cities);
                break;
            case 3:
                scanner.nextLine();
                System.out.println("Please enter city name:");
                cityName = scanner.nextLine();
                System.out.println("Enter country code:");
                String countryCode = scanner.nextLine();
                System.out.println("Enter district:");
                String district = scanner.nextLine();
                System.out.println("Enter city population:");
                long population = scanner.nextLong();

                City city = new City(cityName, countryCode, district, population);
                CityRepository.addCity(city);
                System.out.println("City " + cityName + " has been added.");
                break;
            case 4:
                scanner.nextLine();
                System.out.println("Please enter city name to delete:");
                cityName = scanner.nextLine();
                //homework
                //CityRepository.deleteCity();
                break;
            case 5:
                //homework
            case 6:
                List<Country> countries = CountryRepository.getCountries();
                printCountries(countries);
                break;
            case 7:
                scanner.nextLine();
                System.out.println("Please enter country name:");
                String countryName = scanner.nextLine();
                countries = CountryRepository.getCountriesByName(countryName);
                printCountries(countries);
                break;
            case 8:
                scanner.nextLine();
                System.out.println("Please enter country code:");
                countryCode = scanner.nextLine();
                countries = CountryRepository.getCountriesByCountryCode(countryCode);
                printCountries(countries);
                cities = CityRepository.getCitiesByCountryCode(countryCode);
                printCities(cities);
                break;
            case 9:
                //homework
            default:
                System.out.println("Invalid option");
        }
    }

    public static void printCities(List<City> cities) {
        printCityHeader();
        for(City city : cities) {
            printCity(city);
        }
    }

    public static void printCitiesOfSingleCountry(List<City> cities) {
        printCityHeader();
        for(City city : cities) {
            printCities(cities);
        }
    }

    public static void printCityHeader() {
        System.out.printf("%-10s %-20s %-20s %-20s %-20s \n","ID","Name", "Country Code", "District", "Population");
    }

    public static void printCity(City city) {
        System.out.printf("%-10s ",city.getId());
        System.out.printf("%-20s ",city.getName());
        System.out.printf("%-20s ",city.getCountryCode());
        System.out.printf("%-20s ",city.getDistrict());
        System.out.println(city.getPopulation());
    }

    public static void printCountries(List<Country> countries) {
        countries.forEach(JDBCMain::printCountry);
    }
    public static void printCountry(Country country) {
        System.out.printf("%-6s | ",country.getCode());
        System.out.printf("%-50s | ",country.getName());
        System.out.printf("%-20s | ",country.getContinent());
        System.out.printf("%-26s | ",country.getRegion());
        System.out.printf("%-6s | ",country.getIndepYear());
        System.out.printf("%-45s | ",country.getGovernmentForm());
        System.out.printf("%-4s ",country.getLifeExpectancy());
        System.out.println(country.getPopulation());
    }
}
