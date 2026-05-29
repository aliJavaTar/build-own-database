package com.own.first;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.*;

public record BadFilePersistence(Path path, byte[] data) {

    public void saveData() {
        try {
            Files.write(path, data, CREATE, WRITE, TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
