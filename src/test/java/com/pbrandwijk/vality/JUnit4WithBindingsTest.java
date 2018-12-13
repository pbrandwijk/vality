package com.pbrandwijk.vality;

import com.pbrandwijk.vality.model.Person;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import javax.script.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.*;

public class JUnit4WithBindingsTest {

    private static final String NASHORN_PROPERTY_NAME = "nashorn.args";
    private static final String NASHORN_PROPERTY_VALUE = "--language=es6";
    private static final String LIBRARY = "src/main/javascript/library.js";
    private static final String SCRIPT = "src/main/javascript/JUnit4WithBindingsTest.js";
    private static final String ENGINE_SHORT_NAME = "JavaScript";
    private static final String LOGGER_VAR_NAME = "logger";
    private static final String LOGGER_PREFIX = "JavaScript";
    private static final String PERSON_VAR_NAME = "person";
    private static final String PERSON_JOE_NAME = "Joe";
    private static final Integer PERSON_JOE_AGE = 32;
    private static final Integer PERSON_JOE_RESULT_AGE = 33;

    @Test
    public void run() throws FileNotFoundException, ScriptException {

        // Set a system property to make sure Nashorn uses ECMAScript 6
        System.setProperty(NASHORN_PROPERTY_NAME, NASHORN_PROPERTY_VALUE);
        // Initialize a script engine manager
        ScriptEngineManager factory = new ScriptEngineManager();
        // Create JavaScript engine for the test
        ScriptEngine engine = factory.getEngineByName(ENGINE_SHORT_NAME);

        // Create a test Person object
        Person person = new Person(PERSON_JOE_NAME, PERSON_JOE_AGE);

        // Bind the a logger and the person object to the engine
        engine.put(LOGGER_VAR_NAME, LoggerFactory.getLogger(LOGGER_PREFIX));
        engine.put(PERSON_VAR_NAME, person);

        // First evaluate the library so its functions are available
        engine.eval(new FileReader(LIBRARY));
        // Then evaluate the script
        engine.eval(new FileReader(SCRIPT));

        // Check that the Java object has been modified correctly by the script
        assertEquals(PERSON_JOE_RESULT_AGE, person.getAge());
    }
}