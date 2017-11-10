package org.thecuriousdev.demo.skeleton.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.thecuriousdev.demo.skeleton.db.PersonRepository;
import org.thecuriousdev.demo.skeleton.db.SimulatedDatabase;
import org.thecuriousdev.demo.skeleton.db.domain.Person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SimulatedDatabase.class)
public class PersonControllerStaticMockTest {

    private PersonController personController;
    private PersonRepository personRepository;

    @Before
    public void setup() {
        personRepository = spy(new PersonRepository());
        personController = new PersonController(personRepository);
        mockStatic(SimulatedDatabase.class);
    }

    @Test
    public void retrievePersonWithStaticMockTest() {
        Person person = new Person("jimmy", 24, "pizza");
        when(SimulatedDatabase.getPerson(any())).thenReturn(person);

        ResponseEntity<Person> response = personController.getPerson("nastya");

        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(person, response.getBody());
    }

    @Test
    public void retrievePersonWithStaticMockAndVerificationTest() {
        Person person = new Person("joakim", 48, "steak");

        ResponseEntity<HttpStatus> response = personController.savePerson(person);

        ArgumentCaptor<Person> personCaptor = forClass(Person.class);
        verifyStatic();
        SimulatedDatabase.savePerson(personCaptor.capture());

        Person capturedPerson = personCaptor.getValue();

        assertNotNull(capturedPerson);
        assertEquals(person, capturedPerson);
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
    }
}
