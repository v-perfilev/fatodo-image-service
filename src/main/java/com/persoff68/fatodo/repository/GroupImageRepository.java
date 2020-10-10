package com.persoff68.fatodo.repository;

import com.persoff68.fatodo.model.GroupImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupImageRepository extends MongoRepository<GroupImage, String> {

    Optional<GroupImage> findByFilename(String filename);

    void deleteByFilename(String filename);
}
