package com.golem.lab.repository;

import com.golem.lab.classes.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<Person, Integer> {
//    void createPerson(Person person);
}
