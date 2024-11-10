package projects.dao;
// imports and class declaration
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import projects.entity.Project;
import projects.exception.DbException;
import provided.util.DaoBase;

// these constants hold the names of the database tables
@SuppressWarnings("unused")
public class ProjectDao extends DaoBase {
	private static final String CATEGORY_TABLE = "category";
	private static final String MATERIAL_TABLE = "material";
	private static final String PROJECT_TABLE = "project";
	private static final String PROJECT_CATEGORY_TABLE = "project_category";
	private static final String STEP_TABLE = "step";

// method & SQL query
	public Project insertProject(Project project) {
	//@formatter:off
		String sql = ""
				+ "INSERT INTO " + PROJECT_TABLE + " "
				+ "(project_name, estimated_hours, actual_hours, difficulty, notes) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?)"; // place holders
	//@formatter:on
		
// connection to database by calling DbConnection.getConnection, method from another class		
		try (Connection conn = DbConnection.getConnection()) {
			startTransaction(conn); //  starts database transaction, ensures data integrity
			
		// prepares SQL query that'll be executed in database	
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				setParameter(stmt, 1, project.getProjectName(), String.class);
				setParameter(stmt, 2, project.getEstimatedHours(), BigDecimal.class);
				setParameter(stmt, 3, project.getActualHours(), BigDecimal.class);
				setParameter(stmt, 4, project.getDifficulty(), Integer.class);
				setParameter(stmt, 5, project.getNotes(), String.class);
				
				stmt.executeUpdate();
				
				Integer projectId = getLastInsertId(conn, PROJECT_TABLE);
				commitTransaction(conn); // changes to database are saved permanently
				
				project.setProjectId(projectId);
				return project;
			}
		// error handling, transaction is rolled back if any exception occurs
			catch (Exception e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
		}
	// if there's a problem connecting to the database, it is caught	
		catch (SQLException e) {
			throw new DbException(e);
		}
	}
	
	
}
