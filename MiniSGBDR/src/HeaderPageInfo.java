package TP3;



import java.nio.ByteBuffer;
import java.util.ArrayList;

public class HeaderPageInfo {
		private int dataPageCount;
		private ArrayList<PageNbfree> couples ;
		
		
		public HeaderPageInfo() {
			this.dataPageCount = 0;
			this.couples = new ArrayList<PageNbfree>();
		}
		
		
		public int getDataPageCount() {
				return dataPageCount;
		}
		
		public void setDataPageCount(int dataPageCount) {
				this.dataPageCount = dataPageCount;
		}
		
		public ArrayList<PageNbfree> getCouples() {
				return couples;
		}
		
		public void setCouples(PageNbfree couple) {
			this.couples.add(couple);
		}

		public void readFromBuffer(byte[] buffer) {
	//// element de  
			
			
			
			ByteBuffer bb=ByteBuffer.wrap(buffer);
		
			
			this.dataPageCount=bb.getInt();
			
			for (int i = 0; i < dataPageCount; i++) {
				
				PageNbfree m = new PageNbfree();
				
				m.setPageidx(bb.getInt());
				m.setFreeSots(bb.getInt());
			
				couples.add(m);
			  
				
			}
			
		}
		
		public void writeToBuffer(byte [] b) {
			
			
			
			ByteBuffer bb = ByteBuffer.wrap(b); 
			bb.putInt(dataPageCount);
			for(int i=0;i<couples.size();i++) {
					bb.putInt(couples.get(i).getPageidx());
					bb.putInt(couples.get(i).getFreeSots());
					
			}
		
	
			
			
			
		}
		
}
