package asm02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("dev")
public class DevController {
    @Value("${FILE.UPLOAD-DIR}")
    private String uploadDir;
    @Autowired
    private ServletContext servletContext;

    @GetMapping("/dir")
    public String dir(@RequestParam String content) {
   /*     String sysDir = System.getProperty("user.dir");
        System.out.println("SYSDIR:"+sysDir);
        Path fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        System.out.println(fileStorageLocation);
        String httpCtxRealPath = s.getServletContext().getRealPath("/");
        System.out.println("REQ REAL PATH :"+httpCtxRealPath);
        String sourceCodePath = getClass().getClassLoader().getResource("").getPath();
        System.out.println("sourceCodePath :"+sourceCodePath);
        String catHome = System.getProperty("catalina.home");
        System.out.println("Cat Home: "+catHome);*/
        String path = servletContext.getRealPath("/resources/static/assets");
        File directory = new File(path);
        File file = new File(path, "file.txt");
        System.out.println(path);
        try {
            if (file.createNewFile()) {
                FileWriter writer = new FileWriter(file);
                if(!content.isEmpty())
                    writer.write(content);
                else
                    writer.write("No content");
                writer.close();
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return "dev/dir";
    }

    @GetMapping("/read")
    public String read(HttpSession s) {
        String path = servletContext.getRealPath("/resources/static/assets/file.txt");
        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            System.out.println(content);
            return content;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return "unable";
    }
}
