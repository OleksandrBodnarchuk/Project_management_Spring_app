package com.javawwa25.app.springboot.group.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.javawwa25.app.springboot.group.UserGroup;
import com.javawwa25.app.springboot.group.dto.SimpleGroupDto;

public interface GroupRepository extends JpaRepository<UserGroup, Long> {

	@Query(value = "select distinct(g.name) as name, g.id as id, count(ug.user_id) as userNumber "
			+ "from user_group g "
			+ "left join users_groups ug on ug.group_id = g.id "
			+ "group by g.id", nativeQuery = true)
	public Set<SimpleGroupDto> getSimpleGroupInfo();
}
