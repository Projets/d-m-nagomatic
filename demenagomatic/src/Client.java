package SacADos;
import java.io.*;
import java.net.*;

public class Client {
	private Socket socket;
	private String nomFichier; //Le nom du fichier qui sera � envoyer au serveur
	private char[][] donnees; //liste correspondant aux donn�es venant du fichier (en-t�te et nombres)
	private int coefValAffective;
	private int coefValPecuniaire;
	private boolean jeuEnCours;

	public void connexion(InetAddress adresseServeur,int port)
	{
		try {
			socket = new Socket(adresseServeur,port);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("\n"+e.getMessage());
			System.exit(1);
		}
	}


	public BufferedReader fluxEntrant()
	{

		try {
			InputStream	inputStream = this.socket.getInputStream();

			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader inBufferedReader = new BufferedReader(inputStreamReader);
			return inBufferedReader;

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("\n"+e.getMessage());
			System.exit(1);
		}
		return null;
	}
	public PrintWriter fluxSortant()
	{

		try {
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter outputPrintWriter = new PrintWriter(outputStream);
			return outputPrintWriter;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("\n"+e.getMessage());
			System.exit(1);
		}
		return null;
	}
	 
	public void dialogueConnexion()
	{
		try{ 
			BufferedReader lecteurReseau = fluxEntrant();
			PrintWriter ecrivainReseau = fluxSortant();
			String message=""; 

			//Envoi d'un message de la part du client au serveur 
			if(!jeuEnCours) message="wannaPlay ? "+coefValPecuniaire+" "+coefValAffective;//message pour demander le lancement d'une partie

			ecrivainReseau.println(message);
			ecrivainReseau.flush(); //Vide le buffer 

			//Reception d'un message
			message=lecteurReseau.readLine();
			if(message.equals("OK") && !jeuEnCours) 
			{//Le serveur a accept� le lancement de la partie
				jeuEnCours=true;	
				dialogueEnvoiFichier();
			}  
			else if(message.equals("KO"))
			{
				System.out.println("Fin de la communication. Le protocole n'a pas �t� respect� ou une partie est d�j� en cours.");
			}
			socket.close();	 
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("\n"+e.getMessage());
			System.exit(1);				
		}
	}

	public void dialogueEnvoiFichier()
	{
		try{
			boolean fin=false;
			boolean tour=true; 
			BufferedReader lecteurReseau = fluxEntrant();
			PrintWriter ecrivainReseau = fluxSortant();
			String message=""; 
			int i=0;
			while (!fin && !message.equals("KO")) {
				if (tour)
				{ 
					//Il faut envoyer les donn�es du fichier .moving au serveur 
					if(i<donnees.length){  
						message="";
						for(int j=0;j<1024;j++)
						{ 
							message+= donnees[i][j];
						}
						i++;
					}
					else 
					{
						message="FIN ENVOI FICHIER";
						fin=true;
					}
				 
					ecrivainReseau.println(message);
					ecrivainReseau.flush(); //Vider le buffer
				} 
				else 
				{//Reception d'un message
					message=lecteurReseau.readLine();

					if(message.equals("KO"))
					{
						System.out.println("Fin de la communication. Le protocole n'a pas �t� respect�.");
					}
				}
				tour=!tour;
			} 	

			dialogueResultat();

		}catch(IOException e){
			e.printStackTrace();
			System.out.println("\n"+e.getMessage());
			System.exit(1);				
		}
	}
	public void dialogueResultat()
	{
		System.out.println("Point d'arriv� : Methode de communication des resultats, c�t� client. WIP");
	}
	public void principal()
	{
		try {
			byte[] tab = {127,0,0,1};
			InetAddress IA;
			IA = InetAddress.getByAddress(tab);
			connexion(IA,2000);
			dialogueConnexion();
			this.socket.close();
		}catch(IOException e){};
	}





	public static void main(String[] args)
	{
		Client c = new Client();
		c.nomFichier = args[0];
		c.coefValAffective=2;
		c.coefValPecuniaire=3;
		c.donnees = LectureFichier.ficToArray(c.nomFichier);
		c.principal();

	}
}
