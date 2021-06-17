package P_Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.P_Attack.Attack;
import models.P_Attack.Extra;
import models.P_Attack.ExtraDAO;
import utils.Conexion;
import utils.FileUtilities;

public class ScenaryDAO {
	private static final String GETALL= "SELECT id,name,resource, id_user FROM scenary WHERE id_user IN (0,";
											
	private final static String INSERT_UPDATE="INSERT INTO scenary (id, name, resource,id_user) "
			+ "VALUES (?,?,?,?) "
			+ "ON DUPLICATE KEY UPDATE name=?,resource=?,id_user=?";
	private final static String DELETE ="DELETE FROM scenary WHERE id=?";
	
	private final static String NEWID = "SELECT (MAX(id)+1) FROM scenary;";
	
	public static ObservableList<Scenary> scenaries=FXCollections.observableArrayList(); //es stages, pero toma ese nombre para evitar confusion con la clase stage
	
	public static void loadScenaries(int id) {
		Connection con = Conexion.getConexion();
		if (con != null) {
			try {
				
				 scenaries=FXCollections.observableArrayList();
				
				Statement st = con.createStatement();
				ResultSet rs= st.executeQuery(GETALL+id+");");
				while(rs.next()) {
					//es que hay al menos un resultado
					Scenary aux=new Scenary();
					aux.setId(rs.getInt("id"));
					aux.setName(rs.getString("name"));
					aux.setResource(rs.getString("resource"));
					aux.setId_user(rs.getInt("id_user"));
					
					scenaries.add(aux);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static List<Scenary> getAllScenariesAsList() {
		List<Scenary> result=new ArrayList();
		
		if(scenaries!=null&&scenaries.size()>0) {
			for(Scenary s: scenaries) {
				result.add(s);
			}
		}		
		return result;
	}
	
	public static ObservableList<Scenary> getScenariesByName(String name) {
		ObservableList<Scenary> result=FXCollections.observableArrayList();
		if(name!=null&&!name.matches("")) {
			for(Scenary s : scenaries) {
				if(s.getName().toLowerCase().contains(name.toLowerCase())) {
					result.add(s);
				}
			}
			return result;
		}
		else {
			return scenaries;
		}
			
	}
	
	public static void guardar(Scenary s) {
		// INSERT o UPDATE
		//INSERT -> si no existe OK
		//En caso de ERROR -> hago un update
		int rs=0;
		Connection con = Conexion.getConexion();
		
		if (con != null) {
			try {
				PreparedStatement q=con.prepareStatement(INSERT_UPDATE);
				q.setInt(1, s.getId());
				q.setString(2, s.getName());
				q.setString(3, s.getResource());
				q.setInt(4, s.getId_user());
				
				q.setString(5, s.getName());
				q.setString(6, s.getResource());
				q.setInt(7, s.getId_user());
				
				rs =q.executeUpdate();	
				if(scenaries.contains(s)) {
					
					int i=scenaries.indexOf(s);
					scenaries.set(i, s);
				}
				else {
					scenaries.add(s);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void eliminar(Scenary s) {
		int rs=0;
		Connection con = Conexion.getConexion();
		
		if (con != null) {
			try {
				PreparedStatement q=con.prepareStatement(DELETE);
				q.setInt(1, s.getId());
				rs =q.executeUpdate();
				
				FileUtilities.removeFile(s.getResource());
				
				scenaries.remove(s);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static int getNewId() {
		//calcula el id mas alto de todos y suma 1
		int result=-1;
		Connection con = Conexion.getConexion();
		if (con != null) {
			try {
				
				Statement st = con.createStatement();
				ResultSet rs= st.executeQuery(NEWID);
				while(rs.next()) {
					return (rs.getInt("(MAX(id)+1)"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return result;
			}
		}
		return result;
	}
}
