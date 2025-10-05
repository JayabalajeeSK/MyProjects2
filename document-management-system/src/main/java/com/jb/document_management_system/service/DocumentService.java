package com.jb.document_management_system.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jb.document_management_system.entity.DocCollection;
import com.jb.document_management_system.entity.Document;
import com.jb.document_management_system.entity.User;
import com.jb.document_management_system.repository.DocCollectionRepository;
import com.jb.document_management_system.repository.DocumentRepository;

@Service
public class DocumentService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DocCollectionRepository collectionRepository;

    // 🔑 Get logged-in user
    private User getCurrentUser() {
        String usernameOrEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getByUsernameOrEmail(usernameOrEmail);
    }

    // ✅ Save document with directory check
    public String <Document> saveDocument(MultipartFile file, String title, String desc, boolean favorite, Long collectionId) throws IOException {
        // User fetch
        User user = getCurrentUser();

        // File meta
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Document doc = new Document();
        doc.setTitle(title);
        doc.setDescription(desc);
        doc.setFileName(fileName);
        doc.setFileType(file.getContentType());
        doc.setFileSize(file.getSize());
        doc.setFavorite(favorite);
        doc.setOwner(user);

        // ⬇️ Save actual file bytes to DB
        doc.setFileData(file.getBytes());

        // Optional collection
        if (collectionId != null) {
            DocCollection collection = collectionRepository.findByIdAndOwnerId(collectionId, user.getId())
                    .orElseThrow(() -> new RuntimeException("Collection not found"));
            doc.setCollection(collection);
        }

        documentRepository.save(doc);
        return "ok";
    }

    // ✅ Delete document
    public void deleteDocument(Long id) throws IOException {
        Document doc = documentRepository.findById(id).orElseThrow();
        if (!doc.getOwner().getId().equals(getCurrentUser().getId()))
            throw new AccessDeniedException("Not owner");

        Files.deleteIfExists(Paths.get(doc.getFilePath()));
        documentRepository.delete(doc);
    }

    // ✅ Download
    public Resource download(Long id) throws IOException {
        Document doc = documentRepository.findById(id).orElseThrow();
        return new UrlResource(Paths.get(doc.getFilePath()).toUri());
    }

    // ✅ Get all
    public List<Document> getAll() {
        return documentRepository.findByOwnerId(getCurrentUser().getId());
    }

    // ✅ Get by type
    public List<Document> getByType(String type) {
        return documentRepository.findByOwnerIdAndFileTypeIgnoreCase(getCurrentUser().getId(), type);
    }

    // ✅ Search by name
    public List<Document> searchByName(String keyword) {
        return documentRepository.findByOwnerIdAndFileNameContainingIgnoreCase(getCurrentUser().getId(), keyword);
    }

    // ✅ Count
    public long countDocs() {
        return documentRepository.countByOwnerId(getCurrentUser().getId());
    }

    // ✅ Favorites
    public List<Document> getFavorites() {
        return documentRepository.findByOwnerIdAndFavoriteTrue(getCurrentUser().getId());
    }

    // ✅ Toggle favorite
    public Document toggleFavorite(Long id) {
        Document doc = documentRepository.findById(id).orElseThrow();
        if (!doc.getOwner().getId().equals(getCurrentUser().getId()))
            throw new AccessDeniedException("Not owner");

        doc.setFavorite(!doc.isFavorite());
        return documentRepository.save(doc);
    }

    // ✅ Add to collection
    public Document addToCollection(Long docId, Long collectionId) {
        Document doc = documentRepository.findById(docId).orElseThrow();
        DocCollection collection = collectionRepository.findByIdAndOwnerId(collectionId, getCurrentUser().getId())
                .orElseThrow(() -> new RuntimeException("Collection not found"));

        doc.setCollection(collection);
        return documentRepository.save(doc);
    }

    // ✅ Remove from collection
    public Document removeFromCollection(Long docId) {
        Document doc = documentRepository.findById(docId).orElseThrow();
        if (!doc.getOwner().getId().equals(getCurrentUser().getId()))
            throw new AccessDeniedException("Not owner");

        doc.setCollection(null);
        return documentRepository.save(doc);
    }
}
