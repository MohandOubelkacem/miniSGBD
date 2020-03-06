package TP1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelDef implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2733104505971741607L;
	private String nomRelation;
	private int nombreColonne ;
	private List<String> listTypeColonne;
	//// on rajoute deux variable recordSize et slotCount
	
	private int recordSize;
	private int slotCount;
	
	//// ont rajoute la varialbe fildIdx 
	private int filedIdx;
	
    public RelDef(){
    	this.nomRelation=null;
    	this.nombreColonne=0;
    	this.listTypeColonne=new ArrayList<String>();
    	this.recordSize=0;
    	this.slotCount=0;
    	this.filedIdx=0;
    	
    	
    }
    
    public int getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

	public int getSlotCount() {
		return slotCount;
	}

	public void setSlotCount(int slotCount) {
		this.slotCount = slotCount;
	}

	public void setNomRelation(String nom){
    	this.nomRelation=nom;
    }
    
    public void setNombreColonne(int nombre){
    	this.nombreColonne=nombre;
    }
    public void setTypeColonne(List<String> list){
    	    	this.listTypeColonne=list;
    	    }
    
    public String getNomRelation(){
    	return nomRelation;
    }
    public int getNombreColonne(){
        	return nombreColonne;
    }
    public List<String> getTypeColonne(){
    	return listTypeColonne;
    }

	public int getFiledIdx() {
		return filedIdx;
	}

	public void setFiledIdx(int filedIdx) {
		this.filedIdx = filedIdx;
	}
    
    }
