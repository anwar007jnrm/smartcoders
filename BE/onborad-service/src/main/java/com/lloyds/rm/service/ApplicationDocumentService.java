package com.lloyds.rm.service;

import com.lloyds.rm.entity.ApplicationDocument;
import com.lloyds.rm.repository.ApplicationDocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ApplicationDocumentService {

    private final ApplicationDocumentRepository repository;

    public ApplicationDocumentService(ApplicationDocumentRepository repository) {
        this.repository = repository;
    }

    public ApplicationDocument saveDocument(Long applicationId, int pageNumber, String fieldName, MultipartFile file) throws IOException {
        ApplicationDocument document = new ApplicationDocument();
        document.setApplicationId(applicationId);
        document.setPageNumber(pageNumber);
        document.setFieldName(fieldName);
        document.setFileName(file.getOriginalFilename());
        document.setFileType(file.getContentType());
        document.setFileContent(file.getBytes());
        return repository.save(document);
    }


    public Optional<ApplicationDocument> getDocument(Long documentId) {
        return repository.findById(documentId);
    }

    public ApplicationDocument updateDocument(Long documentId, MultipartFile file, String fieldName, int pageNumber) throws IOException {
        ApplicationDocument document = repository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Document not found with ID: " + documentId));

        document.setFileName(file.getOriginalFilename());
        document.setFileType(file.getContentType());
        document.setFileContent(file.getBytes());
        document.setFieldName(fieldName);
        document.setPageNumber(pageNumber);

        return repository.save(document);
    }
}