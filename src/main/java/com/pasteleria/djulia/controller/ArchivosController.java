package com.pasteleria.djulia.controller;

import com.pasteleria.djulia.dto.ApiResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ArchivosController {

    @GetMapping("/archivos/{categoria}/{archivo}")
    public ResponseEntity<?> obtenerArchivoProducto(
            @PathVariable String categoria,
            @PathVariable String archivo) {

        try {
            String path = "static/productos/" + categoria + "/" + archivo;
            Resource resource = new ClassPathResource(path);

            if (!resource.exists()) {
                return ResponseEntity
                        .status(404)
                        .body(new ApiResponse(false,
                                "El archivo no existe",
                                null));
            }

            String contentType;
            String filename = archivo.toLowerCase();
            if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
                contentType = "image/jpeg";
            } else if (filename.endsWith(".png")) {
                contentType = "image/png";
            } else {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + archivo + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body(new ApiResponse(false,
                            "Error cargando archivo: " + e.getMessage(),
                            null));
        }
    }

    @GetMapping("/comprobantes/{archivo}")
    public ResponseEntity<?> obtenerComprobante(
            @PathVariable String archivo) {

        try {
            String path = "static/comprobantes/" + archivo;
            Resource resource = new ClassPathResource(path);

            if (!resource.exists()) {
                return ResponseEntity
                        .status(404)
                        .body(new ApiResponse(false,
                                "El comprobante no existe",
                                null));
            }

            String contentType;
            String filename = archivo.toLowerCase();
            if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
                contentType = "image/jpeg";
            } else if (filename.endsWith(".png")) {
                contentType = "image/png";
            } else if (filename.endsWith(".pdf")) {
                contentType = "application/pdf";
            } else {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + archivo + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body(new ApiResponse(false,
                            "Error cargando comprobante: " + e.getMessage(),
                            null));
        }
    }
}
