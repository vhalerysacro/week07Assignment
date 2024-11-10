package projects;
// import statements
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {
	// main method, entry point of program
	public static void main(String[] args) {
		 new ProjectsApp().processUserSelections();
	
	}
	// instance variables
	private ProjectService projectService = new ProjectService();
	private Scanner scanner = new Scanner (System.in);
	// @formatter:off
	private List<String> operations = List.of (
					"1) Add a project"
			);
	// @formatter:on
			
	
	// method, runs a loop showing user available options and selection
	private void processUserSelections() {
		boolean done = false;
		while(!done) {
			try {
				int selection = getUserSelection();
				
				switch(selection) {
				case -1:
					done = exitMenu();
					break;
					
				case 1:
					createProject();
					break;
				
				default:
					System.out.println("\n" + selection + " is not a valid selection. Try again.");
					break;
				}
			} 
			// catches exceptions when there's an error in user input
			catch(Exception e) {
				System.out.println("\nError: " + e + " Try again.");
			}
		
		}
		
	}
	// method prints exiting menu, return true that stops loops in method above that ends program
	private boolean exitMenu() {
		System.out.println("Exiting the menu.");
		return true;
	}
	// a method that prompts user to enter information about the project
	private void createProject() {
		String projectName = getStringInput("Enter project name");
		BigDecimal estimatedHours = getDecimalInput("Enter estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficultry(1-5)");
		String notes = getStringInput("Enter the project notes");
		
		Project project = new Project(); // then creates new project
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project dbProject = projectService.addProject(project); // saves project
		System.out.println("You have successfully created project: " + dbProject);
	}
	// method to get decimal input, uses BigDecimal to handle decimal numbers accurately
	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		try {
			return new BigDecimal(input).setScale(2);
		}
		// catches exceptions when there's an error in user input
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number.");
		}
	}

	// method displaying menu options and then asking for a menu selection
	private int getUserSelection() {
		printOperations();
		
		Integer input = getIntInput("Enter a menu selection");
		
		return Objects.isNull(input) ? - 1: input;
	}
	// method getting an integer input from user
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		 if(Objects.isNull(input)) {
			 
		 return null;
		}
		 
		 try {
			 return Integer.valueOf(input);	
		 }
		// catches exceptions when there's an error in user input
		 catch(NumberFormatException e){
			throw new DbException(input + " is not a valid number. Try again.");
		 }
		
	}
	// method asking user for a string, returns null if empty string is returned
	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String input = scanner.nextLine();
		
		return input.isBlank() ? null : input.trim();
	}
	// method prints available options on the menu
	private void printOperations() {
		System.out.println("\nThese are the available selections. Press Enter key to quit:");
		
		operations.forEach(line -> System.out.println(" " + line));
	}

}
