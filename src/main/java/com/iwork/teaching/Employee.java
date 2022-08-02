package com.iwork.teaching;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;

public class Employee {
    // Employee class
    // fields and methods are self-explanatory
    private int id;
    private String first_name, last_name;
    private String email;
    private Gender gender;

    public Employee(String first_name, String last_name, String email, Gender gender) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.gender = gender;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public static Employee generateFake() {
        Fairy fairy = Fairy.create();
        Person person = fairy.person();

        return new Employee(
                person.getFirstName(),
                person.getLastName(),
                person.getEmail(),
                person.isMale() ? Gender.Male : Gender.Female
        );
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                '}';
    }
}
