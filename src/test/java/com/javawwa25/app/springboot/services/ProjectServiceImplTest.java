package com.javawwa25.app.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.ProjectRepository;
import com.javawwa25.app.springboot.web.dto.ProjectDto;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {
	
	@Mock
	private Model model;
	@Mock
	private UserService userService;
	@Mock
	private ProjectRepository projectRepository;
	@InjectMocks
	private ProjectServiceImpl underTest;
	
	private List<Project> projects = new ArrayList<>();
	private User user = new User();
	
	@BeforeEach
	public void setUp() {
		user.setId(1L);
		Project javaProject = Project.builder()
				.startDate(LocalDate.now())
				.name("Java")
				.info("Description")
				.build();
		javaProject.setId(1L);
		javaProject.setUsers(Set.of(user));
		
		Project pythonProject = Project.builder()
				.startDate(LocalDate.now())
				.name("Python")
				.info("Description")
				.build();
		pythonProject.setId(1L);
		pythonProject.setUsers(Set.of(user));
		user.addProject(javaProject);
		user.addProject(pythonProject);
		
		projects.addAll(List.of(javaProject, pythonProject));
	}

	@Test
	void testGetAllProjects() {
		given(projectRepository.findAll()).willReturn(projects);
		List<Project> allProjects = underTest.getAllProjects();
		assertEquals(projects.size(), allProjects.size());
	}

	@Test
	void testSave() {
		underTest.save(ProjectDto.builder().build());
		verify(projectRepository, times(1)).save(any());
	}

	@Test
	void testGetProjectById() {
		given(projectRepository.findById(anyLong())).willReturn(Optional.of(projects.get(1)));
		Project project = underTest.getProjectById(1L);
		verify(projectRepository, times(1)).findById(anyLong());
		assertNotNull(project);
	}
	
	@Test
	void testGetProjectById_throws_error() {
		long id = 1L;
		given(projectRepository.findById(anyLong())).willReturn(Optional.empty());
		Exception exception = assertThrows(RuntimeException.class, () -> {
			underTest.getProjectById(id);
		});
		String expectedMessage = "Project not found for id : " + id;
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testDeleteProjectById() {
		underTest.deleteProjectById(1L);
		verify(projectRepository, times(1)).deleteById(anyLong());
	}

//	@Test
//	void testGetAllProjectsByUserId() {
//		given(projectRepository.findByUsers_Id(anyLong())).willReturn(projects);
//		List<Project> retrievedProjects = underTest.getAllProjectsByUserId(user.getId());
//		assertEquals(projects.size(), retrievedProjects.size());
//		assertTrue(retrievedProjects.get(0).getUsers().contains(user));
//	}
//
//	@Test
//	public void testFillDtoProjectsModel() {
//		given(userService.getLoggedUser()).willReturn(user);
//		underTest.fillDtoProjectsModel(model);
//		verify(userService, times(1)).getLoggedUserDto();
//		verify(model, times(2)).addAttribute(any(),any());
//		verify(projectRepository,times(1)).findByUsers_Id(anyLong());
//	}
//
//	@Test
//	public void testFillAllProjectsForAdmin() {
//		underTest.fillAllProjectsForAdmin(model);
//		verify(userService, times(1)).getLoggedUserDto();
//		verify(model, times(2)).addAttribute(any(),any());
//	}

}
