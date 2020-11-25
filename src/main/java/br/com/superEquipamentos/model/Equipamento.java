package br.com.superEquipamentos.model;

public abstract class Equipamento {
	

	private int id;
	private String nome;
	private String fabricante;
	private double custo;
	
	public Equipamento(int id, String nome, String fabricante, double custo) {
		super();
		this.id = id;
		this.nome = nome;
		this.fabricante = fabricante;
		this.custo = custo;
	}
	public Equipamento( String nome, String fabricante, double custo) {
		super();
		this.nome = nome;
		this.fabricante = fabricante;
		this.custo = custo;
	}
	
	public Equipamento() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int i) {
		this.id = i;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public double getCusto() {
		return custo;
	}
	public void setCusto(double custo) {
		this.custo = custo;
	}

}

