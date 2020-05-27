package it.polito.tdp.artsmia.model;

public class Adiacenza implements Comparable<Adiacenza>{
	
	private Integer idArtista1;
	private Integer idArtista2;
	private Integer peso;
	
	public Adiacenza(Integer idArtista1, Integer idArtista2, Integer peso){
		super();
		this.idArtista1 = idArtista1;
		this.idArtista2 = idArtista2;
		this.peso = peso;
	}

	public Integer getIdArtista1() {
		return idArtista1;
	}

	public void setIdArtista1(Integer idArtista1) {
		this.idArtista1 = idArtista1;
	}

	public Integer getIdArtista2() {
		return idArtista2;
	}

	public void setIdArtista2(Integer idArtista2) {
		this.idArtista2 = idArtista2;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public int compareTo(Adiacenza o) {
		return -(this.peso-o.getPeso());
	}

}
