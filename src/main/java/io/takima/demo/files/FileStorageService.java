package io.takima.demo.files;

import io.takima.demo.classes.file.FileDB;
import io.takima.demo.dao.FileDBDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Service for profile image
 */
@Service
public class FileStorageService {

    @Autowired
    private FileDBDAO fileDBRepository;

    /**
     * Store profile image
     * @param file Profile image
     * @return new file saved with parameters
     * @throws IOException Error
     */
    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

        return fileDBRepository.save(FileDB);
    }

    /**
     * Find a file by id
     * @param id Profile image
     * @return File found
     */
    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    /**
     * Find all files
     * @return All files
     */
    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
