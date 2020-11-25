package br.com.superEquipamentos.model;

public class CaixaCambio extends Equipamento{
	
	private String tipo;
	private String descricao;
	
	public CaixaCambio() {
		super();
	}
	public CaixaCambio(int id, String nome, String fabricante, double custo) {
		super(id, nome, fabricante, custo);
	}
	public CaixaCambio(int id, String nome, String fabricante, double custo, String tipo, String descricao) {
		super(id, nome, fabricante, custo);
		this.tipo = tipo;
		this.descricao = descricao;
	}
	public CaixaCambio( String nome, String fabricante, double custo, String tipo, String descricao) {
		super(nome, fabricante, custo);
		this.tipo = tipo;
		this.descricao = descricao;
	}
	public CaixaCambio(String nome, String fabricante, double custo) {
		super(nome, fabricante, custo);
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescricao() {
		return descricao;
	}
	@Override
	public String toString() {
		return "CaixaCambio [tipo=" + tipo + ", descricao=" + descricao + ", fabricante=" + getFabricante() +", custo=" + getCusto() +", nome=" + getNome()+ "]";
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
