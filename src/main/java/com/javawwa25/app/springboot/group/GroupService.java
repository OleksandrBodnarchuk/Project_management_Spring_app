package com.javawwa25.app.springboot.group;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.user.dto.GroupDto;
import com.javawwa25.app.springboot.utils.CommonUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupService {
	private final GroupRepository groupRepository;

	public GroupDto save(GroupDto dto) {
		UserGroup saved = groupRepository.save(CommonUtils.mapDtoToGroup(dto));
		dto.setId(saved.getId());
		return dto;

	}

	public Set<GroupDto> getAll() {
		return groupRepository.findAll().stream().map(CommonUtils::mapGroupToDto).collect(Collectors.toSet());
	}
}
