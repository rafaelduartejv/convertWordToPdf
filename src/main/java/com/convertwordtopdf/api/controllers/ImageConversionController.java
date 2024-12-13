package com.convertwordtopdf.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/convert")
public class ImageConversionController {

    @PostMapping("/png-to-jpeg")
    public ResponseEntity<String> convertPngToJpg(@RequestParam("file") MultipartFile file) {
        try {
            if (!file.getOriginalFilename().endsWith(".png")) {
                return ResponseEntity.badRequest().body("O arquivo enviado não é um PNG válido.");
            }

            String originalFileName = file.getOriginalFilename();
            String baseFileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
            String savePath = "E:\\Downloads\\salvo\\" + baseFileName + ".jpeg";

            InputStream inputStream = file.getInputStream();
            BufferedImage image = ImageIO.read(inputStream);

            File outputFile = new File(savePath);
            ImageIO.write(image, "jpeg", outputFile);

            return ResponseEntity.ok("Arquivo convertido salvo em: " + savePath);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o arquivo.");
        }
    }

    @PostMapping("/jpeg-to-png")
    public ResponseEntity<String> convertJpgToPng(@RequestParam("file") MultipartFile file) {
        try {
            if (!file.getOriginalFilename().endsWith(".jpeg")) {
                return ResponseEntity.badRequest().body("O arquivo enviado não é um JPEG válido.");
            }

            String originalFileName = file.getOriginalFilename();
            String baseFileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
            String savePath = "E:\\Downloads\\salvo\\" + baseFileName + ".png";

            InputStream inputStream = file.getInputStream();
            BufferedImage image = ImageIO.read(inputStream);

            File outputFile = new File(savePath);
            ImageIO.write(image, "png", outputFile);

            return ResponseEntity.ok("Arquivo convertido salvo em: " + savePath);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o arquivo.");
        }
    }
}