package com.jb.document_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jb.document_management_system.entity.Document;
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> 
{
    List<Document> findByOwnerId(Long ownerId);

    List<Document> findByOwnerIdAndFileTypeIgnoreCase(Long ownerId, String type);
    List<Document> findByOwnerIdAndFileNameContainingIgnoreCase(Long ownerId, String name);
    long countByOwnerId(Long ownerId);

    List<Document> findByOwnerIdAndFavoriteTrue(Long ownerId);
}

