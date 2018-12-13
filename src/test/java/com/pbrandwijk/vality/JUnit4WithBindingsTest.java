package com.pbrandwijk.vality;

import com.pbrandwijk.vality.model.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.*;

public class JUnit4WithBindingsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JUnit4WithBindingsTest.class);

    ScriptEngine engine;

    @Before
    public void setUp() throws Exception {
        // Create a script engine manager
        ScriptEngineManager factory = new ScriptEngineManager();
        // Create JavaScript engine
        engine = factory.getEngineByName("JavaScript");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void run() throws FileNotFoundException, ScriptException {
        Person person = new Person("Joe", 32);

        // Bind the Java objects to the engine
        engine.put("logger", LoggerFactory.getLogger("JavaScript"));
        engine.put("person", person);

        // First evaluate the library
        String libraryJs = "src/main/javascript/library.js";
        FileReader libraryJsReader = new FileReader(libraryJs);
        engine.eval(libraryJsReader);

        // Then evaluate the script
        String scriptJs = "src/main/javascript/script.js";
        FileReader scriptJsReader = new FileReader(scriptJs);
        engine.eval(scriptJsReader);

        // Check that the Java object has been modified correctly by the script
        assertEquals((Integer) 33, person.getAge());
    }
}