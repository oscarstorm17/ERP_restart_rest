package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import com.example.demo.repository.FriendsRepository;
import com.example.demo.security.JwtUtil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exceptions.FriendsNotFoundException;
import com.example.demo.model.Friends;
import com.example.demo.model.User;
@Service
public class FriendService {
	private final FriendsRepository friendsRepository;
	private final UserRepository userRepository;
	
	public FriendService(
			FriendsRepository friendsRepository,
			UserRepository userRepository
			) {
		this.friendsRepository=friendsRepository;
		this.userRepository = userRepository;
	}
	
	
	public List<Friends> getFriendsByUserName(String username_){
		List<Friends> friends = friendsRepository.findByUser1(username_);
		if(friends.isEmpty()) {
			System.out.println("Friend Service: getFriendsByUsername: No data found");
			return new ArrayList<>();
		}
		else {
			return friends;
		}
	}
	
	public Friends addFriendToUser(Friends friendsObj) {
		return friendsRepository.save(friendsObj);
	}
	
	public List<User> searchUserByUsernameSuggestion(String username_){
		List<User> users = userRepository.findByUsernameContainingIgnoreCase(username_);
		if(users.isEmpty()) return new ArrayList<>(); //return empty ArrayList
		else {
			return users;
		}
	}
	
	public Friends deleteFriend(Friends friend) {
		friendsRepository.delete(friend);
		return null;
	}
	
	

}
