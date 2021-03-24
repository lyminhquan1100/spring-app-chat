package spring.backend.library.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import spring.backend.library.exception.BaseException;
import spring.backend.library.utils.RandomUtils;

@Service
public class FileStorageService implements StorageService {

  @Value("${file.upload-dir}")
  private String filePath;

  private Path fileStorageLocation;

  @Override
  public FileInfo upload(MultipartFile file, String... relativePath) {

    return null;
  }

  @Override
  public FileInfo upload(MultipartFile file) {
    Map<String, String> mapFileName = storeFile(file);
    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/")
        .path("/downloadFile/").path(mapFileName.get("nameFileStore")).toUriString();

    FileInfo uploadFile = new FileInfo(mapFileName.get("nameFileInit"),
        mapFileName.get("nameFileStore"), file.getSize());

    return uploadFile;
  }

  public Map<String, String> storeFile(MultipartFile file) {
    // Normalize file name
    Map<String, String> mapFile = new HashMap<>();
    File f = new File(filePath);
    if (!f.exists()) {
      f.mkdirs();
    }

    fileStorageLocation = Paths.get(filePath);
    String fileNameCheck = StringUtils.cleanPath(file.getOriginalFilename());

    try {
      // Check if the file's name contains invalid characters
      if (fileNameCheck.contains("..")) {
        throw new BaseException("Sorry! Filename contains invalid path sequence " + fileNameCheck);
      }

      String fileExt = "";
      String fileNameFormat = "";
      if (fileNameCheck.contains(".")) {
        fileExt = fileNameCheck.substring(fileNameCheck.lastIndexOf('.'));
        fileNameFormat = "file";
      }
      String fileName = fileNameFormat + RandomUtils.getRandomId() + fileExt;
      // Copy file to the target location (Replacing existing file with the same name)
      Path targetLocation = this.fileStorageLocation.resolve(fileName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

      mapFile.put("nameFileInit", fileNameCheck);
      mapFile.put("nameFileStore", fileName);
      mapFile.put("targitLocation", targetLocation.toString());

      return mapFile;
    } catch (IOException ex) {
      throw new BaseException("Could not store file " + fileNameCheck + ". Please try again!");
    }
  }


}
