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
import models.P_Character.Character;
import utils.Conexion;
import utils.FileUtilities;

public class AttackDAO extends Attack{

	private static final String GETALL= "SELECT id,name,power,cost,hit_rate,id_extra,photo,animation,media, id_user FROM attack WHERE id_user IN (0,";
	
	private final static String INSERT_UPDATE="INSERT INTO attack (id, name, power,cost,hit_rate,id_extra,photo,animation,media, id_user) "
		+ "VALUES (?,?,?,?,?,?,?,?,?,?) "
		+ "ON DUPLICATE KEY UPDATE name=?,power=?,cost=?,hit_rate=?,id_extra=?,photo=?,animation=?,media=?,id_user=?";
	
	private final static String DELETE ="DELETE FROM attack WHERE id=?";
	
	private final static String NEWID ="SELECT (MAX(id)+1) FROM attack;";
	
	public static ObservableList<Attack> attacks=FXCollections.observableArrayList();
		
	public static void loadAttacks(int id) {
		Connection con = Conexion.getConexion();
		if (con != null) {
			try {
				attacks=FXCollections.observableArrayList();
				Statement st = con.createStatement();
				ResultSet rs= st.executeQuery(GETALL+id+");");
				while(rs.next()) {
					//es que hay al menos un resultado
					Extra e=ExtraDAO.getExtraById(rs.getInt("id_extra"));
					Attack aux=new Attack();
					aux.setId(rs.getInt("id"));
					aux.setName(rs.getString("name"));
					aux.setPower(rs.getInt("power"));
					aux.setCost(rs.getInt("cost"));
					aux.setHit_rate(rs.getInt("hit_rate"));
					aux.setExtra(e);
					aux.setPhoto(rs.getString("photo"));
					aux.setAnimation(rs.getString("animation"));
					aux.setMedia(rs.getString("media"));
					aux.setId_user(rs.getInt("id_user"));
						
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
	
	public static ObservableList<Attack> getAttacksByName(String name) {
		ObservableList<Attack> result=FXCollections.observableArrayList();
		if(name!=null&&!name.matches("")) {
			for(Attack a : attacks) {
				if(a.getName().toLowerCase().contains(name.toLowerCase())) {
					result.add(a);
				}
			}
			return result;
		}
		else {
			return attacks;
		}
			
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
				q.setInt(6, a.getExtra().getId());
				q.setString(7, a.getPhoto());
				q.setString(8, a.getAnimation());
				q.setString(9, a.getMedia());
				q.setInt(10, a.getId_user());
				
				q.setString(11, a.getName());
				q.setInt(12, a.getPower());
				q.setInt(13, a.getCost());
				q.setInt(14, a.getHit_rate());
				q.setInt(15, a.getExtra().getId());
				q.setString(16, a.getPhoto());
				q.setString(17, a.getAnimation());
				q.setString(18, a.getMedia());
				q.setInt(19, a.getId_user());
				
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
				
				if(!a.getPhoto().matches("src/main/resources/images/attacks/adefault.jpg")) {
					FileUtilities.removeFile(a.getPhoto());
				}
				
				if(!a.getMedia().matches("no_resource")) {
					FileUtilities.removeFile(a.getMedia());
					System.out.println("entro?");
				}
				
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
