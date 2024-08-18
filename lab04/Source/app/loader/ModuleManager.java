package loader;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ModuleManager {
    private final static String CUSTOM_MODULE_NAME = "Processing";
    private final static String SUM = "processors.Sum";
    private final static String DIVISION = "processors.Division";
    private final static String MULTIPLICATION = "processors.Multiplication";
    private final static String SUBTRACTION = "processors.Subtraction";
    private static ModuleLayer customLayer;
    private List<Class<?>> classes;

    public ModuleManager(String moduleLocation) {
        Set<String> rootModules = Set.of("Processing");
        customLayer = createLayer(moduleLocation, rootModules);
        classes = loadClasses(customLayer);
    }

    private static ModuleLayer createLayer(String modulePath, Set<String> rootModules) {
        Path path = Paths.get(modulePath);
        ModuleFinder beforeFinder = ModuleFinder.of(path);
        ModuleFinder afterFinder = ModuleFinder.of();
        Configuration parentConfig = ModuleLayer.boot().configuration();
        Configuration config = parentConfig.resolve(beforeFinder, afterFinder, rootModules);
        CustomClassLoader customClassLoader = new CustomClassLoader(path);
        ModuleLayer parentLayer = ModuleLayer.boot();
        ModuleLayer customLayer = parentLayer.defineModulesWithOneLoader(config, customClassLoader);

        if (customLayer.modules().isEmpty()) {
            System.out.println("\nCould not find the module " + rootModules + " at " + modulePath + ". ");
        }
        return customLayer;
    }

    private static List<Class<?>> loadClasses(ModuleLayer layer) {
        ArrayList<Class<?>> classes = new ArrayList<>();
        classes.add(loadClass(SUM, layer));
        classes.add(loadClass(MULTIPLICATION, layer));
        classes.add(loadClass(DIVISION, layer));
        classes.add(loadClass(SUBTRACTION, layer));
        return classes;
    }

    private static Class<?> loadClass(String name, ModuleLayer layer) {
        try {
            return layer.findLoader(CUSTOM_MODULE_NAME).loadClass(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Class<?>> getClasses() {
        return classes;
    }

    public void unloadModule() {
        customLayer = null;
        classes = null;
        System.gc();
    }
}
