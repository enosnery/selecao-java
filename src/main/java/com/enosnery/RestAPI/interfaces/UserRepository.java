package com.enosnery.RestAPI.interfaces;


import com.enosnery.RestAPI.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

}
