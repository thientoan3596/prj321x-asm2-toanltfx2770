package asm02.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
    /**
     * Checking the existing file and produce new file name!
     * If there is matching file name adding suffix with the largest duplicate suffix indicator + 1.
     * Otherwise, keep the original name.
     * Eg1:
     * List.of('item1.pdf','item2.pdf')
     * file.name = item1.pdf, => Result: item1(1).pdf
     * <p>
     * Eg2:
     * List.of('item1.pdf','item2.pdf')
     * file.name = item.pdf, => Result: item.pdf
     * <p>
     * Eg3:
     * List.of('item1(3).pdf','item2.pdf')
     * file.name = item1.pdf, => Result: item1(4).pdf
     */
    public static String addingSuffix(MultipartFile file, List<String> existingFileNames) throws NullPointerException {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new NullPointerException("Original file name cannot be null");
        }
        int lastDotIdx = originalFileName.lastIndexOf(".");
        String fileExtension = originalFileName.substring(lastDotIdx);
        String nameWithoutExtension = originalFileName.substring(0, lastDotIdx);
        Pattern pattern = Pattern.compile(nameWithoutExtension + "(?:\\((\\d+)\\))?" + fileExtension + "$");
        int maxDuplicateVersion = existingFileNames.stream()
                .map(f -> {
                    Matcher m = pattern.matcher(f);
                    if (m.matches() && m.group(1) != null) {
                        return Integer.valueOf(m.group(1));
                    } else if (m.matches()) {
                        return 0;
                    }
                    return -1;
                })
                .max(Integer::compareTo).orElse(-1) + 1;
        if (maxDuplicateVersion > 0){
            return nameWithoutExtension+"("+maxDuplicateVersion+")"+fileExtension;
        }
        return originalFileName;
    }
}
