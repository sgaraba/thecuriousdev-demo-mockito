package org.thecuriousdev.demo.skeleton.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.thecuriousdev.demo.skeleton.db.PersonRepository;
import org.thecuriousdev.demo.skeleton.db.domain.Person;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PersonControllerMockTest {

    @Mock
    private PersonRepository personRepository;
    private PersonController personController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        personController = new PersonController(personRepository);
    }

    @Test
    public void retrievePersonWithMockTest() {
        Person person = new Person("viktor", 24, "tacos");
        when(personRepository.findById(any())).thenReturn(Optional.of(person));

        ResponseEntity<Person> response = personController.getPerson("viktor");

        ArgumentCaptor<String> personNameCaptor = forClass(String.class);
        verify(personRepository, times(1)).findById(personNameCaptor.capture());
        String name = personNameCaptor.getValue();

        assertNotNull(name);
        assertEquals("viktor", name);
        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(person, response.getBody());
    }

}
