package com.example.demo.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Friends;
import com.example.demo.model.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.FriendService;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/friend")
public class FriendController {
	private final FriendService friendService;
	private final UserService userService;
	private final JwtUtil jwtUtil;
	
	public FriendController(
			FriendService friendService,
			UserService userService,
			JwtUtil jwtUtil
			) {
		this.userService=userService;
		this.friendService=friendService;
		this.jwtUtil=jwtUtil;
		
	}
	
	
	@GetMapping("/check")
	public String check() {
		return "WORKING";
	}
	
	
	//Enter correct friendName and friend is added to the user.
	//Entry is made in Friends table
	@CrossOrigin(origins = "http://localhost:5173/")
	@PostMapping("/addFriend")
	public ResponseEntity<Friends> addFriend(@RequestBody Friends friend_){ // resolve this
		System.out.println("Friend Controller: Add Friend Reached");
		String token = jwtUtil.getToken();
		String myName = jwtUtil.extractUsername(token);
		friend_.setUser1(myName);
		String part1 = friend_.getUser1();
		String part2 = friend_.getUser2();
//		System.out.println("part1 = "+part1+" part 2 : "+part2);
		if(part2.equals(myName)){	
			System.out.println("FriendName = MyName = Error");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
		List<Friends> list = friendService.getFriendsByUserName(myName);
		ArrayList<String> listOfFriends = new ArrayList<>(); 
		for(Friends item: list) {
			listOfFriends.add(item.getUser2());
		}
//		for(String str: listOfFriends) {
//			System.out.println(""+str);
//		}
		
		if(listOfFriends.contains(part2)) {
			System.out.println("Friend Controller: friend already exists");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE ).build();
		}
		
		Friends friendObj = new Friends(part1, part2);
		Friends friendObj2 = new Friends(part2, part1);
		friendService.addFriendToUser(friendObj);
		friendService.addFriendToUser(friendObj2);
//		System.out.println("user1: = "+friendObj.getUser1());
//		System.out.println("user2: = "+friendObj.getUser2());
		return ResponseEntity.ok(friendObj);
	}
	
	
	//Fetch My friends from Friends table
	@CrossOrigin(origins = "http://localhost:5173/")
	@GetMapping("/fetchFriends")
	public ResponseEntity<List<Friends>> getMyFriends() {
		//System.out.println("Freind Controller: GetFriendsById reached");
		String token = jwtUtil.getToken();
		String myName = jwtUtil.extractUsername(token);
		List<Friends> list = friendService.getFriendsByUserName(myName);
		return ResponseEntity.ok(list) ;
	}
	
	
	//Search Friends from Users table to Add as my friend.
	@CrossOrigin(origins = "http://localhost:5173/")
	@PostMapping("/findNewFriends")
	public ResponseEntity<List<User>> findNewFriends(@RequestParam String query_){
		List<User> suggestedUsers = friendService.searchUserByUsernameSuggestion(query_); //returns list of users
		
		String token = jwtUtil.getToken();
		String myName = jwtUtil.extractUsername(token);
		List<Friends> alreadyFriends = friendService.getFriendsByUserName(myName);
		//this filter is here so that my username is not included in list of friends
		suggestedUsers = suggestedUsers.stream().filter(user -> !user.getUsername().equals(myName)).toList();
		//this filter for already friends cannot be added to friends again
		for(Friends friend: alreadyFriends) {
			suggestedUsers = suggestedUsers.stream().filter(x -> !x.getUsername().equals(friend.getUser2())).toList();
		}
		return ResponseEntity.ok(suggestedUsers);
	}
	@CrossOrigin(origins = "http://localhost:5173/")
	@DeleteMapping("/deleteFriend")
	public ResponseEntity<Friends> deleteFriend(@RequestParam("friendName_") String friendName_){
		System.out.println("Friend Controller: delete Friend: friendName = "+friendName_);
		String token = jwtUtil.getToken();
		String myName = jwtUtil.extractUsername(token);
		String deleteThis="";
		List<Friends> alreadyFriends = friendService.getFriendsByUserName(myName);
		for(Friends friend: alreadyFriends) {
			if(friend.getUser1().equals(myName) && friend.getUser2().equals(friendName_)) {
				System.out.println("Friend Controller: Delete Friend: Friendship Found");
				deleteThis=friendName_;
			}
		}
		friendService.deleteFriend(new Friends(myName, deleteThis));
		friendService.deleteFriend(new Friends(deleteThis, myName));
		return null;
	}

	
	
	

}
