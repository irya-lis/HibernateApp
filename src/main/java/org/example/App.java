package org.example;

import org.example.model.Actor;
import org.example.model.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Movie.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        try (sessionFactory) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

           Movie movie = new Movie("My Bloody Valentine", 1998);

           Actor actor = session.get(Actor.class, 1);

           movie.setActors(new ArrayList<>(Collections.singletonList(actor)));
           actor.getMovies().add(movie);

           session.save(movie);


            session.getTransaction().commit();
        }

    }

}
