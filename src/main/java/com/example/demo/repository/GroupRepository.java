package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Group;

import java.util.Optional;
import java.util.List;


@Repository
public interface GroupRepository extends JpaRepository<Group, Long>{
	Optional<Group> findByGroupID(Long groupID);
	List<Group> findByAdmin(String admin);
	
	
	
	
	
	
}
