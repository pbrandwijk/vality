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

public class JavaScriptTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaScriptTest.class);

    ScriptEngine engine;

    @Before
    public void setUp() throws Exception {
        // create a script engine manager
        ScriptEngineManager factory = new ScriptEngineManager();
        // create JavaScript engine
        engine = factory.getEngineByName("JavaScript");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void run() throws FileNotFoundException, ScriptException {
        String fileName = "src/main/javascript/script.js";
        Person person = new Person("Joe", 32);

        engine.put("logger", LoggerFactory.getLogger("JavaScript"));
        engine.put("person", person);

        FileReader reader = new FileReader(fileName);
        engine.eval(reader);

        assertEquals((Integer) 33, person.getAge());
    }
}