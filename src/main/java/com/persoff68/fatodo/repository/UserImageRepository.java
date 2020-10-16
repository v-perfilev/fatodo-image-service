package com.persoff68.fatodo.repository;

import com.persoff68.fatodo.model.UserImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserImageRepository extends MongoRepository<UserImage, String> {

    Optional<UserImage> findByFilename(String filename);

    void deleteByFilename(String filename);
}
