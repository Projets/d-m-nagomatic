package SacADos;
public class Test {

	public static void main(String[] args){
		try {
			LectureFichier f = new LectureFichier();
			Probleme p = new Probleme();
			 
			if(args[1].equals("-multi")) 
			{
				if(args.length==3 && args[2].equals("-host"))//En vrai c'est pas une chaîne de caractère "host" mais l'adresse du serveur auquel le client souhaite se co
				{//L'user doit se connecter au serveur 'host' donné en paramètre 
					Client.main(args);
					
				}
				else if(args.length==2)
				{//L'user souhaite lancer lancer le programme en tant que serveur
					Serveur.main(new String[1]);
				}
				else 
				{
					help();
				} 
			}
			else if(args.length==2 && args[1].equals("-computer")){
				f.lecture(p,args[0]);
				p.lancementResout2();

				//p.afficherSelection();
				System.out.println(p.getStringSolution());
			}
			else if(args.length==2 && args[1].equals("-human")){
				f.lecture(p, args[0]);
				p.afficherEnsemble();
				p.user();	 
			}
			else if (args.length==1 && args[0].equals("-manual")){
				help();
			}
			else if (args.length==1){
				f.lecture(p,args[0]);
				p.resout2();
			}
			else help();
		}
		catch(Exception e){
			System.err.println(e);
		}

	}

	private static void help(){
		System.out.println("Ce programme necessite au moins 1 argument qui correspond au fichier .moving que vous souhaitez lui transmettre.");
		System.out.println("Liste des options: ");
		System.out.println("java Moving file.moving [-solo -multi[:host]] [-human -computer [-sI]] [-help]");
		System.out.println("-solo");
		System.out.println("    Lance le programme en solo");
		System.out.println("-multi[:host]");
		System.out.println("    Lance le programme en mode multi. Si aucun hote n'est prÃ©cisÃ©, le programme dÃ©marre en tant que serveur. Sinon, il dÃ©marre en tant que client et se connecte au serveur 'host'");
		System.out.println("-human");
		System.out.println("    Le programme est dirigé par un humain");
		System.out.println("-computer -sI");
		System.out.println("    Le programme est dirigé par l'ordinateur, suivant la stratÃ©gie 'I' (entre 1 et 5). Si aucune stratégie n'est précisée, celle qui est considérée comme la plus efficace est utilisée par défaut");
		System.out.println("-help");
		System.out.println("    Afficher cette aide.");
	}
}
