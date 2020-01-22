package com.moneytransfer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourcesUtils {

    public static String getOriginalString(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }

    public static String getString(String path) throws IOException {
        return getOriginalString(path)
                .replace("\n", "")
                .replace("\r", "")
                .replaceAll("\\s+", "");
    }

}
