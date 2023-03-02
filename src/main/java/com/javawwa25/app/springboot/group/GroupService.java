package com.javawwa25.app.springboot.group;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.javawwa25.app.springboot.group.dto.SimpleGroupDto;
import com.javawwa25.app.springboot.user.dto.GroupDto;
import com.javawwa25.app.springboot.utils.CommonUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupService {
	private final static Logger LOG = LoggerFactory.getLogger(GroupService.class);

	private final GroupRepository groupRepository;

	public GroupDto save(GroupDto dto) {
		LOG.debug("[" + this.getClass().getSimpleName() + "] - save - called");
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
}
