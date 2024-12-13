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
public class convertToImg3x4 {

    @PostMapping("/to-3x4")
    public ResponseEntity<String> convertTo3x4(@RequestParam("file") MultipartFile file) {
        try {

            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || !originalFileName.matches(".*\\.(jpg|jpeg|png|gif|bmp)$")) {
                return ResponseEntity.badRequest().body("O arquivo enviado não é uma imagem válida.");
            }

            String baseFileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
            String savePath = "E:\\Downloads\\salvo\\" + baseFileName + "_3x4.jpg";

            InputStream inputStream = file.getInputStream();
            BufferedImage originalImage = ImageIO.read(inputStream);

            int targetWidth = 354;
            int targetHeight = 472;

            BufferedImage resizedImage = resizeImage(originalImage, targetWidth, targetHeight);

            File outputFile = new File(savePath);
            ImageIO.write(resizedImage, "jpg", outputFile);

            return ResponseEntity.ok("Imagem convertida para 3x4 e salva em: " + savePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o arquivo.");
        }
    }
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        double scaleX = (double) targetWidth / originalWidth;
        double scaleY = (double) targetHeight / originalHeight;
        double scaleFactor = Math.min(scaleX, scaleY);

        int newWidth = (int) (originalWidth * scaleFactor);
        int newHeight = (int) (originalHeight * scaleFactor);

        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(scaledImage, (targetWidth - newWidth) / 2, (targetHeight - newHeight) / 2, null);
        g.dispose();

        return resizedImage;
    }
}

