package SacADos;
import java.util.*;

/**
 * <b>La classe probleme contient toutes les m�thodes utiles a� l'utilisateur.</b>
 * <p>
 * Les champs de la classe sont les suivants :
 * <ul>
 * <li>poidsLimite : La limite de poids a� ne pas franchir lors de l'ajout des items</li>
 * <li>ensemble : Un ensemble qui contient tous les items de d�part</li>
 * <li>nombreItems : Le nombre d'items total au d�part</li>
 * <li>selection : Un ensemble qui contient tous les items selectionn�s</li>
 * <li>nombreItemsSelection : Le nombre d'items selectionn�s</li>
 * </ul>
 * </p>
 * @author Dylan Vincent
 *
 */
public class Probleme {
	private int poidsLimite;
	private int choix;	
	private int nombreItems; 
	private int nombreItemsSelection;
	private int bestScore;
	private ArrayList<Item> ensemble; 
	private ArrayList<Item> selection;
	private Scanner sc = new Scanner(System.in);

	/**
	 * Constructeur de Probleme.
	 */
	public Probleme(){
		ensemble = new ArrayList<Item>();
		selection = new ArrayList<Item>();
	}

	/**
	 * Modifier la valeur de poids limite.
	 * @param x
	 */
	public void setPoidsLimite(int x){
		poidsLimite=x; 
	}

	/**
	 * Modifier le nombre d'items total dans l'ensemble
	 * @param x
	 * 			Le nombre d'items
	 */
	public void setNbItems(int x){
		nombreItems=x; 
	}

	/**
	 * Retourne le poids limite
	 * @return poidsLimite
	 */
	public int getPoidsLimite(){ 
		return poidsLimite; 
	}

	/**
	 * Retourne le nombre d'items
	 * @return nombreItems
	 */
	public int getNbItems(){
		return nombreItems; 
	}

	/**
	 * <p>
	 * Retourne le nombre d'items dans l'ensemble "selection"</p>
	 * @return nombreItemsSelection
	 */
	public int getNombreItemSelection(){ 
		return nombreItemsSelection;
	}

	/**<p>
	 * Ajouter un item a� l'ensemble</p> 
	 * @param i
	 * 			objet de type Item
	 */
	public void ajouteItem(Item i){
		ensemble.add(i);
	}

	/**<p>
	 * Retourne la valeur totale (prix) de la s�lection courante</p>
	 * @return prix
	 */
	public int valeurSelection(){  
		int prix=0;

		for(int i=0;i<selection.size();i++)
		{
			prix += selection.get(i).getPrix();
		}
		return prix;
	}

	/**<p>
	 * Retourne le poids total de la s�lection courante.</p>
	 * @return poids
	 */
	public int poidsTotalSelection(){
		int poids=0;	
		for(int i=0;i<selection.size();i++){
			poids += getItemSelection(i).getPoids();
		}
		return poids;
	}

	/**<p>
	 * Retourne l'indice d'un item pour un id donn�. 
	 * Retourne -1 si l'item n'est pas trouv�.</p>
	 * @param id
	 * 
	 */
	private int getIndiceItem(int id,ArrayList<Item> e){
		for(int i=0;i<e.size();i++)
		{
			if(e.get(i).getId()==id)
			{
				return i;
			}
		}
		return -1;
	}

	/**
	 * <p>
	 * Ajouter un item, d'id donn�, de "ensemble" dans "selection"
	 * L'ajout de l'item n'est pas possible s'il fait d�passer le poids limite ou s'il est d�ja� pr�sent dans "selection".
	 * </p>
	 * @param id
	 * @return boolean (r�ussi ou non)
	 */
	public boolean selectionneItem(int id){  
		int i = getIndiceItem(id,ensemble);
		if( i != -1)
		{
			if((poidsTotalSelection()+ensemble.get(i).getPoids()) <= poidsLimite && selection.contains(ensemble.get(i))==false){
				selection.add(ensemble.get(i));
				return true;
			}
		}
		System.out.println("Vous avez d�ja� selectionner cet item !");
		return false;
	}

	/**
	 * Retirer un item de "selection" 
	 * @param i
	 */
	public void deselectionneItem(int id){
		int i = getIndiceItem(id,selection);
		if( i != -1) selection.remove(i);
	}

	/**
	 * Afficher les items de "ensemble"
	 */
	public void afficherEnsemble(){
		System.out.println("Liste des objets a� choisir ");
		for(int i=0;i<getEnsemble().size();i++){
			System.out.println(" id= " + getItemEnsemble(i).getId() + " poids= " + getItemEnsemble(i).getPoids() + " prix= " + getItemEnsemble(i).getPrix() + " affectif= " + getItemEnsemble(i).getValeurAffective() );
		}
		System.out.println("\n");
	}

	/**
	 * Afficher les items de "selection"
	 */
	public void afficherSelection(){
		int poidsTotal=0, prixTotal=0, nbItem=0;
		System.out.println("Liste des objets a� emporter ");
		for(int i=0;i<getSelection().size();i++){
			System.out.println(" id= " + getItemSelection(i).getId() + " poids= " + getItemSelection(i).getPoids() + " prix= " + getItemSelection(i).getPrix() + " affectif= " + getItemSelection(i).getValeurAffective() );
			poidsTotal += getItemSelection(i).getPoids();
			prixTotal += getItemSelection(i).getPrix();
			nbItem++; 
		}
		System.out.println("Poids total de la selection = " + poidsTotal);
		System.out.println("Prix total de la selection = "+prixTotal);
		System.out.println("Nombre d'items = "+nbItem);
	}

	/**
	 * Afficher le poids limite
	 */
	public void afficherPoidsLimite(){
		System.out.println("Poids limite du container: " + poidsLimite);
	}

	/**
	 * Retourne "ensemble"
	 * @return ensemble
	 */
	public ArrayList<Item> getEnsemble(){
		return ensemble;
	}

	/**
	 * Retourne la "selection"
	 * @return selection
	 */
	public ArrayList<Item> getSelection(){
		return selection;
	}

	/**
	 * Retourne l'item d'indice donn� de "ensemble"
	 * @param i
	 * @return
	 */
	public Item getItemEnsemble(int i){
		return ensemble.get(i);
	}

	/**
	 * Retourne l'item d'indice donn� de "selection"
	 * @param i
	 * @return
	 */
	public Item getItemSelection(int i){
		return selection.get(i);
	}

	/**<p>
	 * La m�thode user est utile pour l'utilisateur, il choisit s'il veut selectionner ou deselectionner un item, ou s'il veut arreter le remplissage du container.
	 *</p>
	 */
	public void user(){
		boolean terminer=false;
		String fin="";
		afficherPoidsLimite();
		sUser();
		while(terminer==false){
			System.out.println("Voulez vous continuer? selection(s)/deselection(d)/non(n)");
			fin=sc.next();
			if(fin.startsWith("n")){
				terminer=true;
			}
			else if(fin.startsWith("s")){
				sUser();
			}
			else if(fin.startsWith("d")){
				dUser();
			}
			else{
				System.out.println("Veuillez retaper votre choix");
			}		
		}
	}


	/**
	 * <p>
	 * Cette m�thode invite l'utilisateur a� entrer un id pour selectionner(depuis ensemble) ou deselectionner(depuis selection)
	 * Si le parametre est 's', il sagira d'une selection, sinon d'une deselection.
	 * </p>
	 * @param c, selection ou deselection (s ou autre chose).
	 * @return id de l'item choisi
	 */
	public int choix(char c){
		if ( c=='s'){
			System.out.println("Entrez l'id de l'objet que vous souhaitez prendre:");
		}
		else System.out.println("Entrez l'id de l'objet que vous souhaitez retirer: ");
		return sc.nextInt();
	}

	/**
	 *  <p>Cette m�thode permet a� l'utilisateur de choisir un id parmis ensemble.
	 * Il ne peut donc que selectionner un item pr�sent dans cette liste avec un id correct. 
	 * </p>
	 */
	public void sUser(){
		choix=choix('s');
		if(choix>0 && choix<=ensemble.size()){
			if( (poidsTotalSelection()+getItemEnsemble(choix-1).getPoids()) <= poidsLimite ){
				selectionneItem(choix);
				System.out.println("poids selection = " + (poidsTotalSelection()));	
			}
			else System.out.println("Vous ne pouvez prendre cet objet sans d�passer le poids limite!");
		}
		else {
			System.out.println("L'id choisi est incorrect!");
			sUser();
		}
	}
	/**
	 * <p>Cette m�thode permet a� l'utilisateur de choisir un item parmis selection.
	 * Il ne peut donc que d�selectionner un item pr�sent dans cette liste avec un id correct. 
	 * </p>
	 */
	public void dUser(){
		afficherSelection();
		choix=choix('d');
		if(getIndiceItem(choix,selection)!=-1){
			deselectionneItem(getIndiceItem(choix,selection));
		}
		else {
			System.out.println("L'id choisi est incorrect!");
			dUser();
		}
	}

	/**
	 * <p> 
	 * Cette m�thode permet d'afficher la selection sous un format pr�cis. 
	 * Le score du r�sultat est aussi affich�.
	 * </p>
	 */
	public void afficheSolution(){
		System.out.print("Solution - score : " + bestScore + " - ");
		for (int i=0;i<ensemble.size()-1;i++){
			if(selection.contains(ensemble.get(i))){
				System.out.print( i+1 + " X - ");
			}
			else System.out.print(i+1 + " O - ");
		}
		if(selection.contains(ensemble.get(ensemble.size()-1))){
			System.out.println( ensemble.size() + " X ");
		}
		else System.out.println( ensemble.size() + " O ");

	}

	public String getStringSolution()
	{
		String s="Solution - score : "+bestScore + " - ";
		for(int i=0;i<ensemble.size()-1;i++)
		{
			if(selection.contains(ensemble.get(i))){
				s += i+1 + " X - ";
			}
			else 
			{
				s += i+1 + " O - ";
			}
		}
		if(selection.contains(ensemble.get(ensemble.size()-1))){
			s += ensemble.size()+ " X ";
		}
		else s+= ensemble.size() + " O ";

		return s;
	}


	/**
	 * <p>
	 * Cette m�thode est construite sous un algorithme permettant la r�solution automatique du probl�me.
	 * Celui-ci se fait en deux temps.
	 * <ul>
	 * <li>On ajout les items depuis ensemble dans selection jusqu'a ce que le poids est depass�. On a donc ici une base pour appliquer
	 * l'algorithme suivant. </li>
	 * <li>On applique ensuite un algorithme fait maison appel� "Algorithme d'�changes par profits".
	 * Pour un objet non ajout�, on regarde s'il serait peut-etre mieux d'�changer cet objet avec un pr�sent dans la selection.
	 * Pour cela, on compare le profit (que r�aliserai l'�change des objets) de l'item en test avec tous les items en selection.
	 * Et on r�alise l'�change avec le meilleur profit a� chaque fois.
	 *  </li>
	 * </ul>
	 * Cette m�thode modifie directement l'ArrayList selection.
	 * 
	 * Cette m�thode prend 1 parametre.  
	 * ArrayList B correspondant a� la liste d'items non-ajout�s
	 * </p>
	 */
	public void resout2() {
		addAllCanBe(); 
		ArrayList<Item> B = getListNonAddedItem();
		int indiceMeilleurProfit; 

		for(int i=0;i<B.size();i++){ //Parcours de la liste d'items non-ajout�s
			double maxProfit=0;
			indiceMeilleurProfit=-1; 
			for(int j=0;j<selection.size();j++)
			{ //Ici on cherche un item avec lequel il est possible de faire un bon �change
				if( (poidsTotalSelection() - selection.get(j).getPoids() + B.get(i).getPoids())<this.poidsLimite)
				{ //Si l'�change des 2 objets ne fait pas d�passer le poids limite
					if(maxProfit < differenceProfit(selection.get(j),B.get(i)))
					{//Si on a trouv� un meilleur profit
						indiceMeilleurProfit=j;
						maxProfit=differenceProfit(getItemSelection(j),B.get(i)); 
					}
				}
			}
			if(indiceMeilleurProfit!=-1 && !selection.contains(B.get(i)))
			{ //Si on a trouv� un profit a� faire et que l'item n'est pas d�j� present dans la selection
				//On souhaite changer l'item num�ro indiceMeilleurProfit (de la selection)
				//Par l'item num�ro i (pr�sent dans la liste B)
				B.add(selection.get(indiceMeilleurProfit));
				//Il peut ensuite etre supprim� de la selection
				selection.remove(selection.get(indiceMeilleurProfit));
				//Ajout de l'item actuel (celui qui donne un max de profit) dans la selection
				selection.add(B.get(i));
			}
			if(i==B.size()) i=0;
		}
		updateScore();
		//afficheSolution(); 
	}

	/**
	 * <p>Cette m�thode permet de lancer autant de fois que necessaire resout2() jusqu'� ce qu'aucune am�lioration n'est possible.
	 * 		Si 2 fois de suite la diff�rence entre l'ancien et le nouveau score est nulle, alors on consid�re que l'on a trouv� la meilleure solution.
	 * </p>
	 */
	public void lancementResout2()
	{
		int ancienScore=0, nouveauScore=0, nbScoreNulle=0, i=0;
		boolean fin=false;
		while(!fin)
		{
			resout2();
			nouveauScore=updateScore();
			//System.out.println(i+" - "+nouveauScore + " - " + (nouveauScore - ancienScore));

			if( (nouveauScore - ancienScore) ==0)
			{
				nbScoreNulle++; 
			}
			else 
			{
				nbScoreNulle=0;
			}
			if(nbScoreNulle==2)
			{
				fin=true;
			}
			ancienScore = nouveauScore;
			i++;
		}
		//System.out.println("Final : score="+ancienScore);
	}

	private double differenceProfit(Item A, Item B) {		
		return ( B.getPrix()*1.0 /B.getPoids()) - ( A.getPrix()*1.0 / A.getPoids());
	}

	/**
	 * <p>
	 * Cet algorithme est similaire a� resout2 sauf qu'au lieu de chercher un meilleur profit, il cherche un meilleur score.
	 * Il a besoin d'une base donn� par addAllCanBe() pour fonctionner et utilise 2 listes : 
	 * <ul>
	 * <li>La liste selection</li>
	 * <li>Une liste cr��e par getListNonAddedItem() pour obtenir les items qui n'ont pas encore �t� ajout�s.</li>
	 * </ul>
	 * </p>
	 */
	public void resout3() {

		addAllCanBe();  

		ArrayList<Item> listNonAdded = getListNonAddedItem();
		int ancienScore; 
		for(int i=0;i<listNonAdded.size();i++){ //Parcours de la liste d'items non-ajout�s 
			ancienScore = updateScore();
			for(int j=0;j<selection.size();j++){ //Dans cette boucle on parcours la selection, a� la recherche 
				//d'un item avec lequel il est possible de faire un bon �change
				//Afin d'obtenir un score croissant
				if( (poidsTotalSelection() - selection.get(j).getPoids() + listNonAdded.get(i).getPoids())<this.poidsLimite  ){ //Si l'�change des 2 objets ne fait pas d�passer le poids limite
					ancienScore = updateScore();
					if(ancienScore<(ancienScore-selection.get(j).getPrix()+listNonAdded.get(i).getPrix() ))
					{//Si l'�change des 2 items donne un meilleur Score

						Item itemDeSelection=selection.get(j);
						Item itemDeListNonAdded = listNonAdded.get(i);			
						//On proc�de a� l'�change
						//-1 Item dans selection est mis dans la liste des items non-ajout�s
						listNonAdded.add(itemDeSelection);
						//-2 Item dans selection est effectivement supprim� de selection
						selection.remove(itemDeSelection);
						//-3 Item de listNonAdded est ajout� dans la selection
						selection.add(itemDeListNonAdded);
						//-4 Item de listNonAdded est effectivement supprim� de listNonAdded
						listNonAdded.remove(itemDeListNonAdded);

					}
				}
			}
		}
		afficheSolution();
	}


	/**
	 * <p>
	 * Cette m�thode permet d'ajouter tous les objets possible depuis ensemble dans selection jusqu'a� ce que l'ajout n'est plus possible (poids limite depass�).
	 * Elle permet de donner une base pour les algorithmes de r�solution par �change.
	 * </p>
	 */
	public void addAllCanBe(){
		for(int i=0;i<ensemble.size();i++){ //Parcours de la liste Ensemble
			if(( (poidsTotalSelection()+ensemble.get(i).getPoids())<poidsLimite) && !selection.contains(ensemble.get(i))){ 
				//On fait attention a� ne pas faire d�passer le poids limite et � ne pas ajouter un item d�j� pr�sent
				selection.add(ensemble.get(i));
			}
		}

	}

	//
	/**
	 *<p>Cette m�thode renvois une liste des objets pr�sent dans ensemble mais non pr�sent dans selection</p> 
	 * @return ArrayList<Item>
	 */
	private ArrayList<Item> getListNonAddedItem(){
		ArrayList<Item> nonAddedItems = new ArrayList<Item>();
		for(int i=0;i<ensemble.size();i++){
			if(!selection.contains(ensemble.get(i))){ //Si la liste selection ne contient pas l'item i de ensemble	
				nonAddedItems.add(ensemble.get(i));
			}
		}
		return nonAddedItems;
	}

	/**
	 * <p>
	 * En plus de le renvoyer, cette m�thode permet de mettre � jour le score de la selection.
	 * </p>
	 * @return score
	 */
	public int updateScore() {
		bestScore= valeurSelection();
		return bestScore;
	}


	/**
	 * <p>
	 * Cette methode permet d'initialiser la valeur generale de chaque item avec les coefficients
	 * donn�s par le client au serveur.
	 * </p>
	 * @param coefValeurPecuniaire
	 * @param coefValeurAffective
	 */

	public void setValeurGenerale(int coefValeurPecuniaire, int coefValeurAffective)
	{
		for(int i=0;i<ensemble.size();i++)
		{
			ensemble.get(i).setValeurGenerale(coefValeurPecuniaire, coefValeurAffective);
		}
	}
}
