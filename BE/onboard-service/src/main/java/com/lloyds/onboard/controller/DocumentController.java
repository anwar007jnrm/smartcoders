package com.lloyds.onboard.controller;

import com.lloyds.onboard.entity.ApplicationDocument;
import com.lloyds.onboard.service.ApplicationDocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final ApplicationDocumentService documentService;

    public DocumentController(ApplicationDocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<ApplicationDocument> getDocument(@PathVariable Long documentId) {
        ApplicationDocument document = documentService.getDocument(documentId);
        return ResponseEntity.ok(document);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadDocument(
            @RequestParam("id") Long id,
            @RequestParam("currentPage") int pageNumber,
            @RequestParam("fieldName") String fieldName,
            @RequestParam("file") MultipartFile file) {
        try {
            ApplicationDocument document = documentService.saveDocument(id, pageNumber, fieldName, file);
            return ResponseEntity.ok("Document uploaded successfully with ID: " + document.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading document: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{documentId}", consumes = "multipart/form-data")
    public ResponseEntity<String> updateDocument(
            @PathVariable Long documentId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("fieldName") String fieldName,
            @RequestParam("pageNumber") int pageNumber) {
        try {
            ApplicationDocument updatedDocument = documentService.updateDocument(documentId, file, fieldName, pageNumber);
            return ResponseEntity.ok("Document updated successfully with ID: " + updatedDocument.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating document: " + e.getMessage());
        }
    }
}