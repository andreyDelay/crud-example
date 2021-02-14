package com.andrey.application.model;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @SequenceGenerator(name = "acc_seq", sequenceName = "my_seq_gen", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_seq")
    private int id;

    @Column(name = "name")
    private String name;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Account() {
    }

    public Account(int id, String name, AccountStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
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

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "id = " + id + ", name = " + name + ", status = " + status;
    }
}
