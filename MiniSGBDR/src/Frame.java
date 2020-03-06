package TP2;



public class Frame {
	 
	 
	 private byte [] buffer;
	 private PageId pageid;
	 private int pin_count;
	 private int dirty;
	 private int compteur;
	 public Frame() {
		 this.buffer=new byte[Constante.PageSize];
		 this.pageid=null;
		 setPin_count(0);
		 setDirty(0);
		 this.setCompteur(0);
	 }
	
	public byte [] getBuffer() {
		return buffer;
	}

	public void setBuffer(byte [] buffer) {
		this.buffer = buffer;
	}

	public PageId getPageid() {
		return pageid;
	}

	public void setPageid(PageId pageid) {
		this.pageid = pageid;
	}

	public int getPin_count() {
		return pin_count;
	}

	public void setPin_count(int pin_count) {
		this.pin_count = pin_count;
	}

	public int getDirty() {
		return dirty;
	}

	public void setDirty(int dirty) {
		this.dirty = dirty;
	}
	 
	 public void methodeLRU() {
		 
	 }

	public int getCompteur() {
		return compteur;
	}

	public void setCompteur(int compteur) {
		this.compteur = compteur;
	}

	
}
