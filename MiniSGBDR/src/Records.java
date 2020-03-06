package TP3;

import java.util.ArrayList;

public class Records {
	private ArrayList<String> value;
	
	public Records() {
		this.value=new ArrayList<String>();
	}

	public ArrayList<String> getValue() {
		return value;
	}

	public void setValue(String value) {
		
		this.value.add(value);
	}
	public String toString(){
		return value.toString();
	}
	
}
