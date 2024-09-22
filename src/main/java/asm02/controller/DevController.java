package asm02.controller;

import asm02.dao.UserDao;
import asm02.dto.response.UserResponse;
import asm02.entity.User;
import asm02.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("dev")
public class DevController {
    @Value("${FILE.UPLOAD-DIR}")
    private String uploadDir;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("ping")
    public String ping(){
        return "pong";
    }
    @GetMapping("/user")
    @Transactional
    public String getUser() {
        Long id = 1L;
        User user = userDao.findById(id).orElse(null);
        if(user == null)
            return "User not found";
        UserResponse response =  userMapper.toResponse(user);
        return response.toString();
    }


    @GetMapping("/dir")
    public String dir(@RequestParam String content) {
        return "Depreciated";
    }

    @GetMapping("/read")
    public String read(HttpSession s) {
        return "Depreciated";
    }
}
