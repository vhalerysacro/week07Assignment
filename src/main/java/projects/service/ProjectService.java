package projects.service;

import java.util.List;
import java.util.NoSuchElementException;
import projects.dao.ProjectDao;
import projects.entity.Project;

public class ProjectService {
	// instance variable
	private ProjectDao projectDao = new ProjectDao();
	
	// method adds a project in database
	public Project addProject(Project project) {
		return projectDao.insertProject(project);
		
	}
	public List<Project> fetchAllProjects() {
		return projectDao.fetchAllProjects();
	}
	
	// methods that returns a project object
	public Project fetchProjectById(Integer projectId) {
		return projectDao.fetchProjectById(projectId).orElseThrow(
				() -> new NoSuchElementException("Project with project ID=" + projectId + " does not exist."));
		
	}

	
	
}
