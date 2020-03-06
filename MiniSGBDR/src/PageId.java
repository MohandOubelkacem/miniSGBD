package TP2;

public class PageId {
	 private int  FileIdx;
	 private int PageIdx;
	 
	 public PageId(int FileIdx,int PageIdx){
		 this.FileIdx=FileIdx;
		 this.PageIdx=PageIdx;
	 }
	 public int getPageIdx(){
		 return PageIdx;
	 }
	 public int getFileIdx(){
		 return FileIdx;
	 }
	 public void setFileIdx(int FileId){
		 this.FileIdx=FileId;
	 }
	 public void setPageIdx(int PageId){
		 this.PageIdx=PageId;
	 }
}
