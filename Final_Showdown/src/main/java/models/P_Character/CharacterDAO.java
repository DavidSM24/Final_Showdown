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
	
	private static final String GETALL= "SELECT id, name AS Nombre, universe AS Universo, description AS Descripcion, hp as HP, "
			+ "energy_ini AS \"Energia Inicial\", energy_restore as Restauracion, atk, def, spe, id_attack_1 as A1 , "
			+ "id_attack_2 as A2, id_attack_3 as A3, id_rol as Rol, photo_face as Face, photo_card as Carta "
			+ "from chara;";
	
	private final static String INSERT_UPDATE="INSERT INTO chara (id, name, universe, description, hp, energy_ini, energy_restore, atk, "
			+ "def, spe, id_attack_1, id_attack_2, id_attack_3, id_rol, photo_face, photo_card) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "
			+ "ON DUPLICATE KEY UPDATE name=?,universe=?,description=?,hp=?,energy_ini=?,energy_restore=?,atk=?,def=?,spe=?,id_attack_1=?, "
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
						q.setInt(5, c.getHp()); 
						q.setInt(6, c.getEnergy_ini()); 
						q.setInt(7, c.getEnergy_recover()); 
						q.setInt(8, c.getAtk());
						q.setInt(9, c.getDef());
						q.setInt(10, c.getSpe());
						q.setInt(11, c.getA1().getId());
						q.setInt(12, c.getA2().getId());
						q.setInt(13, c.getA3().getId());
						q.setInt(14, c.getRol().getId());
						q.setString(15, c.getPhoto_face());
						q.setString(16, c.getPhoto_card());
						
						q.setString(17, c.getName()); 
						q.setString(18, c.getUniverse());
						q.setString(19, c.getDescription());
						q.setInt(20, c.getHp()); 
						q.setInt(21, c.getEnergy_ini());
						q.setInt(22, c.getEnergy_recover());
						q.setInt(23, c.getAtk());
						q.setInt(24, c.getDef());
						q.setInt(25, c.getSpe());
						q.setInt(26, c.getA1().getId());
						q.setInt(27, c.getA2().getId());
						q.setInt(28, c.getA3().getId());
						q.setInt(29, c.getRol().getId());
						q.setString(30, c.getPhoto_face());
						q.setString(31, c.getPhoto_card());
						
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
