package spring.boot.core.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  public void deleteAll();

  FileInfo store(MultipartFile file, String... relativePath);

  FileInfo store(MultipartFile file, String[] extensions, String... relativePath);

  FileInfo store(MultipartFile file, String prefix, String[] extensions, String... relativePath);
}