package com.example.admin.contoller;

import com.example.admin.contoller.utils.ControllerUtils;
import com.example.admin.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("")
@RestController()
public class AdminController {
    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final ControllerUtils controllerUtils;
    private final AdminService adminService;

    @Autowired
    public AdminController(ControllerUtils controllerUtils, AdminService adminService) {
        this.controllerUtils = controllerUtils;
        this.adminService = adminService;
    }

    @PostMapping("/merchant/{identifier}/for/deletion")
    public ResponseEntity<String> setMerchantForDeletion(@PathVariable String identifier) {
        return new ResponseEntity<>(adminService.setMerchantForDeletion(identifier), HttpStatus.OK);
    }

}
