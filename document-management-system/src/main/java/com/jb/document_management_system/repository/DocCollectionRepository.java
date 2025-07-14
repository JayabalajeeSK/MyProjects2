package com.jb.document_management_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jb.document_management_system.entity.DocCollection;

@Repository
public interface DocCollectionRepository extends JpaRepository<DocCollection, Long> 
{
    List<DocCollection> findByOwnerId(Long ownerId);
    Optional<DocCollection> findByIdAndOwnerId(Long id, Long ownerId);
}
