package com.example.social_network.repository;

import com.example.social_network.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
    // List<Contest> findAll();
}
