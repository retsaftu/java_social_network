package com.example.social_network.repository;

import com.example.social_network.models.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Users, String> {
}
