package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomFileNameController {

    public String nextString() {
        String fileName;
        do {
            for (int idx = 0; idx < buf.length; ++idx) {
                buf[idx] = symbols[random.nextInt(symbols.length)];
            }
            fileName = new String(buf);
        } while(folderFileSet.contains(fileName));
        folderFileSet.add(fileName);
        return fileName;
    }

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    public static final String digits = "0123456789";

    public static final String alphanum = upper + lower + digits;

    private final Random random;

    private final char[] symbols;

    private final char[] buf;

    private Set<String> folderFileSet = new HashSet<>();

    public RandomFileNameController(String folderURL, int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
        if(folderURL != null) {
            try (Stream<Path> walk = Files.walk(Paths.get(folderURL))) {
                List<String> result = walk.filter(Files::isRegularFile)
                        .map(x -> x.toString()).collect(Collectors.toList());

                for (String fileName : result) {
                    folderFileSet.add(fileName.replace(folderURL, "").split("\\.")[0]);
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    public RandomFileNameController(String folderURL, int length, Random random) {
        this(folderURL, length, random, alphanum);
    }

    public RandomFileNameController(String folderURL, int length) {
        this(folderURL, length, new SecureRandom());
    }

    public RandomFileNameController(String folderURL) {
        this(folderURL, 21);
    }

    public RandomFileNameController() {
        this(null, 21);
    }
}
