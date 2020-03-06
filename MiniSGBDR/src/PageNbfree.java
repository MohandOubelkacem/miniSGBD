package TP3;

public class PageNbfree {
	private int pageidx;
	private int freeSlots;
	public PageNbfree(int pageidx, int freeSots) {
		this.pageidx = pageidx;
		this.freeSlots = freeSots;
	}
	public PageNbfree() {
		
	}
	
	public int getPageidx() {
		return pageidx;
	}
	public void setPageidx(int pageidx) {
		this.pageidx = pageidx;
	}
	public int getFreeSots() {
		return freeSlots;
	}
	public void setFreeSots(int freeSots) {
		this.freeSlots = freeSots;
	}
	public String toString() {
		return "pageidx : "+ pageidx +"freeslot :"+freeSlots;
	}
	
}


