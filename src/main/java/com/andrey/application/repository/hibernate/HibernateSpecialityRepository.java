package com.andrey.application.repository.hibernate;

import com.andrey.application.model.Speciality;
import com.andrey.application.repository.SpecialityRepository;
import com.andrey.application.repository.connection.ConnectionUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public class HibernateSpecialityRepository implements SpecialityRepository {

    private SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();

    @Override
    public Speciality save(Speciality objectToSave) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(objectToSave);
        session.getTransaction().commit();
        session.close();
        return objectToSave;
    }

    @Override
    public Speciality update(Speciality speciality) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Speciality specialityFromDB = session.find(Speciality.class, speciality.getId());
        if (specialityFromDB != null) {
            specialityFromDB.setName(speciality.getName());
        }
        session.getTransaction().commit();
        session.close();
        return specialityFromDB;
    }

    @Override
    public void delete(Integer id) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Speciality speciality = session.load(Speciality.class, id);
            session.delete(speciality);
            session.getTransaction().commit();
            session.close();
        } catch (EntityNotFoundException e) {
            System.out.println("Speciality with such is doesn't exist!");
        }
    }

    @Override
    public Optional<Speciality> find(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Speciality speciality = session.find(Speciality.class, id);
        session.getTransaction().commit();
        session.close();
        return Optional.of(speciality);
    }

    @Override
    public List<Speciality> findAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List specialities = session.createQuery("FROM Speciality ").list();
        session.getTransaction().commit();
        session.close();
        return specialities;
    }
}
