package SacADos;
import java.io.*;

/**
 * <p>
 * La classe LectureFichier permet de lire un fichier afin de remplir le tableau "ensemble" dans la classe probleme.
 * Le fichier doit avoir ce format : 
 * 
 * Poids limite du container : 80
 * Nombre d'items : 20
 * 1 15 20 36
 * 2 17 32 57
 * ...
 * </p>
 * <p>
 * Les valeurs ne sont que des exemples, les lignes n'ont pas besoin d'être dans cet ordre precis.
 * Par contre, il y a un ordre pour les valeurs definissant les items :
 * id poids prix valeurAffective
 *  
 * </p>
 * @see Probleme
 * @author Dylan Vincent
 *
 */
public class LectureFichier{
	BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));	
	String fichier = "";

	public String recupFic() throws IOException{
		System.out.println("Quel est le nom de votre fichier ?");
		fichier = clavier.readLine();
		return fichier;
	}

	/**
	 * <p>
	 * Cette m�thode permet de verifier le nom du fichier � partir duquel les donn�es sont charg�s.
	 * Si l'extension du fichier n'est pas "moving", il ne peut pas etre utilis�.
	 *  </p>
	 * @param s nom du fichier
	 * @return
	 * @throws IOException
	 */
	public BufferedReader fichier(String s) throws IOException{
		fichier=/*recupFic()*/s;
		while (!fichier.endsWith(".moving")){
			System.out.println("L'extension du fichier est incorrect, l'extension correct est .moving");
			fichier=recupFic();
		}
		try{
			BufferedReader fic = new BufferedReader(new FileReader(fichier));
			return fic;
		}catch(FileNotFoundException e)
		{
			System.out.println("Le fichier est introuvable.");
			System.exit(1);
		}
		return null;
	}

	/**
	 * <p>
	 * Cette m�thode permet de lire un fichier afin de construire un ensemble d'items utilisable.
	 * C'est ici qu'est ger� l'ensemble des erreurs qui peuvent r�sider dans la syntaxe. 
	 * </p>
	 * @param Probleme p, nomFichier
	 * @throws IOException Pour eviter les erreurs lors de la lecture de fichier
	 */	
	public void lecture(Probleme p,String s) throws IOException {
		String ligne = "";
		int n=0; // nombre de lignes
		int l=0; //nombre total de lignes	
		BufferedReader ficTexte = fichier(s);	
		while (ficTexte.readLine() != null) {
			l++;
		}
		ficTexte.close();
		ficTexte = fichier(s);
		while((ligne = ficTexte.readLine()) != null) {
			n++;		 
			if (ligne.startsWith( "Poids limite du container : ")) {
				if(Integer.decode(ligne.split(" : ")[1].split(" ")[0])<0){
					System.err.print("Il y a un probleme a� la ligne " + n + " : poids du container negatif \n" + ligne +"\n");
					System.exit(1);
				}
				p.setPoidsLimite(Integer.decode(ligne.split(" : ")[1].split(" ")[0]));	
			}
			else if ( ligne.startsWith( "Nombre d'items : ")){
				p.setNbItems(Integer.decode(ligne.split(" : ")[1].split(" ")[0]));
				if(p.getNbItems() > (l-2)){ 
					System.err.print("Il y a un probleme a� la ligne " + n + " : nombre d'item incorrect \n" + ligne +"\n");
					System.exit(1);
				}
			}
			else if ( ligne.split(" ").length==4 ){
				try{
					Integer.decode(ligne.split(" ")[0]);
					Integer.decode(ligne.split(" ")[1]);
					Integer.decode(ligne.split(" ")[2]);
					Integer.decode(ligne.split(" ")[3]);
				}
				catch(NumberFormatException e){
					System.err.print("Il y a un probleme a� la ligne " + n + " : probleme avec un entier \n" + ligne +"\n");
					System.exit(1);
				}
				if((Integer.decode(ligne.split(" ")[0]).intValue())>0 && (Integer.decode(ligne.split(" ")[1]).intValue())>0 && (Integer.decode(ligne.split(" ")[2]).intValue())>=0 && (Integer.decode(ligne.split(" ")[3]).intValue())>0 ){
					Item i = new Item (Integer.decode(ligne.split(" ")[0]),Integer.decode(ligne.split(" ")[1]),Integer.decode(ligne.split(" ")[2]),Integer.decode(ligne.split(" ")[3]));
					p.ajouteItem(i);
				}
				else{
					System.err.print("Il y a un probleme a� la ligne " + n + " : nombre negatif \n" + ligne +"\n");
					System.exit(1);
				}	
			}
			else{
				System.err.print("Il y a un probleme a� la ligne " + n + " : trop ou pas assez de colonnes \n" + ligne +"\n");
				System.exit(1);
			}
		} 
		ficTexte.close();
	}


	/**
	 * <p>
	 * Cette methode permet de lire un fichier et de construire un tableau de bytes.
	 * Chaque ligne de ce tableau sera d'au maximum 1024 bytes, ceci facilitera l'envoi des donn�es puisque cette methode est utilis�e pour la classe Client.java
	 * </p>
	 * @param s
	 * @return byte[][]
	 */
	public static char[][] ficToArray(String s)
	{
		char[][] tab = new char[5][1024];  
		int ligneTab=0, colonneTab=0; 
		String ligneARanger; 
		BufferedReader ficTexte;
		try
		{ 
			ficTexte = new BufferedReader(new FileReader(s));
			ligneARanger = ficTexte.readLine();
			while(ligneARanger!=null && !ligneARanger.equals("")) //On parcours tout le fichier ligne par ligne
			{ 
				for(int i=0;i<ligneARanger.length();i++) //On parcours la ligne caractere par caractere
				{					
					tab[ligneTab][colonneTab] = (char)ligneARanger.charAt(i); 
					colonneTab++;
					if(colonneTab==1024)
					{
						colonneTab=0; ligneTab++;
					}
					if(ligneTab==tab.length)
					{
						tab = agrandirMatrice(tab);
					}
				} 
				ligneARanger = ficTexte.readLine();
			}
			ficTexte.close();
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return tab;
	}
	
	
	
	/**
	 * <p>
	 * Cette methode permet d'agrandir une matrice par deux tout en gardant la m�me longueur des lignes. 
	 * Exemple : En entr�e : Une matrice 50*1024 
	 * 			 En sortie : Une matrice 100*1024.
	 * </p>
	 * @param tab
	 * @return tab 
	 */
	public static char[][] agrandirMatrice(char tab[][])
	{
		char[][] newTab = new char[tab.length*2][tab[0].length];
		for(int i=0;i<tab.length;i++)
		{
			for(int j=0;j<tab[i].length;j++)
			{
				newTab[i][j] = tab[i][j];
			}
		}
		return newTab;
	}
}
