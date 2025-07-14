package com.jb.document_management_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.document_management_system.entity.DocCollection;
import com.jb.document_management_system.repository.DocCollectionRepository;

@Service
public class DocCollectionService {
    @Autowired private DocCollectionRepository repo;
    @Autowired private UserService userService;

    public List<DocCollection> getMyCollections(String email) {
        Long ownerId = userService.getByEmail(email).getId();
        return repo.findByOwnerId(ownerId);
    }

    public DocCollection create(String name, String email) {
        DocCollection col = new DocCollection();
        col.setName(name);
        col.setOwner(userService.getByEmail(email));
        return repo.save(col);
    }

    public DocCollection edit(Long id, String name, String email) {
        DocCollection col = repo.findByIdAndOwnerId(id, userService.getByEmail(email).getId())
            .orElseThrow(() -> new RuntimeException("Collection not found"));
        col.setName(name);
        return repo.save(col);
    }
}
