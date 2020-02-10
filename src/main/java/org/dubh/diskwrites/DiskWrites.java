package org.dubh.diskwrites;

import org.dubh.engage.ConfigurationEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.*;
import java.nio.channels.*;
import java.nio.file.StandardOpenOption;
import com.google.common.collect.ImmutableSet;

public class DiskWrites {
  private static final Random random = new Random();

  private final int concurrency;
  private final int files;
  private final int size;

  // Fake amount of time it takes to generate each item. This should cause a fixed
  // overhead of about 5 minutes (5 * 60000) in a sequential
//  private static final int TIME_TO_GENERATE = 5;
  
  public static void main(String[] args) throws Exception {
    ConfigurationEngine ce =
        new ConfigurationEngine()
            .withCommandlineArgs(args)
            .withGeneratedProperties(Flags.get())
            .initialize();
    if (!ce.checkUsage()) {
      System.exit(1);
    }
    int concurrency = Flags.get().getConcurrency();
    int files = Flags.get().getFiles();
    int size = Flags.get().getSize();

    DiskWrites writes = new DiskWrites(concurrency, files, size);
    writes.sequentialWrites();
    writes.parallelWrites();
  }

  DiskWrites(int concurrency, int files, int size) {
    this.concurrency = concurrency;
    this.files = files;
    this.size = size;
  }

  private void sequentialWrites() throws IOException {
    byte[] data = generateRandomData(size);

    // TODO: allow the location to be configured, in case this is a different mountpoint
    Path tempDirWithPrefix = Files.createTempDirectory("diskwrites");
    System.out.println("Sequential writing to " + tempDirWithPrefix);
    long start = System.currentTimeMillis();
    for (int i = 0; i < files; i++) {
      writeFile(tempDirWithPrefix.resolve(String.valueOf(i)), data);
    }
    long elapsed = System.currentTimeMillis() - start;

    System.out.println("Elapsed: " + elapsed);
  }

  private void parallelWrites() throws IOException {
    byte[] data = generateRandomData(size);

    List<String> filenames = new ArrayList<>(files);
    for (int i = 0; i < files; i++) {
      filenames.add(String.valueOf(i));
    }
    Path tempDirWithPrefix = Files.createTempDirectory("diskwrites");
    System.out.println("Parallel Writing to " + tempDirWithPrefix);

    long start = System.currentTimeMillis();
    filenames
        .parallelStream()
        .forEach(f -> writeFile(tempDirWithPrefix.resolve(f), data));
    long elapsed = System.currentTimeMillis() - start;

    System.out.println("Elapsed: " + elapsed);
  }

  private static void writeFile(Path file, byte[] data) {


    try (OutputStream outputStream = createOutputStream(file)) {
      outputStream.write(data);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
}

  private byte[] generateRandomData(int size) {
    byte[] data = new byte[size];
    random.nextBytes(data);
    return data;
  }

  private static OutputStream createOutputStream(Path file) throws IOException {
    return new BufferedOutputStream(Channels.newOutputStream(
        Files.newByteChannel(
            file,
            ImmutableSet.of(
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE))));
  }

}