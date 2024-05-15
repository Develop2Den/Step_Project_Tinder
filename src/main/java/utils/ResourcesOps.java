package src.main.java.utils;


import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourcesOps {

    public static String dirUnsafe(String prefix) {
//        try {
//            URL resourceUrl = ResourcesOps.class.getClassLoader().getResource(prefix);
//            if (resourceUrl == null) {
//                throw new IllegalArgumentException("Resource not found: " + prefix);
//            }
//            Path resourcePath = Paths.get(resourceUrl.toURI());
//            return resourcePath.toString();
//        } catch (Exception ex) {
//            throw new RuntimeException("Error while getting resource directory: " + prefix, ex);
//        }
        try {
            URI uri = ResourcesOps.class
                    .getClassLoader()
                    .getResource(prefix)
                    .toURI();
            String mainPath = Paths.get(uri).toString();
            Path path = Paths.get(mainPath);
            return path.toString();
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }
}
