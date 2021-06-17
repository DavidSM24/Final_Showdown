package P_Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import models.P_User.User;
import utils.Conexion;

public class SesionDAO {
	private static final String ACTIVATEEVENTS = "SET GLOBAL event_scheduler = ON";
	private static final String GETSession = "SELECT id, id_user, time FROM session WHERE session.id_user= ";
	private static final String GETNEWID= "SELECT id FROM session ORDER BY id DESC LIMIT 1";
	private final static String INSERT_UPDATE="INSERT INTO session (id, id_user,time) "
			+ "VALUES (?,?,?) "
			+ "ON DUPLICATE KEY UPDATE id_user=?,time=?";

	public static void activateEvents() {
		Connection con = Conexion.getConexion();
		if (con != null) {
			try {
				
				Statement st = con.createStatement();
				ResultSet rs= st.executeQuery(ACTIVATEEVENTS);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	public static boolean isConnected(User u) {

		boolean result = false;

		Connection con = Conexion.getConexion();
		if (con != null) {
			try {

				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(GETSession + u.getId() + ";");

				Sesion s = new Sesion();
				while (rs.next()) {
					s.setId(rs.getInt("id"));
					s.setId_user(rs.getInt("id_user"));
					s.setTime(rs.getTimestamp("time"));

					System.out.println(s.getTime());

					if (s.getId_user() >=0) {
						result = true;
					}
				}

				return result;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("catch en sessionDao");
				return result;
			}
		}
		return result;
	}
	
	public static void guardar(Sesion s) {
		// INSERT o UPDATE
				//INSERT -> si no existe OK
				//En caso de ERROR -> hago un update
				int rs=0;
				Connection con = Conexion.getConexion();
				
				if (con != null) {
					try {
						PreparedStatement q=con.prepareStatement(INSERT_UPDATE);
						q.setInt(1, s.getId()); 
						q.setInt(2, s.getId_user());
						q.setTimestamp(3, s.getTime());
						
						q.setInt(4, s.getId_user());
						q.setTimestamp(5, s.getTime());
						
						rs =q.executeUpdate();	
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	}
	
	public static int getNewId() {
		
		int result=0;
		
		Connection con = Conexion.getConexion();
		if (con != null) {
			try {

				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(GETNEWID);
				
				while(rs.next()) {
					result=(rs.getInt("id"));
					result++;
					return result;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("catch en sessionDao");
				return 0;
			}
		}
		return 0;
	}

}
