package projects.service;

import projects.dao.ProjectDao;
import projects.entity.Project;

public class ProjectService {
	// instance variable
	private ProjectDao projectDao = new ProjectDao();
	
	// method adds a project in database
	public Project addProject(Project project) {
		
		return projectDao.insertProject(project);
		
	}

	
	
}
