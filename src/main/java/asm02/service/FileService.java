package asm02.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * Save the file to UPLOADS with predefined dir! (Check properties) with a NEW name.
     * <ul>
     *     <li>
     *         If the file image, save with <uuid>_<originalName> (trimmed if total >255 character)
     *     </li>
     *     <li>
     *         If the file image, save with <uuid>.<file ext>
     *     </li>
     * </ul>
     * @return file name only. NB! NO PATH
     */
    String uploadFile(MultipartFile file);

    /**
     * Deleting file!
     */
    void deleteFile(String fileName);

    boolean isImage(MultipartFile file);
    boolean isCv(MultipartFile file);

}
