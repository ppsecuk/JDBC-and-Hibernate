package hibernate.repository;

import hibernate.model.Country;

import hibernate.model.CountryCode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CountryRepository {
    private static SessionFactory sessionFactory;
    static {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        MetadataSources sources = new MetadataSources(registry);
        Metadata metadata = sources.getMetadataBuilder().build();
        sessionFactory = metadata.buildSessionFactory();
    }
    public static List<Country> getCountries() {
        Session session = sessionFactory.openSession();
//        List<Country> countries = session.createQuery("FROM country", Country.class)
//                .setMaxResults(50)
//                .getResultList();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Country> criteriaQuery = criteriaBuilder.createQuery(Country.class);
        criteriaQuery.from(Country.class);

        List<Country> countries = session.createQuery(criteriaQuery)
                .setMaxResults(50)
                .getResultList();

        session.close();
        return countries;
    }
    public static List<Country> getCountriesByName(String countryName) {
        Session session = sessionFactory.openSession();
//        List<Country> countries = session.createQuery("FROM country c Where c.name = :countryNameParam", Country.class)
//                .setParameter("countryNameParam", countryName)
//                .setMaxResults(50)
//                .getResultList();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Country> criteriaQuery =  criteriaBuilder.createQuery(Country.class);
        Root<Country> root = criteriaQuery.from(Country.class);

        Predicate predicate = criteriaBuilder.equal(root.get("name"), countryName);

        criteriaQuery.where(predicate);

        List<Country> countries = session.createQuery(criteriaQuery)
                .setMaxResults(50)
                .getResultList();

        session.close();
        return countries;
    }
    public static Country getCountryByCode(String countryCode) {
        Session session = sessionFactory.openSession();
        Country country = session.createQuery("FROM Country c Where c.code = :countryCodeParam", Country.class)
                .setParameter("countryCodeParam", countryCode)
                .uniqueResult();
        session.close();
        return country;
    }
    public static Country getCountryById(String code) {
        Session session = sessionFactory.openSession();
        //      Country country =  session.createQuery("FROM Country c Where c.id = :codeParam", Country.class)
        //              .setParameter("codeParam", code).uniqueResult();
        //      Country country = session.find(Country.class, code);
        Country country = session.byId(Country.class).load(CountryCode.valueOf(code));
        session.close();
        return country;
    }
    public static void addCountry(Country country) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(country);
        transaction.commit();
        session.close();
    }
}
