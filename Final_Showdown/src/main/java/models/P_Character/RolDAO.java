package models.P_Character;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.P_Attack.Extra;
import utils.Conexion;

public class RolDAO{
	
	private static final String GETALL="SELECT id, name, hp_base, atk_base, def_base, spe_base FROM rol";
	
	public static List<Rol> getAllRols() {
		List<Rol> result=new ArrayList();
		Connection con = Conexion.getConexion();
		if (con != null) {
			try {
				Statement st = con.createStatement();
				ResultSet rs= st.executeQuery(GETALL);
				while(rs.next()) {
					//es que hay al menos un resultado
					Rol aux=new Rol();
					aux.setId(rs.getInt("id"));
					aux.setName(rs.getString("name"));
					aux.setHp_base(rs.getInt("hp_base"));
					aux.setAtk_base(rs.getInt("atk_base"));
					aux.setDef_base(rs.getInt("def_base"));
					aux.setSpe_base(rs.getInt("spe_base"));
					result.add(aux);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		return result;
	}
	
	public static ObservableList<Rol> getAllObservableRols() {
		ObservableList<Rol> result=FXCollections.observableArrayList();
		Connection con = Conexion.getConexion();
		if (con != null) {
			try {
				Statement st = con.createStatement();
				ResultSet rs= st.executeQuery(GETALL);
				while(rs.next()) {
					//es que hay al menos un resultado
					Rol aux=new Rol();
					aux.setId(rs.getInt("id"));
					aux.setName(rs.getString("name"));
					aux.setHp_base(rs.getInt("hp_base"));
					aux.setAtk_base(rs.getInt("atk_base"));
					aux.setDef_base(rs.getInt("def_base"));
					aux.setSpe_base(rs.getInt("spe_base"));
					result.add(aux);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		return result;
	}
	
}
