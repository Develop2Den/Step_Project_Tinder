package utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourcesOps {
    public static List<String> dirUnsafe(String prefix) {
        try {
            URL resourceUrl = ResourcesOps.class.getClassLoader().getResource(prefix);
            if (resourceUrl == null) {
                throw new IllegalArgumentException("Resource not found: " + prefix);
            }
            URI resourceUri = resourceUrl.toURI();

            Path resourcePath;
            if ("jar".equals(resourceUri.getScheme())) {
                try (FileSystem fs = FileSystems.newFileSystem(resourceUri, Collections.emptyMap())) {
                    resourcePath = fs.getPath(prefix);
                }
            } else {
                resourcePath = Paths.get(resourceUri);
            }

            try (Stream<Path> paths = Files.walk(resourcePath, 1)) {
                return paths
                        .filter(Files::isRegularFile)
                        .map(Path::toString)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error while getting resource directory: " + prefix, ex);
        }
    }
}
