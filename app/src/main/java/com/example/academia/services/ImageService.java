package com.example.academia.services;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.*;

@Service
public class ImageService {
    public static byte[] compressImage(byte[] data) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream)) {
            gzipOutputStream.write(data);
        }

        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] compressedImage) throws IOException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedImage);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = gzipInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();
        }
    }

    public byte[] resizeImage(MultipartFile imageFile, int targetWidth, int targetHeight) {
        try {
            ByteArrayOutputStream resizedImageStream = new ByteArrayOutputStream();
            Thumbnails.of(imageFile.getInputStream())
                    .size(targetWidth, targetHeight)
                    .toOutputStream(resizedImageStream);

            return resizedImageStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
