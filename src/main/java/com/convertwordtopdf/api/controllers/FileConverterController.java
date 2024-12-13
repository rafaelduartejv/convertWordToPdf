package com.convertwordtopdf.api.controllers;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/api/convert")
public class FileConverterController {

    @PostMapping("/word-to-pdf")
    public ResponseEntity<String> convertWordToPdf(@RequestParam("file") MultipartFile file) {
        try {
            if (!file.getOriginalFilename().endsWith(".docx")) {
                return ResponseEntity.badRequest().body("O arquivo enviado não é um Word válido.");
            }
            String originalFileName = file.getOriginalFilename();
            String baseFileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));

            String savePath = "E:\\Downloads\\salvo\\" + baseFileName + ".pdf";
            File fileOnServer = new File(savePath);

            ByteArrayInputStream wordInputStream = new ByteArrayInputStream(file.getBytes());
            XWPFDocument wordDocument = new XWPFDocument(wordInputStream);

            PdfWriter writer = new PdfWriter(savePath);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            for (XWPFParagraph paragraph : wordDocument.getParagraphs()) {
                String text = paragraph.getText();
                if (text != null && !text.isEmpty()) {
                    document.add(new com.itextpdf.layout.element.Paragraph(text));
                }
            }

            document.close();
            wordDocument.close();

            return ResponseEntity.ok("Arquivo convertido salvo em: " + savePath);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o arquivo.");
        }
    }


    @PostMapping("/pdf-to-word")
    public ResponseEntity<String> convertPdfToWord(@RequestParam("file") MultipartFile file) {
        try {

            if (!file.getOriginalFilename().endsWith(".pdf")) {
                return ResponseEntity.badRequest().body("O arquivo enviado não é um PDF válido.");
            }

            String originalFileName = file.getOriginalFilename();
            String baseFileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));

            String savePath = "E:\\Downloads\\salvo\\" + baseFileName + ".docx";
            File fileOnServer = new File(savePath);

            ByteArrayInputStream pdfInputStream = new ByteArrayInputStream(file.getBytes());
            com.itextpdf.kernel.pdf.PdfReader reader = new com.itextpdf.kernel.pdf.PdfReader(pdfInputStream);
            com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(reader);

            XWPFDocument wordDocument = new XWPFDocument();

            for (int i = 1; i <= pdfDocument.getNumberOfPages(); i++) {
                String pageContent = com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor.getTextFromPage(pdfDocument.getPage(i));
                XWPFParagraph paragraph = wordDocument.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText(pageContent);
            }

            pdfDocument.close();

            fileOnServer.getParentFile().mkdirs();

            try (FileOutputStream fos = new FileOutputStream(fileOnServer)) {
                wordDocument.write(fos);
            }
            wordDocument.close();

            return ResponseEntity.ok("Arquivo convertido salvo em: " + savePath);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o arquivo.");
        }
    }
}