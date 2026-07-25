package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.FriendsNotFoundException;
import com.example.demo.exceptions.IllegalFieldException;
import com.example.demo.model.Friends;
import com.example.demo.model.Group;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.FriendService;
import com.example.demo.service.GroupsService;

@RestController
@RequestMapping("/group")
public class GroupController {
	private final JwtUtil jwtUtil;
	private final GroupsService groupsService;
	private Authentication authentication;
	private FriendService friendService;
	
	public GroupController(GroupsService groupsService, JwtUtil jwtUtil, FriendService friendService) {
		this.jwtUtil = jwtUtil;
		this.friendService=friendService;
		// TODO Auto-generated constructor stub
		this.groupsService=groupsService;
	}
	
	@GetMapping("/check")
	public String check() {
		//System.out.println(authentication.getName());
		return "WORKING";
		
	}
	
	@CrossOrigin(origins = "http://localhost:5173/")
	@GetMapping("/getAllGroups")
	public ResponseEntity<List<Group>> fetchGroupsByUsername(@RequestHeader("Authorization") String authHeader) throws Exception{
		//System.out.println("Group Controller: header : "+authHeader);
		String token = authHeader.substring(7);
		String username = jwtUtil.extractUsername(token);
		//System.out.println("Group Controller: token in request: "+token);
		//System.out.println("Group Controller: username from token: "+username);
		List<Group> groups = groupsService.getGroupByAdmin(username);
		//System.out.println("Group Controller: group entity = "+groups.get(0));
		return ResponseEntity.ok(groups);
	}
	
	@CrossOrigin(origins = "http://localhost:5173/")
	@PostMapping("/saveGroup")
	public ResponseEntity<Group> createGroup(@RequestBody Group group_) {
		String token = jwtUtil.getToken();
		String myUser = jwtUtil.extractUsername(token);
		System.out.println("Group Controller: create Group : details "+group_.getAdmin()+"  "+group_.getGroupName()+" ");
		List<String> members = group_.getMembers();
		for(String str : members) {
			System.out.println("members = "+str);
		}
		members.add(group_.getAdmin());
		//System.out.println("usernmae inside token : "+username);
		group_.setAdmin(myUser);
		Group group = groupsService.createGroup(group_);
		return ResponseEntity.ok(group);
	}

	@CrossOrigin(origins = "http://localhost:5173/")
	@DeleteMapping("/deleteGroup")
	public ResponseEntity<Group> deleteGroup(@RequestBody Long id_){
		Group deletedGroup = groupsService.deleteGroup(id_);  //return group object that is deleted
		//if(str == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
		//else 
		return ResponseEntity.ok(deletedGroup);
	}
	
	@CrossOrigin(origins = "http://localhost:5173/")
	@GetMapping("/getGroupByGroupID")
	public ResponseEntity<Group> fetchGroupById(@RequestParam Long id_){
		String token = jwtUtil.getToken();
		//System.out.println("Token Generated : "+token);		
		//get current username
		String currentUsername = jwtUtil.extractUsername(token);
		//System.out.println("usernmae inside token : "+username);
		Group group = groupsService.getGroupById(id_) ;
		return ResponseEntity.ok(group);
	}
	
	@CrossOrigin(origins = "http://localhost:5173/")
	@PostMapping("/updateGroup")
	public ResponseEntity<Group> addMemberToGroup(@RequestParam Long groupID_, @RequestParam String username_){
		Group group = groupsService.getGroupById(groupID_);
		List<String> members = group.getMembers();
		//check if username is a friend.
		String token = jwtUtil.getToken();
		String myuser = jwtUtil.extractUsername(token);
		//check friendship...
		boolean friendship=false;
		List<Friends> friends = friendService.getFriendsByUserName(myuser);
		for (Friends friend: friends) {
			//System.out.println(friend.getUser1() + "  "+ friend.getUser2());
			if(friend.getUser2().equals(username_)) friendship=true;
		}
		if(friendship) {
			//friend is OK
			//if friend is already a member of the group
			if(members.contains(username_)) {
				//dont add
				throw new IllegalFieldException();
			}
			members.add(username_);
			group.setMembers(members);
			groupsService.createGroup(group);
			return ResponseEntity.ok(group);
		}
		
		else {
			//not friends.
			throw new FriendsNotFoundException();
		}
	}
	
	
	
}

