package TP4;

import TP2.PageId;

public class Rid {
	
	private PageId pageid;
	private int slotIdx;
	
	public Rid() {
		
	}
	public Rid(PageId pageid,int slotIdx) {
			this.pageid=pageid;
			this.slotIdx=slotIdx;
	}
	public PageId getPageid() {
		return pageid;
	}
	public void setPageid(PageId pageid) {
		this.pageid = pageid;
	}
	public int getSlotIdx() {
		return slotIdx;
	}
	public void setSlotIdx(int slotIdx) {
		this.slotIdx = slotIdx;
	}
}
