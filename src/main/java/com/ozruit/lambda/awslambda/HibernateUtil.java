package com.ozruit.lambda.awslambda;


import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (null != sessionFactory)
            return sessionFactory;
        
        Configuration configuration = new Configuration();

        String jdbcUrl = "jdbc:mysql://yyy.4554.us-west-2.rds.amazonaws.com:3306/ozruitdb";
        
        configuration.setProperty("hibernate.connection.url", jdbcUrl);
        configuration.setProperty("hibernate.connection.username", "aaa");
        configuration.setProperty("hibernate.connection.password", "xxxxx");

        configuration.configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        try {
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
        return sessionFactory;
    }
}


