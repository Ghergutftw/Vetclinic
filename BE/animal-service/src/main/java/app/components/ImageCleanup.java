package app.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ImageCleanup implements CommandLineRunner {

    private static final List<String> PRESERVE_FILE_NAMES = Arrays.asList("Bella.jpg", "Luna.jpg", "Pipu.jpg", "Topolina.jpg" , "default.jpg");

    public void cleanUpImages() {
        String imagesFolderPath = "animal-service/src/main/resources/images/animals/";

        File imagesFolder = new File(imagesFolderPath);

        if (imagesFolder.exists() && imagesFolder.isDirectory()) {
            File[] files = imagesFolder.listFiles();

            if (files != null) {
                Arrays.stream(files)
                        .filter(file -> !shouldPreserve(file.getName()))
                        .forEach(this::deleteFile);
            }
        }
    }

    private boolean shouldPreserve(String fileName) {
        return PRESERVE_FILE_NAMES.contains(fileName);
    }

    private void deleteFile(File file) {
        try {
            Files.delete(file.toPath());
            log.info("Deleted file: " + file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run(String... args) throws Exception {
        cleanUpImages();
    }
}
