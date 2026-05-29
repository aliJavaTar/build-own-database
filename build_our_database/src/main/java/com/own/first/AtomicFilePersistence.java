package com.own.first;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.UUID;


public record AtomicFilePersistence(Path path, byte[] data) {
    public void saveData() throws IOException {
        Path dir = path.toAbsolutePath().getParent();

        Path tempFile = dir.resolve(
                dir.getFileName() + ".tmp." + UUID.randomUUID()
        );

        try {
            Files.write(tempFile, data,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.CREATE_NEW,
                    StandardOpenOption.TRUNCATE_EXISTING);

            Files.move(tempFile, path,
                    StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);

        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}
