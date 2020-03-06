package TP4;

import java.io.IOException;
import java.util.ArrayList;

import TP1.Main;
import TP1.RelDef;
import TP2.PageId;
import TP3.HeapFile;
import TP3.Records;
public class FileManager {
	private ArrayList<HeapFile> ListHeapFiles;
	
private FileManager() {
	ListHeapFiles =  new ArrayList<HeapFile>();
	}

	
	/** Point d'accès pour l'instance unique du singleton DBManager */
	
	   /** Holder */
	 private static class FileManagerHolder
	    {       
	        /** Instance unique non préinitialisée */
	        private final static FileManager instance = new FileManager();
	    }
	 
	    /** Point d'accès pour l'instance unique du singleton DBManager */
	 public static FileManager getInstance()
	   {
	        return FileManagerHolder.instance;
	   }
	 
	
	
	
	public ArrayList<HeapFile> getListHeapFiles() {
		return ListHeapFiles;
	}

	public void setListHeapFiles(ArrayList<HeapFile> listHeapFiles) {
		ListHeapFiles = listHeapFiles;
	}
	
	
	
	public void Init() {
		for(int i=0 ; i<Main.dbManager.dbdef.getListe().size();i++) {
			
			HeapFile heapFile=new HeapFile(Main.dbManager.dbdef.getListe().get(i));
		
			ListHeapFiles.add(heapFile);
				
		}
	}
	
	public void createNewHeapFile(RelDef iRelDef) throws IOException {
		HeapFile heapFile=new HeapFile(iRelDef);
		//heapFile.setReldef(iRelDef);
	
		ListHeapFiles.add(heapFile);
		heapFile.createNewOnDisk();
	}
	
	public Rid insertRecordInRelation(String iRelationName,Records iRecord) throws IOException
	{
		Rid rid=new Rid();
		
		
		for(int i=0;i<ListHeapFiles.size();i++) {
			if(ListHeapFiles.get(i).getReldef().getNomRelation().equals(iRelationName)) {
					rid=ListHeapFiles.get(i).insertRecord(iRecord);
					return rid;
			}
		}
		
	return null;
		
	}
	
	public void reset() {
		ListHeapFiles.clear();
	}
	
	public ArrayList<Records> getAllRecords( String iRelationName) throws IOException 
	{
	  ArrayList<Records> listRecords=new ArrayList<Records>();
	  ArrayList<PageId> listPageId=new ArrayList<PageId>();
	 
	  
	   for(int i=0;i<ListHeapFiles.size();i++) {
	
		   
		
			if(ListHeapFiles.get(i).getReldef().getNomRelation().equals(iRelationName) ) {
			
			
			listPageId=ListHeapFiles.get(i).getDataPagesIds();
			
			
			for(int j=0;j<listPageId.size();j++) {
				
				
						
				listRecords.addAll(ListHeapFiles.get(i).getRecordsOnPage(listPageId.get(j)));
				
				
				
			}
			
			
			}
		
	   }
	 
	   return listRecords;
	
}
	
	public ArrayList<Records> getAllRecordsWithFilter(String iRelationName,int iIdxCol,String iValeur) throws IOException{
	    ArrayList<Records> listRecords=new ArrayList<Records>();
		ArrayList<Records>  list=new ArrayList<Records> ();
		 list=getAllRecords(iRelationName);
			
	
		 for(int i=0;i<list.size();i++)
			 
		 {   
			 
			
			 if(list.get(i).getValue().get(iIdxCol).equals(iValeur)) {
				
					Records record= list.get(i);
					listRecords.add(record);
				
			 }
				
			 
		 }
			
		
		      
		
		 return listRecords;
	}
	

	public ArrayList<Records> join(String nomRelation1,String nomRelation2,int indice_colonne1,int indice_colonne2 ) throws IOException {
		 ArrayList<PageId> listPage=new ArrayList<PageId>();
		 ArrayList<PageId> listPage1=new ArrayList<PageId>();
		 HeapFile b=null;
		 HeapFile c=null;
		 ArrayList<Records> listRecords=new ArrayList<Records>();
		 
		 for(int i=0;i<ListHeapFiles.size();i++) {
				
			   
				
				if(ListHeapFiles.get(i).getReldef().getNomRelation().equals(nomRelation1)) {
				     
				b=ListHeapFiles.get(i);
				listPage=ListHeapFiles.get(i).getDataPagesIds();
			
				}
				if(ListHeapFiles.get(i).getReldef().getNomRelation().equals(nomRelation2)) {
					
					c=ListHeapFiles.get(i);
					listPage1=ListHeapFiles.get(i).getDataPagesIds();
					
					}
		 }
		 
		 for(int j=0;j<listPage.size();j++) {
			 ArrayList<Records> re=b.getRecordsOnPage(listPage.get(j));
		
			 for(int k=0;k<listPage1.size();k++){
				
				 ArrayList<Records> r=c.getRecordsOnPage(listPage1.get(k));
				 
				 
					for(int l=0;l<re.size();l++) {
					
					
						for(int z=0;z<r.size();z++) {
							
								if( (re.get(l).getValue().get(indice_colonne1)).equals(r.get(z).getValue().get(indice_colonne2))){
										
										Records record= new Records();
										record.setValue(re.get(l).getValue().get(indice_colonne1));
										listRecords.add(record);
								}
						}
					 }
			
			 }

		}

		 return listRecords;
		
				 
	}
	
}
