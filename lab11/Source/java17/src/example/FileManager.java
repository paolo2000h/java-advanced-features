package example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileManager {
    private static final String SNAPSHOT_DIRECTORY = System.getProperty("user.home") + File.separator + "Documents" + File.separator + ".snapshot";
    private HashMap<String, String> currentSnapshot = new HashMap<>();
    private HashMap<String, String> savedSnapshot = new HashMap<>();

    private String generateMD5ForFile(String filename) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(Files.readAllBytes(Paths.get(filename)));
        byte[] digest = md.digest();

        return Base64.getEncoder().encodeToString(digest);
    }

    public void createCurrentSnapshot(String path) {
        currentSnapshot.clear();
        try {
            Set<String> set = listFiles(path);
            for (String s : set) {
                currentSnapshot.put(s, generateMD5ForFile(s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<String> listFiles(String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream.filter(file -> (!Files.isDirectory(file) && !file.getFileName().toString().equals("desktop.ini")))
                    .map(Path::toAbsolutePath).map(Path::toString).collect(Collectors.toSet());
        }
    }

    public void generateSnapshotFolder() {
        Path path = Paths.get(SNAPSHOT_DIRECTORY);
        if (Files.exists(path)) {
            return;
        }
        try {
            Files.createDirectories(path);
            Files.setAttribute(path, "dos:hidden", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSnapshot(String path) throws IOException, NoSuchAlgorithmException {
        savedSnapshot.clear();
        if (!Files.exists(Paths.get(SNAPSHOT_DIRECTORY + File.separator + generateMD5ForDirectory(path)))) {
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(SNAPSHOT_DIRECTORY + File.separator + generateMD5ForDirectory(path));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            HashMap<String, String> hm = (HashMap) objectInputStream.readObject();
            objectInputStream.close();
            savedSnapshot = hm;
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveSnapshot(String path) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(SNAPSHOT_DIRECTORY + File.separator + generateMD5ForDirectory(path));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(currentSnapshot);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, Boolean> compareSnapshots() {
        HashMap<String, Boolean> isChangedHashMap = new HashMap<>();
        for (Map.Entry<String, String> entry : savedSnapshot.entrySet()) {
            String saved = entry.getValue();
            String key = entry.getKey();
            String current = currentSnapshot.get(key);
            String fileName = Paths.get(key).getFileName().toString();

            if (saved.equals(current)) {
                isChangedHashMap.put(fileName, false);
            } else {
                isChangedHashMap.put(fileName, true);
            }
        }

        for (Map.Entry<String, String> entry : currentSnapshot.entrySet()) {
            if(!savedSnapshot.containsKey(entry.getKey())) {
                isChangedHashMap.put(Paths.get(entry.getKey()).getFileName().toString(), true);
            }
        }

        return isChangedHashMap;
    }

    private String generateMD5ForDirectory(String path) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(path.getBytes());
        byte[] digest = md.digest();
        Base64.Encoder encoder = Base64.getUrlEncoder();
        return encoder.encodeToString(digest);
    }
}
