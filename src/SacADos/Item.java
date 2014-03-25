package SacADos;
/**
 * <b>La classe Item represente les objets qui seront a  ajouter dans le container. 
 * Une fois que l'item est cree, il est immuable.</b>
 * <p>
 * Un item est represente comme ceci : 
 * <ul>
 * <li> Un id : Chaque item a un id unique</li>
 * <li> Un poids : Le poids total de la selection ne doit pas depasser une limite</li>
 * <li> Un prix : Qui compte pour la selection</li>
 * <li> Une valeur affective : C'est a  dire a  quel point l'utilisateur tiens a  cet objet.
 * </ul>
 * </p>
 * 
 * @see Probleme
 * @author Dylan Vincent
 */



public class Item {
	private int id;
	private int poids;
	private int prix;
	private int valeurAffective;
	private int valeurGenerale;

	/**
	 * <p>
	 * Unique constructeur de la classe Item.
	 * </p>
	 * @param id
	 * @param poids
	 * @param prix
	 * @param valeurAffective
	 */
	public Item(int id, int poids, int prix, int valeurAffective)
	{
		this.id=id;
		this.poids=poids;
		this.prix=prix;
		this.valeurAffective=valeurAffective;
		this.valeurGenerale=0;
	}

	/**
	 * @return l'id de l'objet courant
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * @return le prix de l'objet courant
	 */
	public int getPrix()
	{
		return prix;
	}

	/**
	 * @return le poids de l'objet courant
	 */
	public int getPoids()
	{
		return poids;
	}

	/**
	 * @return la valeur affective de l'objet courant
	 */
	public int getValeurAffective()
	{
		return valeurAffective;
	}
	public void setValeurGenerale(int coefValeurPecuniaire, int coefValeurAffective)
	{
		this.valeurGenerale = coefValeurAffective*valeurAffective + coefValeurPecuniaire*prix;
	}
}

