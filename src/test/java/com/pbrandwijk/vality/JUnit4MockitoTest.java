package com.pbrandwijk.vality;

import com.pbrandwijk.vality.model.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class JUnit4MockitoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JUnit4MockitoTest.class);
    private static final String LIBRARY = "src/main/javascript/library.js";
    private static final String SCRIPT = "src/main/javascript/JUnit4MockitoTest.js";
    private static final String ENGINE_SHORT_NAME = "JavaScript";
    private static final String LOGGER_VAR_NAME = "logger";
    private static final String LOGGER_PREFIX = "JavaScript";
    private static final String PERSON_VAR_NAME = "person";
    private static final String PERSON_JOE_NAME = "Joe";
    private static final String PERSON_JANE_NAME = "Jane";

    private ScriptEngine engine;

    @Before
    public void setUp() {
        // Set a system property to make sure Nashorn uses ECMAScript 6
        System.setProperty("nashorn.args", "--language=es6");
        // Initialize a script engine manager
        ScriptEngineManager factory = new ScriptEngineManager();
        // Create JavaScript engine for the test
        engine = factory.getEngineByName(ENGINE_SHORT_NAME);
        // Bind the a logger object to the engine
        engine.put(LOGGER_VAR_NAME, LoggerFactory.getLogger(LOGGER_PREFIX));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void runWithSpyObject() throws FileNotFoundException, ScriptException {
        // Set up a spied Person object
        Person joe = spy(new Person(PERSON_JOE_NAME, 32));

        // Bind the Person object to the engine
        engine.put(PERSON_VAR_NAME, joe);

        // First evaluate the library so its functions are available
        engine.eval(new FileReader(LIBRARY));
        // Then evaluate the script
        engine.eval(new FileReader(SCRIPT));

        // Verify that the getAge() method was called twice on the object
        verify(joe, times(2)).getAge();
        // Check that the Java object has been modified correctly by the script
        assertEquals((Integer) 33, joe.getAge());
    }

    @Test
    public void runWithMockObject() throws FileNotFoundException, ScriptException {
        // Set up a mocked Person object
        Person jane = mock(Person.class);
        when(jane.getName()).thenReturn(PERSON_JANE_NAME);
        when(jane.getAge()).thenReturn(40);

        // Bind the Person object to the engine
        engine.put(PERSON_VAR_NAME, jane);

        // First evaluate the library so its functions are available
        engine.eval(new FileReader(LIBRARY));
        // Then evaluate the script
        engine.eval(new FileReader(SCRIPT));

        // Check that the mocked Java object has not been modified by the script
        assertEquals((Integer) 40, jane.getAge());
        // But verify that setAge(33) was actually called on the object
        verify(jane).setAge(33);
    }
}