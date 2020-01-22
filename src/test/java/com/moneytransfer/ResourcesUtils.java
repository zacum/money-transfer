package com.moneytransfer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourcesUtils {

    public static String getString(String path) throws IOException {
        return Files.readString(Paths.get(path))
                .replace("\n", "")
                .replace("\r", "")
                .replaceAll("\\s+", "");
    }

}
