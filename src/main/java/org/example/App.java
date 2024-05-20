package org.example;

import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class).addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();


        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Person person = session.get(Person.class, 2);
            System.out.println("Получили человека из таблицы");

            session.getTransaction().commit();

            System.out.println("Сессия закончилась(session close)");

            System.out.println("открыли сесиию и транзакцию еще раз ");
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            person = (Person) session.merge(person);
            Hibernate.initialize(person.getItems());


            session.getTransaction().commit();
            System.out.println("Вне второй сессии");

            // это работает, т к связные товары были подгружены неленивой загрузкой
            System.out.println(person.getItems());


        } finally {
            sessionFactory.close();

        }

    }

}
