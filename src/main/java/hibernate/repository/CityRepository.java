package hibernate.repository;

import hibernate.model.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CityRepository {

    private static SessionFactory sessionFactory;

    static {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        MetadataSources sources = new MetadataSources(registry);
        Metadata metadata = sources.getMetadataBuilder().build();
        sessionFactory = metadata.buildSessionFactory();
    }

/*    static{
        Configuration cfg = new Configuration().configure();
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
        sessionFactory = cfg.buildSessionFactory(serviceRegistry);
    }*/
    public static List<City> getCities (){

        Session session = sessionFactory.openSession();
        //List<City> cities = session.createQuery("FROM city", City.class).setMaxResults(50).getResultList();
        List<City> cities = session.createQuery("FROM city", City.class)
                .setMaxResults(50)
                .getResultList();
        session.close();

        return cities;
    }

    public static List<City> getCitiesByName (String cityName){
        Session session = sessionFactory.openSession();
        //List<City> cities = session.createQuery("FROM city", City.class).setMaxResults(50).getResultList();
        List<City> cities = session.createQuery("FROM city c WHERE c.name = :cityNameParam", City.class)
                .setParameter("cityNameParam", cityName)
                .setMaxResults(50)
                .getResultList();
        session.close();

        return cities;
    }

    public static List<City> getCitiesByCountryCode (String countryCode) {
        Session session = sessionFactory.openSession();
        //List<City> cities = session.createQuery("FROM city", City.class).setMaxResults(50).getResultList();
        List<City> cities = session.createQuery("FROM city c WHERE c.countryCode = :countryCodeParam", City.class)
                .setParameter("countryCodeParam", countryCode)
                .setMaxResults(50)
                .getResultList();
        session.close();

        return cities;
    }

    public static City getCityById (Long cityId){
        Session session = sessionFactory.openSession();
        //List<City> cities = session.createQuery("FROM city", City.class).setMaxResults(50).getResultList();
        /*City city = session.createQuery("FROM City c WHERE c.id = :cityIdParam", City.class)
                .setParameter("cityIdParam", cityId)
                .uniqueResult();*/
        //City city = session.find(City.class, cityId);
        City city = session.byId(City.class).load(cityId);
        session.close();

        return city;
    }

    public static void updateCityName (Long cityId, String newName) {
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        City city = getCityById(cityId);
        if (city != null) {
            city.setName(newName);
            session.update(city);
            transaction.commit();
        }
        session.close();
    }

    public static void deleteCity (Long cityId, String newName) {
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        City city = getCityById(cityId);
        if (city != null) {
            city.setName(newName);
            session.delete(city);
            transaction.commit();
        }
        session.close();
    }

    public static void addCity (City city){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(city);
        transaction.commit();
        session.close();
    }
}
