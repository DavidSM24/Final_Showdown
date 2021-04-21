package models.P_Attack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Conexion;

public class AttackDAO extends Attack{
	private static final String GETALL= "SELECT id,name,power,cost,hit_rate,id_extra,photo,animation FROM attack;";
	private final static String INSERT_UPDATE="INSERT INTO attack (id, name, power,cost,hit_rate,id_extra,photo,animation) "
		+ "VALUES (?,?,?,?,?,?,?,?) "
		+ "ON DUPLICATE KEY UPDATE name=?,power=?,cost=?,hit_rate=?,id_extra=?,photo=?,animation=?";
	private final static String DELETE ="DELETE FROM attack WHERE id=?";
	
	public static List<Attack> getAllAttacks() {
		List<Attack> result=new ArrayList();
		Connection con = Conexion.getConexion();
		if (con != null) {
			try {
				Statement st = con.createStatement();
				ResultSet rs= st.executeQuery(GETALL);
				while(rs.next()) {
					//es que hay al menos un resultado
					Attack aux=new Attack();
					aux.setId(rs.getInt("id"));
					aux.setName(rs.getString("name"));
					aux.setPower(rs.getInt("power"));
					aux.setCost(rs.getInt("cost"));
					aux.setHit_rate(rs.getInt("hit_rate"));
					aux.setId_extra(rs.getInt("id_extra"));
					aux.setPhoto(rs.getString("photo"));
					aux.setAnimation(rs.getString("animation"));
					
					
					result.add(aux);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		return result;
	}
	
	public static ObservableList<Attack> getAllObservableAttacks() {
		ObservableList<Attack> result=FXCollections.observableArrayList();
		Connection con = Conexion.getConexion();
		if (con != null) {
			try {
				Statement st = con.createStatement();
				ResultSet rs= st.executeQuery(GETALL);
				while(rs.next()) {
					//es que hay al menos un resultado

					Attack aux=new Attack();
					aux.setId(rs.getInt("id"));
					aux.setName(rs.getString("name"));
					aux.setPower(rs.getInt("power"));
					aux.setCost(rs.getInt("cost"));
					aux.setHit_rate(rs.getInt("hit_rate"));
					aux.setId_extra(rs.getInt("id_extra"));
					aux.setPhoto(rs.getString("photo"));
					aux.setAnimation(rs.getString("animation"));
					
					result.add(aux);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		return result;
	}
	
	//save here
	
	public static void guardar(Attack a) {
		// INSERT o UPDATE
		//INSERT -> si no existe OK
		//En caso de ERROR -> hago un update
		int rs=0;
		Connection con = Conexion.getConexion();
		
		if (con != null) {
			try {
				PreparedStatement q=con.prepareStatement(INSERT_UPDATE);
				q.setInt(1, a.getId());
				q.setString(2, a.getName());
				q.setInt(3, a.getPower());
				q.setInt(4, a.getCost());
				q.setInt(5, a.getHit_rate());
				q.setInt(6, a.getId_extra());
				q.setString(7, a.getPhoto());
				q.setString(8, a.getAnimation());
				
				q.setString(9, a.getName());
				q.setInt(10, a.getPower());
				q.setInt(11, a.getCost());
				q.setInt(12, a.getHit_rate());
				q.setInt(13, a.getId_extra());
				q.setString(14, a.getPhoto());
				q.setString(15, a.getAnimation());
				
				rs =q.executeUpdate();		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void eliminar(Attack a) {
		int rs=0;
		Connection con = Conexion.getConexion();
		
		if (con != null) {
			try {
				PreparedStatement q=con.prepareStatement(DELETE);
				q.setInt(1, a.getId());
				rs =q.executeUpdate();
				a=null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
