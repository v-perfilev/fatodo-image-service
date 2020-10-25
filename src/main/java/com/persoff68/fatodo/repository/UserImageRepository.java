package com.persoff68.fatodo.repository;

import com.persoff68.fatodo.model.UserImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserImageRepository extends MongoRepository<UserImage, UUID> {

    Optional<UserImage> findByFilename(String filename);

    void deleteByFilename(String filename);
}
