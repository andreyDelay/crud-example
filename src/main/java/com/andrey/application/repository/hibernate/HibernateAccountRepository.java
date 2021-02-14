package com.andrey.application.repository.hibernate;

import com.andrey.application.model.Account;
import com.andrey.application.repository.AccountRepository;
import com.andrey.application.repository.connection.ConnectionUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public class HibernateAccountRepository implements AccountRepository {

    private SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();

    @Override
    public Account save(Account objectToSave) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(objectToSave);
        session.getTransaction().commit();
        session.close();
        return objectToSave;
    }

    @Override
    public Account update(Account account) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Account accountFromDB = session.find(Account.class, account.getId());
        if (accountFromDB != null) {
            if (account.getName() != null) {
                accountFromDB.setName(account.getName());
            } else {
                accountFromDB.setStatus(account.getStatus());
            }
        }
        session.getTransaction().commit();
        return accountFromDB;
    }

    @Override
    public void delete(Integer id) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Account account = session.load(Account.class, id);
            session.delete(account);
            session.getTransaction().commit();
            session.close();
        } catch (EntityNotFoundException e) {
            System.out.println("Account with such id doesn't exist!");
        }
    }

    @Override
    public Optional<Account> find(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Account account = session.find(Account.class, id);
        session.close();
        return Optional.of(account);
    }

    @Override
    public List<Account> findAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List accounts = session.createQuery("FROM Account").list();
        session.getTransaction().commit();
        return accounts;
    }
}
