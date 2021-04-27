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
	
	public static ObservableList<Attack> attacks=FXCollections.observableArrayList();
	
	public static void loadAllAttacks() {
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
					
					
					attacks.add(aux);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static List<Attack> getAllAttacksAsList() {
		List<Attack> result=new ArrayList();
		
		if(attacks!=null&&attacks.size()>0) {
			for(Attack a: attacks) {
				result.add(a);
			}
		}		
		return result;
	}
	
	public static Attack getAttackById(int id) {
		Attack result=null;
		
		if(attacks!=null) {
			for (int i = 0; i < attacks.size(); i++) {
				if(attacks.get(i).getId()==id) {
					return attacks.get(i);
				}
			}	
		}
		
		return result;	
	}
	
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
				if(attacks.contains(a)) {
					int i=attacks.indexOf(a);
					attacks.set(i, a);
				}
				else {
					attacks.add(a);
				}
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
				attacks.remove(a);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static int getNewId() {
		//calcula el id mas alto de todos y suma 1
		int result=-1;
		if(attacks!=null&&attacks.size()>0) {
			for(Attack a: attacks) {
				if(a.getId()>result) {
					result=a.getId();
				}
			}
			
			result++;
		}
		else{
			return 0;
		}
		return result;
	}
}
