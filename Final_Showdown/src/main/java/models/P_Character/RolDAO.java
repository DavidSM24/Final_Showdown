package models.P_Character;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

@XmlRootElement(name = "repository")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Rol.class})
public class RolDAO implements Serializable {
	
	@XmlElement(name = "rol")
	private List<Rol> roles;
	private static RolDAO instance_R;

	private RolDAO() {
		super();
		this.roles = new ArrayList(); // crea lista vacía
	}

	private RolDAO(List<Rol> roles) {
		super();
		this.roles = roles;
	}

	public static RolDAO getInstance() {
		if (instance_R == null) {
			instance_R = new RolDAO();
		}
		return instance_R;
	}

	public static RolDAO getInstance(List<Rol> lista) { // carga directamente unos datos en el DAO
		if (instance_R == null) {
			instance_R = new RolDAO(lista);
		}
		return instance_R;
	}

	public void addNewRol(Rol newR) {
		if (newR != null && roles != null)
			this.roles.add(newR);
	}

	public List<Rol> getAllRols() {
		if(roles!=null&&roles.size()>0) {
			return roles;
		}
		return null;
	}
	
	public ObservableList<Rol> OL_getAllRols(){
		ObservableList<Rol> result=null;
		if(roles!=null&&roles.size()>0) {
			result=FXCollections.observableArrayList();
			for(Rol r:roles) {
				result.add(r);
			}
			
		}
		return result;
	}

	public void saveRols() {
		JAXBContext jaxbC;
		Rol[] lista=new Rol[0];
		try {
			if (roles != null && roles.size() > 0) {
				lista = new Rol[roles.size()];
				for (int i = 0; i < lista.length; i++) {
					
					if (roles.get(i) != null) {
						lista[i]=(roles.get(i));
					}
					
				}
			}

			jaxbC = JAXBContext.newInstance(RolDAO.class);
			Marshaller m = jaxbC.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(lista, new File("roles.xml"));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveFile() {
		try {
			FileOutputStream fos = new FileOutputStream(new File("Roles.dat"));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.roles);
			oos.flush();
			oos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadFile(){

		try {
			FileInputStream fi=new FileInputStream("Roles.dat");
			ObjectInputStream oi=new ObjectInputStream(fi);

			roles=(List<Rol>)oi.readObject();
			oi.close();
		} catch (FileNotFoundException e) {
			System.out.println("\n No se ha cargado ningún rol. No hay roles que cargar.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
