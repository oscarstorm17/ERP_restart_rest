package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Friends;
@Repository
public interface FriendsRepository extends JpaRepository<Friends, Long>{
	List<Friends> findByUser1(String user1);
	
	
}
