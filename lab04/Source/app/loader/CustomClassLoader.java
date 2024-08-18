package loader;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomClassLoader extends ClassLoader {
    private final Path searchPath;

    public CustomClassLoader(Path path) {
        if (!Files.isDirectory(path)) throw new IllegalArgumentException("Path must be a directory");
        searchPath = path;
    }

    @Override
    public Class<?> findClass(String binName) throws ClassNotFoundException {
        Path classfile = Paths.get(searchPath + FileSystems.getDefault().getSeparator() + binName.replace(".", FileSystems.getDefault().getSeparator()) + ".class");
        byte[] buf;
        try {
            buf = Files.readAllBytes(classfile);
        } catch (IOException e) {
            throw new ClassNotFoundException("Error in defining " + binName + " in " + searchPath, e);
        }
        return defineClass(binName, buf, 0, buf.length);
    }
}

