package TP1;

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



public class DBDef implements Serializable {

 

 
 
 //// le constructeur est rendu inaccessible 
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<RelDef> liste;
	private int compteur;   
	private DBDef() {
		liste = new ArrayList<RelDef>() ;
		compteur=0;
	}
	  /** Holder */
		 private static class DBDefHolder
		    {       
		        /** Instance unique non préinitialisée */
		        private final static DBDef instance = new DBDef();
		    }
		 
		    /** Point d'accès pour l'instance unique du singleton DBManager */
		 public static DBDef getInstance()
		   {
		        return DBDefHolder.instance;
		   }
 
	
	
 
	public  void addRellation(RelDef relation ) {
		this.liste.add(relation);
	
		compteur++;
   }
		
	 
 
	public List<RelDef> getListe() {
		return  liste;
	}
	public void setListe(ArrayList<RelDef> liste) {
		this.liste = liste;
	}
	public int getCompteur() {
			return compteur;
	}
	public void setCompteur(int compteur) {
			this.compteur = compteur;
	}
	public  void Init() {
		File sauvegarde =new File(Main.cheminRelatif+ "/Catalog.ser");
		if(sauvegarde.exists()) {
		FileInputStream file;
		ObjectInputStream object;
		try {
			file = new FileInputStream(sauvegarde);
			object = new ObjectInputStream(file);
			DBDef dbdeflu = ((DBDef) object.readObject());
			
			this.liste =dbdeflu.liste;
			System.out.println(dbdeflu.compteur);
			this.compteur=dbdeflu.compteur;
			object.close();
			file.close();
		} catch(FileNotFoundException e) {
				e.printStackTrace();
		}
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		
	}
	public  void  Finish()  {
	 
		File sauvegarde =new File(Main.cheminRelatif+ "/Catalog.ser");
		FileOutputStream file;
		ObjectOutputStream object;
		try {
			file = new FileOutputStream(sauvegarde);
			object = new ObjectOutputStream(file);
			object.writeObject(Main.dbManager.dbdef);
			object.close();
			file.close();
		} catch(FileNotFoundException e) {
				e.printStackTrace();
		}
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
 
 
	public void reset() {
		this.liste.clear();
		this.compteur=0;
	}
}