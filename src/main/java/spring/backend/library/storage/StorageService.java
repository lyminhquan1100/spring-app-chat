package spring.backend.library.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
  FileInfo upload(MultipartFile file,String... relativePath);
  FileInfo upload(MultipartFile multipartFile);
}
