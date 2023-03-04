package com.javawwa25.app.springboot.group.service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.group.UserGroup;
import com.javawwa25.app.springboot.group.dto.GroupUsersForm;
import com.javawwa25.app.springboot.group.dto.SimpleGroupDto;
import com.javawwa25.app.springboot.group.repo.GroupRepository;
import com.javawwa25.app.springboot.user.User;
import com.javawwa25.app.springboot.user.dto.GroupDto;
import com.javawwa25.app.springboot.user.service.UserService;
import com.javawwa25.app.springboot.utils.CommonUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupService {
	private final static Logger LOG = LoggerFactory.getLogger(GroupService.class);

	private final GroupRepository groupRepository;
	private final UserService userService;

	public GroupDto save(GroupDto dto) {
		UserGroup saved = groupRepository.save(CommonUtils.mapDtoToGroup(dto));
		dto.setId(saved.getId());
		return dto;

	}

	public Set<GroupDto> getAll() {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - getAll - called");
		return groupRepository.findAll().stream().map(CommonUtils::mapGroupToDto).collect(Collectors.toSet());
	}

	public Set<SimpleGroupDto> getSimpleGroupInfo() {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - getSimpleGroupInfo - called");
		return groupRepository.getSimpleGroupInfo();
	}

	public GroupDto getGroupById(long groupId) {
		return CommonUtils.mapGroupToDto(
				getByIdOrThrow(groupId));
	}

	public void assignUsers(long id, GroupUsersForm form) {
		if (Objects.nonNull(form) && !form.getUsers().isEmpty()) {
			UserGroup group = getByIdOrThrow(id);
			Set<User> users = form.getUsers().stream().filter(dto -> (dto.getAccountId() > 0))
					.map(dto -> userService.getUserByAccountId(dto.getAccountId()))
					.collect(Collectors.toSet());
			group.setUsers(users);
			groupRepository.save(group);
		}
	}
	
	private UserGroup getByIdOrThrow(long groupId) {
		return groupRepository.findById(groupId)
		.orElseThrow(() -> new RuntimeException("Group id is not valid."));
	}

}
