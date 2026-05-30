package com.own.log;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AppendLog {
    public FileChannel openLog(String path) throws IOException {
        Path logPath = Paths.get(path);
        return FileChannel.open(logPath, StandardOpenOption.READ,
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE);
    }

    public void appendLog(FileChannel channel, String line) throws IOException {
        try (channel) {
            String record = line + "\n";
            byte[] bytes = record.getBytes(StandardCharsets.UTF_8);
            channel.write(ByteBuffer.wrap(bytes));
            channel.force(true);
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
