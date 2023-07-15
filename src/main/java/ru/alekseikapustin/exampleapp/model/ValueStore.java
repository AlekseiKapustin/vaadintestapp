package ru.alekseikapustin.exampleapp.model;

import jakarta.persistence.*;

@Entity
public class ValueStore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "datavalue")
    private int value;

    protected ValueStore() {}

    public ValueStore(int value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ValueStore{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
