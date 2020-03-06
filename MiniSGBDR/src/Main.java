package TP1;



import java.io.IOException;
import java.util.Scanner;





public class Main {

	private static Scanner s;
	public static String cheminRelatif;
	public static String cheminRelatif2;
	public static  DBManager dbManager;
	public static void main(String[] args) throws IOException {
		// TODO Auto-gener	ated method stub
		cheminRelatif= args[0];//"C:\\\\\\\\Users\\\\\\\\gaya\\\\\\\\Desktop\\\\\\\\Projet-fini_Khelili_Djouaher-Dekkar\\\\\\\\DB\\\\\\\\";
		// pour les fichier csv a lire
		cheminRelatif2=args[1];//"C:\\\\\\\\\\\\\\\\Users\\\\\\\\\\\\\\\\gaya\\\\\\\\\\\\\\\\Desktop\\\\\\\\\\\\\\\\Projet-fini_Khelili_Djouaher-Dekkar\\\\\\\\\\\\\\\\";
		dbManager=DBManager.getInstance();
		dbManager.Init();
		
		s = new Scanner(System.in);
		String commande="";
	
		boolean b=true;
		do {
			System.out.println("Entrez votre commande");
			commande=s.nextLine();
			String[] arg=commande.split(" ");
		
		if(arg[0].equals("exit")) {
			b=false;
			dbManager.Finish();
			
		}
		else {
			dbManager.ProcessCommand(arg);
		}
		}while(b==true);
	  
		
	}
	
	

}
