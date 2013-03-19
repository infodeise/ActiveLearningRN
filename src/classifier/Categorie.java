package classifier;

public class Categorie {

	private String categorie;
	private int quantidade;
	
	public Categorie(String categorie, int quantidade) {
		this.categorie = categorie;
		this.quantidade = quantidade;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	
}
