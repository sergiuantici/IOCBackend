package com.example.licenta.service;

import com.example.licenta.model.User;
import com.example.licenta.utils.ExcelHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AdminService {

    public List<User> processExcel(MultipartFile file) throws IOException {
        return ExcelHelper.excelToUsers(file.getInputStream());
    }
}
