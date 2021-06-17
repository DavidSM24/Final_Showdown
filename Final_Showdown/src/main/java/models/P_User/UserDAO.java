package models.P_User;

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
import models.P_Character.Character;
import utils.Conexion;

public class UserDAO {
public static ObservableList<User> users= FXCollections.observableArrayList();
	
	private static final String GETALL="SELECT id,mail,username,password,usercode,confirmed FROM user";
	private final static String INSERT_UPDATE="INSERT INTO user (id, mail, username, password, usercode,confirmed) "
			+ "VALUES (?,?,?,?,?,?) "
			+ "ON DUPLICATE KEY UPDATE mail=?,username=?,password=?,usercode=?,confirmed=?";
	
	public static void loadAllUsers() {
		Connection con = Conexion.getConexion();
		if (con != null) {
			try {
				Statement st = con.createStatement();
				ResultSet rs= st.executeQuery(GETALL);
				while(rs.next()) {
					//es que hay al menos un resultado
					User aux=new User();
					aux.setId(rs.getInt("id"));
					aux.setMail(rs.getString("mail"));
					aux.setUsername(rs.getString("username"));
					aux.setPassword(rs.getString("password"));
					aux.setUsercode(rs.getString("usercode"));
					if(rs.getString("confirmed").matches("yes")) {
						aux.setConfirmed(true);
					}
					else {
						aux.setConfirmed(false);
					}
					
					users.add(aux);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static List<User> getAllUsersAsList() {
		List<User> result=new ArrayList();
		
		if(users!=null&&users.size()>0) {
			for(User u: users) {
				result.add(u);
			}
		}		
		return result;
	}
	
	public static void guardar(User u) {
		// INSERT o UPDATE
		//INSERT -> si no existe OK
		//En caso de ERROR -> hago un update
		int rs=0;
		Connection con = Conexion.getConexion();
		
		if (con != null) {
			try {
				PreparedStatement q=con.prepareStatement(INSERT_UPDATE);
				
				String confirmed="no";
				if(u.isConfirmed()) {
					confirmed="yes";
				}
				
				q.setInt(1, u.getId());
				q.setString(2, u.getMail());
				q.setString(3, u.getUsername());
				q.setString(4, u.getPassword());
				q.setString(5, u.getUsercode());	
				q.setString(6, confirmed);		
				
				q.setString(7, u.getMail());
				q.setString(8, u.getUsername());
				q.setString(9, u.getPassword());
				q.setString(10, u.getUsercode());
				q.setString(11, confirmed);	
				
				
				rs =q.executeUpdate();	
				if(users.contains(u)) {
					int i=users.indexOf(u);
					users.set(i, u);
				}
				else {
					users.add(u);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static User getUserByName(String name){
		User result=null;
		
		for (User u:users) {
			if(u.getUsername().matches(name)) {
				return u;
			}
		}
		
		return result;
	}
	
	public static User getUserByMail(String mail){
		User result=null;
		
		for (User u:users) {
			if(u.getMail().matches(mail)) {
				return u;
			}
		}
		
		return result;
	}
	
	public static User getUserByUserCode(String code){
		User result=null;
		
		for (User u:users) {
			if(u.getUsercode().matches(code)) {
				return u;
			}
		}
		
		return result;
	}
	
	public static int getNewId() {
		//calcula el id mas alto de todos y suma 1
		
		int result=-1;
		if(users!=null&&users.size()>0) {
			for(User u: users) {
				if(u.getId()>result) {
					result=u.getId();
				}
			}
			
			result++;
		}
		else{
			return 0;
		}
		return result;
	}
	
	//hacere el eliminar, tambien borra ataques, escenrios y personajes de este usuario...///////////////////////
}
