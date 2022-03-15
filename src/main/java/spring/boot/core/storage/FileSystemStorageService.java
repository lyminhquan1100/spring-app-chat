package spring.boot.core.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import spring.boot.core.exception.BaseException;
import spring.boot.core.utils.DateUtils;
import spring.boot.core.utils.FileUtil;
import spring.boot.core.utils.StringUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {
    @Value("${file.upload-dir}")
    private String rootPath;

    private final Path rootLocation;

    private final ResourceLoader resourceLoader;

    public FileSystemStorageService(@Value("${app.storage.location:./files/public}") String rootLocationStr, ResourceLoader resourceLoader) {
        this.rootLocation = Paths.get(rootLocationStr).normalize();
        this.resourceLoader = resourceLoader;
        if (!Files.exists(rootLocation)) {
            FileUtil.createDirectories(rootLocation);
        }
    }

    @Override
    public FileInfo store(MultipartFile file, String... relativePath) {
        return store(file, null, relativePath);
    }

    @Override
    public FileInfo store(MultipartFile file, String[] extensions, String... relativePath) {
        return store(file, null, extensions, relativePath);
    }

    @Override
    public FileInfo store(MultipartFile file, String prefix, String[] extensions, String... relativePath) {
        if (file == null || file.isEmpty()) {
            throw new BaseException("không tìm thấy file");
        }

        String fileName = file.getOriginalFilename();

        if (fileName == null) {
            throw new BaseException("Không có tên file");
        }

        fileName = (prefix == null ? "" : prefix) + StringUtil.cleanPath(fileName);

        Path location = resolve(relativePath)
                .resolve(DateUtils.formatDate(DateUtils.getToday()))
                .resolve(UUID.randomUUID().toString())
                .resolve(fileName);
        validatePath(location, rootLocation);

        try {
            if (!Files.exists(location.getParent())) {
                FileUtil.createDirectories(location.getParent());
            }

            file.transferTo(location);
            return getFileInfo(location);
        } catch (IOException e) {
            throw new BaseException("lỗi ioexception");
        }
    }

    protected FileInfo getFileInfo(Path path) {
        path = path.normalize();
        return new FileInfo(
                path.toAbsolutePath(),
                rootLocation.relativize(path),
                path.getFileName(),
                rootLocation
                        .relativize(path)
                        .toString()
                        .replaceAll("\\\\", "/")
        );
    }

    private Path resolve(String... relativePath) {
        Path location = rootLocation;
        if (relativePath != null && relativePath.length > 0) {
            for (String path : relativePath) {
                if (!StringUtil.isBlank(path)) {
                    location = location.resolve(path);
                }
            }
        }
        return location;
    }

    private void validatePath(Path path, Path parent) {
        String name = parent.toAbsolutePath().normalize().relativize(path.toAbsolutePath().normalize()).toString();

        if (name.contains("..")) {
            throw new BaseException("path not valid");
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }


}