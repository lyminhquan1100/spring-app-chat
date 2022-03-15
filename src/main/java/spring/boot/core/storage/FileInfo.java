package spring.boot.core.storage;

import lombok.*;

import java.nio.file.Path;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileInfo {

  private Path absolutePath;

  private Path relativePath;

  private Path fileName;

  private String filePath;
}