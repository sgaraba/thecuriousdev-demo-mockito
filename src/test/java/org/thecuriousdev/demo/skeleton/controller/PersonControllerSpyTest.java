package org.thecuriousdev.demo.skeleton.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.thecuriousdev.demo.skeleton.db.PersonRepository;
import org.thecuriousdev.demo.skeleton.db.domain.Person;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class PersonControllerSpyTest {

    private PersonController personController;
    private PersonRepository personRepository;

    @Before
    public void setup() {
        personRepository = spy(new PersonRepository());
        personController = new PersonController(personRepository);
    }

    @Test
    public void retrievePersonWithSpyTest() {
        Person person = new Person("nastya", 24, "sushi");
        doReturn(Optional.of(person)).when(personRepository).findById(any());

        ResponseEntity<Person> response = personController.getPerson("nastya");

        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(person, response.getBody());
    }

}
