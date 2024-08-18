package pl.edu.pwr.tkubik.javata;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Engine {
    private Invocable invocable;

    public void loadScript(String script) throws FileNotFoundException, ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
        engine.eval(new FileReader(script));
        invocable = (Invocable) engine;
        System.out.println(invocable);
    }

    public ArrayList<ArrayList<Boolean>> iteration(ArrayList<ArrayList<Boolean>> currentState, int one, int two) throws ScriptException, NoSuchMethodException {
        return (ArrayList<ArrayList<Boolean>>) invocable.invokeFunction("nextIteration", currentState, one, two);
    }
}
