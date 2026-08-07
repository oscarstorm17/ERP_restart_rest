package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.exceptions.GroupNotFoundException;
import com.example.demo.exceptions.IllegalFieldException;
import com.example.demo.model.Group;
import com.example.demo.repository.GroupRepository;

@Service
public class GroupsService {
	private final GroupRepository groupRepository;
	public GroupsService(GroupRepository groupRepository) {
		this.groupRepository = groupRepository;
		// TODO Auto-generated constructor stub
	}
	
	//public List<Group> getAllGroups()
	
	public Group getGroupById(Long id_) {
		//Optional<Group> group = groupRepository.findByGroupID(id_);
		//we should not return an empty object but we could return an empty List.  
		//this is good programming behaviour
		return groupRepository.findByGroupID(id_)
				.orElseThrow(()-> new GroupNotFoundException(id_)
				);	
	}
	
	public List<Group> getGroupByAdmin(String username_) {
		List<Group> groups = groupRepository.findByAdmin(username_);
		if(groups.isEmpty()) {
			//throw new GroupNotFoundException(username_) ;
			//its ok to return empty list if conditions not met
			return new ArrayList<>();
		}
		else {
			return groups;
		}
	}
	
	public Group createGroup(Group group_) {// check this
		//list of data checks
		String admin = group_.getAdmin().trim();
		String groupName = group_.getGroupName();
//		System.out.println(//"xxxxx "+group_.getMembers().);
		List<String> members = group_.getMembers();
		for (int i = 0; i < members.size(); i++) {
		    members.set(i, members.get(i));
		    //System.out.println(members.get(i));
		}
		if(admin==null || admin.isBlank() ||
				groupName==null || groupName.isBlank()||
				members.isEmpty()
				) {
			throw new IllegalFieldException();
		}
		else {
			members.add(group_.getAdmin());
			group_.setMembers(members);
			int i=0;
			for(String member : group_.getMembers()) {
				System.out.println("members = "+i+"  "+member);
				i++;
			}
			groupRepository.save(group_);
			return group_;	
		}
	}
	
	public Group deleteGroup(Long id_) {
		Optional<Group> group = groupRepository.findByGroupID(id_);
		if(group.isEmpty()) {
			throw new GroupNotFoundException(id_);
		}
		else {
			//String groupName = group.get().getGroupName();
			groupRepository.deleteById(id_);
			return group.get();
		}
	}
	
	
	
	

}
