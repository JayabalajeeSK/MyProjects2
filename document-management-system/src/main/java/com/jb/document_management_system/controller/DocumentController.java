package com.jb.document_management_system.controller;
import com.jb.document_management_system.entity.Document;
import com.jb.document_management_system.service.DocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController

@RequestMapping("/api/documents")

public class DocumentController {

    @Autowired
    private DocumentService documentService;

    // ✅ Upload
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/upload")
    public ResponseEntity<Document> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam String title,
            @RequestParam(required = false) String desc,
            @RequestParam(defaultValue = "false") boolean favorite,
            @RequestParam(required = false) Long collectionId
    ) throws IOException 
    {
        Document doc = documentService.saveDocument(file, title, desc, favorite, collectionId);
        return ResponseEntity.ok(doc);
    }

    // ✅ Download
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws IOException {
        Resource file = documentService.download(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    // ✅ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws IOException {
        documentService.deleteDocument(id);
        return ResponseEntity.ok("Document deleted successfully.");
    }

    // ✅ Get all
    @GetMapping("/all")
    public List<Document> getAll() {
        return documentService.getAll();
    }

    // ✅ Get by type
    @GetMapping("/type")
    public List<Document> getByType(@RequestParam String type) {
        return documentService.getByType(type);
    }

    // ✅ Search by name
    @GetMapping("/search")
    public List<Document> search(@RequestParam String keyword) {
        return documentService.searchByName(keyword);
    }

    // ✅ Count
    @GetMapping("/count")
    public long count() {
        return documentService.countDocs();
    }

    // ✅ Favorites
    @GetMapping("/favorites")
    public List<Document> favorites() {
        return documentService.getFavorites();
    }

    // ✅ Toggle Favorite
    @PutMapping("/favorite/{id}")
    public Document toggleFavorite(@PathVariable Long id) {
        return documentService.toggleFavorite(id);
    }

    // ✅ Add to Collection
    @PutMapping("/collection/{docId}/add")
    public Document addToCollection(@PathVariable Long docId, @RequestParam Long collectionId) {
        return documentService.addToCollection(docId, collectionId);
    }

    // ✅ Remove from Collection
    @PutMapping("/collection/{docId}/remove")
    public Document removeFromCollection(@PathVariable Long docId) {
        return documentService.removeFromCollection(docId);
    }
}
