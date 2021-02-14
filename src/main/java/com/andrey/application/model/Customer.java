package com.andrey.application.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @SequenceGenerator(name = "acc_seq", sequenceName = "my_seq_gen", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_seq")
    @Column(name = "customer_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "age")
    private Integer age;

    @ManyToMany(mappedBy = "customers",cascade = {CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Speciality> specialities = new HashSet<>();

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private Account account;

    public Customer() {
    }

    public Customer(Integer id, String name, String surname, Integer age, Account account) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<Speciality> specialities) {
        this.specialities = specialities;
    }

    public void addSpeciality(Speciality speciality) {
        specialities.add(speciality);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
        account.setCustomer(this);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "id = " + id + ", Name = " + name + ", Surname = " + surname + ", Age = " + age +
                ", Account = " + account.getName() + ", status: " + account.getStatus().name();
    }
}
