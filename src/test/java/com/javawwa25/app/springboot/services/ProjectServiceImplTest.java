package com.javawwa25.app.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
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
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import com.javawwa25.app.springboot.models.Project;
import com.javawwa25.app.springboot.models.User;
import com.javawwa25.app.springboot.repositories.ProjectRepository;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {
	
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
		Project project = projects.get(0);
		underTest.save(project);
		verify(projectRepository, times(1)).save(project);
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

	@Test
	void testGetAllProjectsByUserId() {
		given(projectRepository.findAllUserProjects(anyLong())).willReturn(projects);
		List<Project> retrievedProjects = underTest.getAllProjectsByUserId(user.getId());
		assertEquals(projects.size(), retrievedProjects.size());
		assertTrue(retrievedProjects.get(0).getUsers().contains(user));
	}

	@Test
	void testFillUserProjects() {
		Model model = mock(ConcurrentModel.class, "myModel");
		
		given(userService.getUserById(user.getId())).willReturn(user);
		given(projectRepository.findAllUserProjects(anyLong())).willReturn(projects);
		
		underTest.fillUserProjects(user.getId(), model);
		verify(model, times(2)).addAttribute(anyString(), any());
	}

}
