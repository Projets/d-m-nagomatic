package SacADos;

import java.io.*;
import java.net.*;

public class Serveur {
	private ServerSocket serverSocket;
	private int coefValeurPecuniaire;
	private int coefValeurAffective;
	private Socket client;
	private boolean partieEnCours;


	public void enregistrementService(int port)
	{
		try {
			serverSocket = new ServerSocket(port,1664); 
			System.out.println("*** Serveur en attente ***");

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("\n"+e.getMessage());
			System.exit(1);
		}
	}

	public Socket nouvelleConnexion()
	{
		try {  
			client = serverSocket.accept();
			System.out.println("\n*** Nouvelle Connexion ! ***\n");
			return client;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("\n"+e.getMessage());
			System.exit(1); 
		}
		return null;
	}

	public BufferedReader fluxEntrant(Socket socket)
	{

		try {
			InputStream	inputStream =socket.getInputStream();
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

	public PrintWriter fluxSortant(Socket socket)
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

	public void dialogue(Socket socket)
	{
		try{
			boolean fin=false;//Terminer la communication, tout s'est bien pass�
			boolean tour=false;
			boolean partieEnCours=false; 
			BufferedReader lecteurReseau = fluxEntrant(socket);
			PrintWriter ecrivainReseau = fluxSortant(socket);
			PrintWriter ecrivainFichier = new PrintWriter(new FileWriter(new File("donnees.moving"))); //Creation du fichier de reception des donn�es

			String message=""; //Dernier message envoy�/re�u 
			while (!fin){
				if (tour){//Envoi du message au client
					if(message.equals("KO")) fin=true;
					ecrivainReseau.println(message);
					ecrivainReseau.flush();
				} else { //Traitements des demandes du client
					message=lecteurReseau.readLine(); 
					if(message.startsWith("wannaPlay ?"))
					{//L'utilisateur veut lancer une partie
						if( !partieEnCours) 
						{
							String[] tab = message.substring(12).split(" "); 
							this.coefValeurPecuniaire = Integer.parseInt(tab[0]);
							this.coefValeurAffective = Integer.parseInt(tab[1]); 
							partieEnCours=true; 
							message="OK"; //On informe le client qu'on accepte sa demande, le message sera envoy� au prochain tour de boucle
						} 
						else 
						{
							System.out.println("Vous ne respectez pas le protocole.");
							System.out.println("Il est necessaire d'avoir deux coefficients (strictement inf�rieur � 10) apr�s la demande de jeu.");
							System.out.println("Exemple : wannaPlay ? 2 1");
							message="KO"; //On informe le client qu'il ne respecte pas le protocole, le message sera envoy� au prochain tour de boucle
						}
					}
					else if (partieEnCours && message!="KO" && message!="OK")
					{ //L'utilisateur envoi les donn�es du fichier
						ecrivainFichier.println(message);
					}
				}
				tour=!tour;
			}
			socket.close();	 
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("\n"+e.getMessage());
			System.exit(1);				
		}
	}

	/**
	 * <p>
	 * Cette methode dialogue avec le client afin de lancer la partie.
	 * Le client doit envoyer  "wannaPlay ? " suivi de deux entiers. (Il doit y avoir un 
	 * espace entre les deux entiers). Le premier est le coefficient de la valeur pecuniaire, le second
	 * est le coefficient de la valeur affective.
	 * </p>
	 * @param socket
	 */
	public void dialogueConnexion(Socket socket)
	{
		try{ 
			boolean respectCommunication=true;
			BufferedReader lecteurReseau = fluxEntrant(socket);
			PrintWriter ecrivainReseau = fluxSortant(socket); 
			String message; 

			message=lecteurReseau.readLine(); 
			if(message.startsWith("wannaPlay ?"))
			{//L'utilisateur veut lancer une partie
				if(!partieEnCours) 
				{
					String[] tab = message.substring(12).split(" "); 
					this.coefValeurPecuniaire = Integer.parseInt(tab[0]);
					this.coefValeurAffective = Integer.parseInt(tab[1]); 
					partieEnCours=true; 
					message="OK"; //On informe le client qu'on accepte sa demande, le message sera envoy� au prochain tour de boucle
					respectCommunication=true;
				} 
				else 
				{
					System.out.println("Une partie est d�j� en cours.");
					respectCommunication=false;
				}
			}
			else
			{
				System.out.println("Vous ne respectez pas le protocole. Pour lancer une partie, vous devez envoyer \"wannaPlay ? \" suivi de deux entiers.");
				System.out.println("Un espace doit �tre present entre les deux entiers.");
				System.out.println("Fin de la communication.");
				respectCommunication=false;
			}

			if(!respectCommunication)
			{ 
				message="KO";
			}

			ecrivainReseau.println(message);
			ecrivainReseau.flush(); 

			//Et on passe � la suite de la communication si le protocole a �t� respect� 
			if(respectCommunication){
				dialogueReceptionFichier(socket);
			}

		}catch(IOException e){
			e.printStackTrace();
			System.out.println("\n"+e.getMessage());
			System.exit(1);				
		}
	}


	/**
	 * <p>
	 * Cette methode dialogue avec le client afin de recevoir les donnees du fichier .moving sur lequel le serveur va travailler.
	 * Apr�s la reception d'une ligne de donn�es, celle-ci est �crit directement dans le fichier data.moving.
	 * </p>
	 * @param socket
	 */
	public void dialogueReceptionFichier(Socket socket)
	{
		try{

			boolean tour=false, respectProtocole=true;
			BufferedReader lecteurReseau = fluxEntrant(socket);
			PrintWriter ecrivainReseau = fluxSortant(socket);
			PrintWriter ecrivainFichier = new PrintWriter(new FileWriter(new File("donnees.moving"))); //Creation du fichier de reception des donn�es

			String message = "";
			while (!message.equals("KO")){
				if (tour){//Envoi du message au client
					ecrivainReseau.println(message);
					ecrivainReseau.flush();
				} else { 
					message=lecteurReseau.readLine(); 
					if (!message.equals("KO") && !message.equals("OK") && !message.equals("FIN ENVOI FICHIER"))
					{ //Reception des donnees du fichier
						ecrivainFichier.println(message);
						System.out.println(message);
						//message="RECU";
					}
					else if(message.equals("FIN ENVOI FICHIER"))
					{
						message="KO";
						respectProtocole=true;
					}
				}
				tour=!tour;
			}
			ecrivainFichier.close(); 
			dialogueResultat(socket);
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("\n"+e.getMessage());
			System.exit(1);				
		}
	}

	public void dialogueResultat(Socket socket)
	{
		System.out.println("Point d'arriv� : Methode de communication des r�sultats, c�t� serveur. WIP");
	}



	public void principal()
	{
		Socket socket;
		enregistrementService(2000);

		for(;;)
		{
			socket=nouvelleConnexion();
			dialogueConnexion(socket);
		}
	}

	public static void main(String[] args)
	{
		Serveur s = new Serveur();
		s.principal();
	}

}
