package com.andrey.application.repository.hibernate;

import com.andrey.application.model.Customer;
import com.andrey.application.repository.CustomerRepository;
import com.andrey.application.repository.connection.ConnectionUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class HibernateCustomerRepository implements CustomerRepository {

    private SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();

    @Override
    public Customer save(Customer objectToSave) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(objectToSave);
        session.getTransaction().commit();
        session.close();
        return objectToSave;
    }

    @Override
    public Customer update(Customer customer) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Customer updated = session.find(Customer.class, customer.getId());
        if (updated != null) {
            if (customer.getSpecialities().size() == 0) {
                updated.setName(customer.getName());
                updated.setSurname(customer.getSurname());
                updated.setAge(customer.getAge());
            } else {
                updated.getSpecialities().addAll(customer.getSpecialities());
            }
            session.persist(updated);
        }
        session.getTransaction().commit();
        session.close();
        return updated;
    }

    @Override
    public void delete(Integer id) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Customer deleted = session.load(Customer.class, id);
            session.delete(deleted);
            session.getTransaction().commit();
            session.close();
        } catch (EntityNotFoundException e) {
            System.out.println("Customer with such id doesn't exist!");
        }
    }

    @Override
    public Optional<Customer> find(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Customer customer = session.createQuery(
                "FROM Customer c LEFT JOIN FETCH c.specialities WHERE c.id =" + id
                    ,Customer.class)
                    .stream()
                    .findFirst()
                    .orElse(null);

        session.getTransaction().commit();
        session.close();
        return Optional.of(customer);
    }

    @Override
    public List<Customer> findAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List customers = session.createQuery("FROM Customer c LEFT JOIN FETCH c.specialities").list();
        session.getTransaction().commit();
        session.close();
        return customers;
    }
}
