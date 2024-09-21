package asm02.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    // TODO: 9/20/2024 Replace with external place to store file!
    @Value("${FILE.MAX-NAME-LENGTH}")
    private int MAX_FILE_NAME_LENGTH;
    @Value("${FILE.UPLOAD-DIR}")
    private String uploadDir;
    @Value("${FILE.IMAGE-DIR}")
    private String imgDir;

    @Value("${FILE.CV-DIR}")
    private String cvDir;

    @Value("${FILE.OTHER-DIR}")
    private String otherDir;
    @Autowired
    private ServletContext servletContext;

    @SneakyThrows
    @Override
    public String uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String newFileName;
        if(isImage(file))
            newFileName = generateName(fileName);
        else {
            String uuid = UUID.randomUUID().toString();
            newFileName = uuid + getExtension(fileName);
        }
        Path  p = getPath(file, newFileName);
        saveFile(file,p);
        return newFileName;
    }
    private Path getPath(MultipartFile file, String name){
        return Paths.get(
                servletContext.getRealPath("/resources/"),
                uploadDir,
                isCv(file) ? cvDir
                        : isImage(file) ? imgDir
                        : otherDir,
                name
        );
    }
    private void saveFile(MultipartFile file, Path filePath) throws IOException{
        Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath);
    }

    @Override
    public void deleteFile(String path) {
        Path p = Paths.get(servletContext.getRealPath("/resources/"), uploadDir, path);
        try {
            Files.deleteIfExists(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.startsWith("image/jpeg") ||
                contentType.startsWith("image/png") ||
                contentType.startsWith("image/gif"));
    }

    @Override
    public boolean isCv(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null
                && contentType.endsWith("pdf")
                && file.getOriginalFilename().endsWith("pdf");
    }
    private String generateName(String originalName) {
        String uuid = UUID.randomUUID().toString();
        String newFilename = uuid + "_" + originalName;
        // TODO: 9/21/2024 Should consider case when uuid + originalName > 255 
        if (newFilename.length() > MAX_FILE_NAME_LENGTH) {
            String extension = "";
            int extIndex = originalName.lastIndexOf(".");
            if (extIndex > 0)
                extension = originalName.substring(extIndex);
            newFilename = uuid + "_" + originalName.substring(0, MAX_FILE_NAME_LENGTH - uuid.length() - extension.length()) + extension;
        }
        return newFilename;
    }
    /**
     * Get file extension base on last index of ".", If not present return empty string.
     * NB! "." (period/dot) is included
     */
    private String getExtension (String fileName) {
       int extIdx = fileName.lastIndexOf(".");
       return extIdx >0 ? fileName.substring(extIdx) : "";
    }
}
