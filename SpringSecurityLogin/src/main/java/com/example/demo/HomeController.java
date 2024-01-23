package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class HomeController {
	
	@GetMapping("/login")
    public String login() {
        return "請登入";
    }
	
    @GetMapping("/")
    public String home() {
        return "Welcome to the home page!";
    }
    
    @GetMapping("/hello")
    public String hello() {
        return "Hello everyone!";
    }
    
    @GetMapping("/manager")
    public String manager() {
        return "Hello Manager !";
    }
    
    @CrossOrigin(origins = "http://localhost:8000")
    @PostMapping("/upload") // 上傳圖片與名稱
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) throws IllegalStateException, IOException {
    	// 將圖片存入到 C:/upload/ 資料夾
    	// 並且將圖片名稱改成 name
    	// 例如: C:/upload/123.jpg, 程式碼如下:
    	File f = new File("C:/upload/" + name + ".png");
    	file.transferTo(f);
    	
    	return "上傳成功";
    }
    
    // 取得圖片
    @CrossOrigin(origins = "http://localhost:8000")
    @GetMapping("/image")
    public void image(@RequestParam("name") String name, HttpServletResponse response) throws IOException {
        File file = new File("C:/upload/" + name + ".png");
        response.setContentType("image/jpeg");
        byte[] imageData = Files.readAllBytes(file.toPath());
        response.getOutputStream().write(imageData);
    }
}

