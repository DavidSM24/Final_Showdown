package models.P_Attack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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

public class ExtraDAO{
	
	private static final String GETALL="SELECT id,name,description FROM extra";
	
	public static List<Extra> getAllExtras() {
		List<Extra> result=new ArrayList();
		Connection con = Conexion.getConexion();
		if (con != null) {
			try {
				Statement st = con.createStatement();
				ResultSet rs= st.executeQuery(GETALL);
				while(rs.next()) {
					//es que hay al menos un resultado
					Extra aux=new Extra();
					aux.setId(rs.getInt("id"));
					aux.setName(rs.getString("name"));
					aux.setDescription(rs.getString("description"));
					result.add(aux);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		return result;
	}
	
	public static ObservableList<Extra> getAllObservableExtras() {
		ObservableList<Extra> result=FXCollections.observableArrayList();
		Connection con = Conexion.getConexion();
		if (con != null) {
			try {
				Statement st = con.createStatement();
				ResultSet rs= st.executeQuery(GETALL);
				while(rs.next()) {
					//es que hay al menos un resultado
					Extra aux=new Extra();
					aux.setId(rs.getInt("id"));
					aux.setName(rs.getString("name"));
					aux.setDescription(rs.getString("description"));
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
