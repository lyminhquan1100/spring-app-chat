package spring.boot.module.file.service;

import com.google.common.io.ByteStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.boot.core.exception.BaseException;
import spring.boot.core.storage.FileInfo;
import spring.boot.core.storage.StorageService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    private final Path imageLocation = Paths.get("uploads");
    private final String DOCUMENT = "documents";

    @Autowired
    private StorageService storageService;

    @Override
    public ResponseEntity<byte[]> getImage(String fileName,
                                           HttpServletRequest httpServletRequest) throws IOException {
        Resource resource = loadFileAsResource(fileName);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(ByteStreams.toByteArray(resource.getInputStream()), headers,
                HttpStatus.OK);
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            File f = imageLocation.toFile();
            if (!f.exists()) {
                f.mkdirs();
            }

            Path filePath = this.imageLocation.resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                System.out.println(resource.toString());
                return resource;
            } else {
                System.out.println("not found");
                throw new BaseException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage() + "------");
            throw new BaseException("File not found " + fileName);
        }
    }

    @Override
    public FileInfo upload(MultipartFile file) {
        return storageService.store(file, DOCUMENT);
    }
}
