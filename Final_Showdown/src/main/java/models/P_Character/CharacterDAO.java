package models.P_Character;

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
import models.P_Attack.AttackDAO;
import utils.Conexion;

public class CharacterDAO {
	
	public static ObservableList<Character> charas=FXCollections.observableArrayList();
	
	private static final String GETALL= "SELECT id, name AS Nombre, universe AS Universo, description AS Descripcion, bando, hp as HP, "
			+ "energy_ini AS \"Energia Inicial\", energy_restore as Restauracion, atk, def, spe, id_attack_1 as A1 , "
			+ "id_attack_2 as A2, id_attack_3 as A3, id_rol as Rol, photo_face as Face, photo_card as Carta "
			+ "from chara;";
	
	private final static String INSERT_UPDATE="INSERT INTO chara (id, name, universe, description, hp, energy_ini, energy_restore, atk, "
			+ "def, spe, id_attack_1, id_attack_2, id_attack_3, id_rol, photo_face, photo_card) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "
			+ "ON DUPLICATE KEY UPDATE name=?,universe=?,description=?,bando=?,hp=?,energy_ini=?,energy_restore=?,atk=?,def=?,spe=?,id_attack_1=?, "
			+ "id_attack_2=?,id_attack_3=?, id_rol=?,photo_face=?,photo_card=?";
	
	private final static String DELETE ="DELETE FROM chara WHERE id=?";
	
	public static void loadAllCharacters() {
		Connection con = Conexion.getConexion();
		if (con != null) {
			try {
				Statement st = con.createStatement();
				ResultSet rs= st.executeQuery(GETALL);
				while(rs.next()) {
					//es que hay al menos un resultado
					Attack a1=AttackDAO.getAttackById(rs.getInt("A1"));
					Attack a2=AttackDAO.getAttackById(rs.getInt("A2"));
					Attack a3=AttackDAO.getAttackById(rs.getInt("A3"));
					Rol r=RolDAO.getRolById(rs.getInt("Rol"));
					Character aux=new Character();
					
					aux.setId(rs.getInt("Id"));
					aux.setName(rs.getString("Nombre"));
					aux.setUniverse(rs.getString("Universo"));
					aux.setDescription(rs.getString("Descripcion"));
					aux.setBand(rs.getString("bando"));
					aux.setHp(rs.getInt("HP"));
					aux.setEnergy_ini(rs.getInt("Energia Inicial"));
					aux.setEnergy_recover(rs.getInt("Restauracion"));
					aux.setAtk(rs.getInt("atk"));
					aux.setDef(rs.getInt("def"));
					aux.setSpe(rs.getInt("spe"));
					aux.setA1(a1);
					aux.setA2(a2);
					aux.setA3(a3);
					aux.setRol(r);
					aux.setPhoto_face(rs.getString("Face"));
					aux.setPhoto_card(rs.getString("Carta"));

					charas.add(aux);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void guardar(Character c) {
		// INSERT o UPDATE
				//INSERT -> si no existe OK
				//En caso de ERROR -> hago un update
				int rs=0;
				Connection con = Conexion.getConexion();
				
				if (con != null) {
					try {
						PreparedStatement q=con.prepareStatement(INSERT_UPDATE);
						q.setInt(1, c.getId()); 
						q.setString(2, c.getName());
						q.setString(3, c.getUniverse());
						q.setString(4, c.getDescription()); 
						q.setString(5, c.getBand());
						q.setInt(6, c.getHp()); 
						q.setInt(7, c.getEnergy_ini()); 
						q.setInt(8, c.getEnergy_recover()); 
						q.setInt(9, c.getAtk());
						q.setInt(10, c.getDef());
						q.setInt(11, c.getSpe());
						q.setInt(12, c.getA1().getId());
						q.setInt(13, c.getA2().getId());
						q.setInt(14, c.getA3().getId());
						q.setInt(15, c.getRol().getId());
						q.setString(16, c.getPhoto_face());
						q.setString(17, c.getPhoto_card());
						
						q.setString(18, c.getName()); 
						q.setString(19, c.getUniverse());
						q.setString(20, c.getDescription());
						q.setString(21, c.getBand());
						q.setInt(22, c.getHp()); 
						q.setInt(23, c.getEnergy_ini());
						q.setInt(24, c.getEnergy_recover());
						q.setInt(25, c.getAtk());
						q.setInt(26, c.getDef());
						q.setInt(27, c.getSpe());
						q.setInt(28, c.getA1().getId());
						q.setInt(29, c.getA2().getId());
						q.setInt(30, c.getA3().getId());
						q.setInt(31, c.getRol().getId());
						q.setString(32, c.getPhoto_face());
						q.setString(33, c.getPhoto_card());
						
						rs =q.executeUpdate();	
						if(charas.contains(c)) {
							int i=charas.indexOf(c);
							charas.set(i, c);
						}
						else {
							charas.add(c);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	}
	
	public static void eliminar(Character c) {
		int rs=0;
		Connection con = Conexion.getConexion();
		
		if (con != null) {
			try {
				PreparedStatement q=con.prepareStatement(DELETE);
				q.setInt(1, c.getId());
				rs =q.executeUpdate();
				charas.remove(c);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
