package loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileReader {
    public static String[] readCSVHeaders(String pathToFile) throws NoSuchFileException {
        Path path = Paths.get(pathToFile);
        try (Stream<String> lines = Files.lines(path)) {
            return lines.findFirst().get().split(",");
        } catch (IOException e) {
            throw new NoSuchFileException("No file found in: " + pathToFile);
        }
    }

    public static String[][] readCSVValues(String pathToFile) throws NoSuchFileException {
        Path path = Paths.get(pathToFile);
        try (Stream<String> lines = Files.lines(path)) {
            return lines.skip(1).map(line -> line.split(",")).toArray(String[][]::new);
        } catch (IOException e) {
            throw new NoSuchFileException("No file found in: " + pathToFile);
        }
    }
}
