package com.own.first;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import static java.nio.file.StandardOpenOption.*;

public record DiskPersistence(Path path, byte[] data) {
    public void save() {
        Path dir = path.toAbsolutePath().getParent();
        Path tmp = dir.resolve(path.getFileName() + ".tem." + UUID.randomUUID());
        try {

            try (FileChannel channel = FileChannel.open(tmp, WRITE, CREATE_NEW)) {
                channel.write(ByteBuffer.wrap(data));
                channel.force(true);
            }

            Files.move(tmp, path, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            syncDirectory(path);
        } catch (Exception e) {
            try {
                Files.deleteIfExists(tmp);
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }

    }

    private void syncDirectory(Path dir) throws IOException {
        try (FileChannel dirChannel = FileChannel.open(dir, READ)) {
            dirChannel.force(true);
        }
    }
}
