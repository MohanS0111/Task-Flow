package com.todoapplication.todo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.todoapplication.todo.domain.User;


@Repository
public interface IUserRepository extends MongoRepository<User, String>{

}
