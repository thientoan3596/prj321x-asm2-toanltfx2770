package asm02.controller.view;

import asm02.service.CompanyService;
import asm02.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
public class CompanyViewController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private FileService fileService;
}
