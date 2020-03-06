package TP2;

import java.io.File;

import java.io.IOException;
import java.io.RandomAccessFile;

import TP1.Main;



public class DiskManager {
	
	

	public  File f;
	
	/**constructeur**/
		private DiskManager() {}

	
	/** Point d'accès pour l'instance unique du singleton DBManager */
	
	   /** Holder */
	 private static class DiskManagerHolder
	    {       
	        /** Instance unique non préinitialisée */
	        private final static DiskManager instance = new DiskManager();
	    }
	 
	    /** Point d'accès pour l'instance unique du singleton DBManager */
	 public static DiskManager getInstance()
	   {
	        return DiskManagerHolder.instance;
	   }
	
	 public void creatFile(int iFileIdx) throws IOException {
	
		
		 	f=new File(Main.cheminRelatif+iFileIdx+".rf");
		 	
		 	f.createNewFile();
		 	
	}
	 public void  AddPage(int iFileIdx,PageId oPageId) throws IOException {
		
		
		 RandomAccessFile file = new RandomAccessFile(Main.cheminRelatif+iFileIdx+".rf","rw");
	    byte[] b=new byte[Constante.PageSize];
	   
	    	
	    double  pageidx=((file.length())/Constante.PageSize);
	    int i = (int)((pageidx)*Constante.PageSize);
	   
	    
	    file.seek(i);
	    file.write(b);
	 
	    oPageId.setFileIdx(iFileIdx);	 
	    oPageId.setPageIdx((int)pageidx);
	    file.close();
	    
	    
	 
	    
	  	
	 }
	 
	 public void ReadPage(PageId iPageId,byte[] oBuffer) throws IOException {
		 RandomAccessFile file = new RandomAccessFile(Main.cheminRelatif+iPageId.getFileIdx()+".rf","r");
		 int i = (iPageId.getPageIdx()*Constante.PageSize);
		 file.seek(i);
		 file.read(oBuffer );
		 file.close();
		 
		
	 }
	 
	public void WritePage(PageId iPageId,byte[] iBuffer) throws IOException {
		RandomAccessFile file = new RandomAccessFile(Main.cheminRelatif+iPageId.getFileIdx()+".rf","rw");
	

			
			 int i=iPageId.getPageIdx()*Constante.PageSize;
			 
			 file.seek(i);
			 file.write(iBuffer );
		
		
		 
		 file.close();
	}

	
	 
}
