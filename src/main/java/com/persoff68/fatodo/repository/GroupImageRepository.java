package com.persoff68.fatodo.repository;

import com.mongodb.lang.NonNull;
import com.persoff68.fatodo.config.aop.cache.annotation.CacheEvictMethod;
import com.persoff68.fatodo.config.aop.cache.annotation.CacheableMethod;
import com.persoff68.fatodo.model.GroupImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupImageRepository extends MongoRepository<GroupImage, String> {

    @CacheableMethod(cacheName = "image-by-filename", key = "#filename")
    Optional<GroupImage> findByFilename(String filename);

    @Override
    @CacheEvictMethod(cacheName = "image-by-filename", key = "#image.filename")
    @NonNull
    <S extends GroupImage> S save(@NonNull S image);

    @CacheEvictMethod(cacheName = "image-by-filename", key = "#filename")
    void deleteByFilename(String filename);
}
