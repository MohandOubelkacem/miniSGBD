package TP1;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import TP2.BufferManager;
import TP2.Constante;
import TP3.Records;
import TP4.FileManager;
import TP4.Rid;

public class DBManager {
	
	public DBDef dbdef=DBDef.getInstance();
	public BufferManager buffer=BufferManager.getInstance();
	public FileManager fileManager=FileManager.getInstance();
	public DBManager dbmanager=DBManager.getInstance();
	
	
	
	static Rid rid= new Rid();
	
	/** Constructeur privé */
	private DBManager() {
		  
	}

	
	/** Point d'accès pour l'instance unique du singleton DBManager */
	
	   /** Holder */
	 private static class DBManagerHolder
	    {       
	        /** Instance unique non préinitialisée */
	        private final static DBManager instance = new DBManager();
	    }
	 
	    /** Point d'accès pour l'instance unique du singleton DBManager */
	 public static DBManager getInstance()
	   {
	        return DBManagerHolder.instance;
	   }
	 
	 public void Init() {
		 dbdef.Init();
		 fileManager.Init();
	 }
	 
	 public void Finish() throws IOException {
		 dbdef.Finish();
		 buffer.flushAll();
	 }
	 
	 public void ProcessCommand(String[] commande) throws IOException {
		 
		 if(commande[0].equals("create")){
			creat(commande);
			 
		 }
		 
		 else if(commande[0].equals("insert")) {
			
			 insert(commande);
		 }
		 else if(commande[0].equals("clean")) {
			 supprimer();
		 }
		 else if(commande[0].equals("fill")) {
			 fill(commande);
		 }
		 else if(commande[0].equals("selectall")) {
			 selectall(commande);
		 }
		 else if(commande[0].equals("select")) {
			 select(commande);
		 }
		 else if(commande[0].equals("join")) {
			 join(commande);
		 }
	 }
	 
	 public void CreatRelation(String nomRelation,int nombreColonne,ArrayList<String> typesDesColonnes) throws IOException {
		 	
		 	
		 RelDef relDef=new RelDef();
		  
		 	relDef.setNombreColonne(nombreColonne);
		 	relDef.setNomRelation(nomRelation);
		 	relDef.setTypeColonne(typesDesColonnes);
		 	
		 	//// on calcule les recordSize et le slotCount 
		 	
		 	////  calcule le recordSize 
		 	int somme = 0 ;
		 	for (int i = 0; i < relDef.getTypeColonne().size(); i++) {
		 		if(relDef.getTypeColonne().get(i).contains("string")) {
		 			String [] tabString = relDef.getTypeColonne().get(i).split("string");
		 		
		 				
			 			int valeur= Integer.valueOf(tabString[1]);
			 			
			 			 somme = somme +(valeur*2);
		 			
		 		}
		 		else {
		 			switch(relDef.getTypeColonne().get(i)) {
		 				case "int" :
		 					somme=somme+4;
		 					break;
		 				case "float" :
		 					somme = somme+4;
		 					break;
		 			
		 		}
		 		}
				
			}
			
		 	    
		 	    
		 	///// calcule le slotCount
		 		
		 		int slotCount =(int)(Constante.PageSize/somme);
		 		//int recordSize=(Constante.PageSize-slotCount)/somme;
		 		int bytmap= slotCount/somme;
		 		slotCount=slotCount-bytmap;
		 		relDef.setRecordSize(somme);
		 		 
		 	    relDef.setSlotCount(slotCount);
		 	  
		 	  /////// modifier la valeur de filedIdx 

		 	   
		
		 	    //tp4 a les brobros
		 	   relDef.setFiledIdx(dbdef.getCompteur());
		 	    dbdef.addRellation(relDef);
		 	 

		 	    fileManager.createNewHeapFile(relDef);
		 	    
	 }
	 public void creat(String[] commande) throws IOException {
	
		 String nomRelation=commande[1];
		 
		 int nombreDeColonne=Integer.valueOf(commande[2]);
		 ArrayList<String> array = new ArrayList<String>();
		 String nomTypeColonne;
		
		
		 for(int i=0;i<nombreDeColonne;i++) {
			 
			 nomTypeColonne=commande[3+i];
			 array.add(nomTypeColonne);
			 
			 
		 }
		 CreatRelation(nomRelation,nombreDeColonne,array);
	 }
	 
	 public void insert(String[] commande) throws IOException {
		 
		
		 String nomRelation=commande[1];
		 
		
		 Records record=new Records();
		 for(int i=2;i<commande.length;i++) {
		 record.setValue(commande[i]);
		 }
		 FileManager fileManager=FileManager.getInstance();
		
		fileManager.insertRecordInRelation( nomRelation, record);
		  
		 
	 }
	 public void supprimer() throws IOException {
		 File repertoire = new File(Main.cheminRelatif);
		 File[] files=repertoire.listFiles();
		 for(int i = 0 ;i<files.length ;i++) {
			 files[i].delete();
		 }
		 Clean();
	 }
	 public  void Clean() throws IOException {
		 buffer.reset();
		 fileManager.reset();
		 dbdef.reset();
		 
	 }
	 public void fill(String [] commande) throws IOException {
	
		 String nomRelation=commande[1];
		
		 String nomfichiercsv=commande[2]; 
		 File file = new File(Main.cheminRelatif2+nomfichiercsv);//"C:\\Users\\gaya\\Desktop\\Projet\\"
	     
		 List<String> lines=new ArrayList<String>();
	     FileReader fr = new FileReader(file);
	     BufferedReader br = new BufferedReader(fr);
	    
	     /*lit chaque ligne du fichier et l insert dans la liste*/
	     String line;
	     while(( line = br.readLine())!=null) {
	    	 lines.add(line);
	    	 
	     }
	     
	   /*  for (String line = br.readLine(); line != null; line = br.readLine()) {
	    	 	
	    	 lines.add(line);
	     }
	     */
	     	
	        /*sépare chaque ligne avec une vergule et mets chaque mot dans le record */
	        String sep = ",";
	        FileManager fileManager=FileManager.getInstance();
	        ArrayList<Records> array =new ArrayList<Records>();
	        
	       for(int i=0;i<lines.size();i++) {
	    	   Records record = new Records();
	            String[] oneData = lines.get(i).split(sep);
	            for(int j=0; j< oneData.length;j++) {
	            	record.setValue(oneData[j]);
	            }    
	           array.add(record);
	          
	        }
	       
	        for(int k=0;k<array.size();k++) {
	       /*on met le record  dans la relation*/
	        	fileManager.insertRecordInRelation( nomRelation, array.get(k));
	        }
   	     br.close();
   	     fr.close();
	 }
	 public void  selectall(String[] commande) throws IOException {
		 
		 String nomRelation=commande[1];
		 List<Records> array =new ArrayList<Records>();
		 array.addAll(fileManager.getAllRecords(nomRelation));
		
		
		 for(int i=0;i<array.size();i++) {
			 
			 for(int j=0;j<array.get(i).getValue().size();j++) {
			 System.out.print(array.get(i).getValue().get(j).toString()+" - ");
			
			 }
			 System.out.println();
			
			
		}
		 
		 System.out.println("Total records "+array.size());
	 }
	 
	 public void select(String[] commande) throws IOException {

		 String nomRelation=commande[1];
		 int colonne = Integer.valueOf(commande[2])-1;
		 String valeur = commande[3];
		 List<Records> array =new ArrayList<Records>();
		 array=fileManager.getAllRecordsWithFilter(nomRelation, colonne , valeur);
		
		 for(int i=0 ;i<array.size();i++) {
			 System.out.print(" - "+array.get(i).getValue().toString());
			 System.out.println();
		 }
		 System.out.println("Total records "+array.size());
	 }
	 
	 public void join(String[] commande) throws IOException {
		 String nomRelation1=commande[1];
		 String nomRelation2=commande[2];
		 int indice_colonne1 =Integer.valueOf( commande[3])-1;
		 
		 int indice_colonne2 =Integer.valueOf(commande[4])-1;
		 List<Records> array =new ArrayList<Records>();
		 array=fileManager.join(nomRelation1,nomRelation2,indice_colonne1,indice_colonne2);
		 
		 for(int i=0 ;i<array.size();i++) {
			 System.out.print(array.get(i).getValue().toString()+" - ");
			 System.out.println();
		 }
		 System.out.println("Total records "+array.size());
	 }
	 
	 
	 
	 }