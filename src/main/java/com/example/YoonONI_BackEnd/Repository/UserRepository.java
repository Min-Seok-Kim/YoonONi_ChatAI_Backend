package com.example.YoonONI_BackEnd.Repository;

import org.springframework.stereotype.Repository;

import com.example.YoonONI_BackEnd.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}