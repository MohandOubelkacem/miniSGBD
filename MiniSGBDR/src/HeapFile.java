package TP3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;


import TP1.RelDef;
import TP2.BufferManager;

import TP2.DiskManager;
import TP2.PageId;

import TP4.Rid;

public class HeapFile {
	
	private RelDef reldef;
	DiskManager diskManager=DiskManager.getInstance();
	BufferManager bufferManager=BufferManager.getInstance();
	
	boolean b = true;
	 
	public HeapFile(RelDef r) {
		
		this.reldef=r;
	}
	
	public RelDef getReldef() {
		return reldef;
	}

	public void setReldef(RelDef reldef) {
		this.reldef = reldef;
	}
	
	public void createNewOnDisk () throws IOException {
		diskManager.creatFile(reldef.getFiledIdx());
		
		PageId headpage=new PageId(-1,-1);
		
		diskManager.AddPage(reldef.getFiledIdx(),headpage );
		
		
		  byte[] contenueDeLaHeadPages=bufferManager.getPage(headpage);
		 
		HeaderPageInfo head=new HeaderPageInfo();
		head.setDataPageCount(0);
		head.writeToBuffer(contenueDeLaHeadPages);
		
		
		bufferManager.freePage(headpage,1);
		
	}

	
	public void getFreePageId(PageId oPageId) throws IOException  {

		
		int fildx=reldef.getFiledIdx();//reldef
		PageId head=new PageId(fildx,0);
		
		byte[] bufferPage=bufferManager.getPage(head);
		
		HeaderPageInfo headerPage=new HeaderPageInfo();
		headerPage.readFromBuffer(bufferPage);
	
		
		ArrayList<PageNbfree> list=headerPage.getCouples();
			
		  for(int i=0;i<list.size();i++) {
			
			if(list.get(i).getFreeSots()>0) {

			  	oPageId.setFileIdx(fildx);
				oPageId.setPageIdx(list.get(i).getPageidx());
				
				
				 bufferManager.freePage(head,0);
				 return;
			
			}
			
			
		  }
		  
			diskManager.AddPage(fildx,oPageId);
			
			headerPage.setDataPageCount(headerPage.getDataPageCount()+1);
			
			PageNbfree coupl=new PageNbfree(oPageId.getPageIdx(),reldef.getSlotCount());
			headerPage.setCouples(coupl);
		
			headerPage.writeToBuffer(bufferPage);
			
			bufferManager.freePage(head,1);
			
			byte [] dataPageBuffer=bufferManager.getPage(oPageId);
		
			
			 for(int i=0;i<reldef.getSlotCount();i++) {
				 
				 dataPageBuffer[i]=0;
			
	       	   }

				
			bufferManager.freePage(oPageId,1);
			
			
				
	}
	
	public void updateHeaderWithTakenSlot(PageId iPageId) throws IOException {
		int fild=reldef.getFiledIdx();
		
		PageId head=new PageId(fild,0);
		
		byte[] bufferhead=bufferManager.getPage(head);
	
		HeaderPageInfo headerPa=new HeaderPageInfo();
		headerPa.readFromBuffer(bufferhead);
		ArrayList<PageNbfree> liste=headerPa.getCouples();
		for(int i=0;i<liste.size();i++) {
			if(liste.get(i).getPageidx()==iPageId.getPageIdx() ) {
			
				liste.get(i).setFreeSots(liste.get(i).getFreeSots()-1);
				
				headerPa.writeToBuffer(bufferhead);
				
				bufferManager.freePage(head,1);
				
				return;
			}	
		}
		
		
	}
	
	//tp4
	public void  writeRecordInBuffer(Records iRecord,byte [] ioBuffer,int  iSlotIdx) {
		// position de l ecriture du record dans le buffer
		
		
		int position=(reldef.getSlotCount())+(iSlotIdx*reldef.getRecordSize());
		
		
		
		ArrayList<String> array=iRecord.getValue();
		ByteBuffer bb = ByteBuffer.wrap(ioBuffer);
		
		bb.position(position);
	
		for (int i = 0; i < reldef.getTypeColonne().size(); i++) {
			if(reldef.getTypeColonne().get(i).contains("string")) {
	 				String [] tabString = reldef.getTypeColonne().get(i).split("string");
	 			
		 			int entier=Integer.valueOf(tabString[1]);
		 		
		 			for (int k = 0; k <entier; k++) {
		 				
		 				bb.putChar(array.get(i).charAt(k));
		 				
		 				
		 				
					}
		 		
		 		
		 		
		 			
	 		}
			else {
	 		switch(reldef.getTypeColonne().get(i)) {
	 		
	 		case "int" :
	 		
	 				int valInt=Integer.parseInt(array.get(i));
	 				
	 			
	 				bb.putInt(valInt);
	 				
	 			
	 				
	 			  break;
	 		case "float" :
	 		
	 			float valFloat=Float.parseFloat(array.get(i));
	 			
	 			bb.putFloat(valFloat);
	 			
	 			break;
	 
	 			}
	 		}
		}
		
		
		}	
	
	public Records readRecordFromBuffer(byte [] iBuffer,int iSlotIdx) {
	
		int position=(reldef.getSlotCount())+(iSlotIdx*reldef.getRecordSize());
		
		ByteBuffer bb = ByteBuffer.wrap(iBuffer);
		bb.position(position);
		Records record = new Records();
		
		String s = "";
		
		for (int i = 0; i < reldef.getTypeColonne().size(); i++) {
			if(reldef.getTypeColonne().get(i).contains("string")) {
	 			String [] tabString = reldef.getTypeColonne().get(i).split("string");
	 			
		 			int entier=Integer.valueOf(tabString[1]);
		 			String s2="";
		 			for (int k = 0; k <entier; k++) {
		 				
		 				s2=s2+bb.getChar();
		 				
					}
		 			record.setValue(s2);
		 		
	 			
	 		
		 			
	 		}
			else {
	 		switch(reldef.getTypeColonne().get(i)) {
	 		case "int" :
	 			
	 			
	 			 s = ""+bb.getInt();
	 		
	 			 record.setValue(s);
	 			break;
	 		case "float" :
	 			
	 		
	 			 s = ""+bb.getFloat();
	 		
	 			 record.setValue(s);
	 	
	 			break;
	 		
			}
	 		
	 		}
		}
		
		
		return record;	
	}

		

	
	public  Rid insertRecordInPage(Records iRecord,PageId iPageId) throws IOException {
		Rid rid=null;
	
		byte []  buffer=bufferManager.getPage(iPageId);
		
		
		
		for(int i=0;i<reldef.getSlotCount();i++) {
			
			if(buffer[i]==(byte)0) {//, nous cherchons, avec la bytemap, une case disponible
		
			
				
			//actualisons la bytemapspécifier que la ca pour se est « occupée »
			
				
				writeRecordInBuffer(iRecord,buffer,i);// écrivons le Record avec writeRecordInBuffe
		
			
				buffer[i]=(byte)1;
				
				

				rid=new Rid(iPageId,i);
				
				
				
				
			
				
				bufferManager.freePage(iPageId, 1);// je l avais mis en comme 
				return rid;
			}
		}
			
			
		
	
	
		return null;
	}
	
	public  Rid insertRecord (Records iRecord) throws IOException {
		Rid rid=new Rid();
		PageId pageid=new PageId(-1,-1);
		
		getFreePageId(pageid);//initialise la page
	
		
		updateHeaderWithTakenSlot(pageid);
		
		rid=insertRecordInPage(iRecord,pageid);// insert le record dans la page
		
		return rid;
	}



	
	public ArrayList<Records> getRecordsOnPage(PageId iPageId) throws IOException{
	
		ArrayList<Records> records = new ArrayList<Records>();
		
		byte [] buffer = bufferManager.getPage(iPageId);
		
		 for (int i = 0; i < reldef.getSlotCount(); i++) {
			
			 	
			 if(buffer[i]==(byte)1) {
				
				 Records record = new Records();
				 record = readRecordFromBuffer(buffer,i );
			
				 records.add(record);
				
				 
	   	 }
		 }
		 bufferManager.freePage(iPageId, 0);
		 return records;
	}
	
	public ArrayList<PageId> getDataPagesIds() throws IOException {
		// TODO Auto-generated method stub
		ArrayList<PageId> arrayPageid = new ArrayList<PageId>();
	
		int fildx=reldef.getFiledIdx();//reldef
		PageId head=new PageId(fildx,0);
		
		byte[] bufferPage=bufferManager.getPage(head);
		
		HeaderPageInfo headerPage=new HeaderPageInfo();
		headerPage.readFromBuffer(bufferPage);
	
		
		ArrayList<PageNbfree> list=headerPage.getCouples();
			
		  for(int i=0;i<list.size();i++) {
			  PageId page=new PageId(-1,-1);
				page.setFileIdx(fildx);
				page.setPageIdx(list.get(i).getPageidx());
				
			  arrayPageid.add(page);
		
		  }
		  
		  bufferManager.freePage(head, 0);
		return arrayPageid;
	}
}
