package com.pbrandwijk.vality;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JUnit4SimpleScriptTest {

    private static final String NASHORN_PROPERTY_NAME = "nashorn.args";
    private static final String NASHORN_PROPERTY_VALUE = "--language=es6";
    private static final String SCRIPT = "src/main/javascript/JUnit4SimpleScriptTest.js";
    private static final String ENGINE_SHORT_NAME = "JavaScript";

    @Test
    public void runSimpleScript() throws FileNotFoundException, ScriptException {

        // Set a system property to make sure Nashorn uses ECMAScript 6
        System.setProperty(NASHORN_PROPERTY_NAME, NASHORN_PROPERTY_VALUE);
        // Initialize a script engine manager
        ScriptEngineManager factory = new ScriptEngineManager();
        // Create JavaScript engine for the test
        ScriptEngine engine = factory.getEngineByName(ENGINE_SHORT_NAME);

        // Evaluate the script
        engine.eval(new FileReader(SCRIPT));

    }

}