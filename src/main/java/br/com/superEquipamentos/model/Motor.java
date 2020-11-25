package br.com.superEquipamentos.model;

public class Motor extends Equipamento {

	private Long velocidade;
	private int potencia;
	
	public Motor() {
		super();
	}
	public Motor(int id, String nome, String fabricante, double custo) {
		super(id, nome, fabricante, custo);

	}
	public Motor(String nome, String fabricante, double custo) {
		super(nome, fabricante, custo);
	}
	public Long getVelocidade() {
		return velocidade;
	}
	public Motor(int id, String nome, String fabricante, double custo, Long velocidade, int potencia) {
		super(id, nome, fabricante, custo);
		this.velocidade = velocidade;
		this.potencia = potencia;
	}
	public Motor(String nome, String fabricante, double custo, Long velocidade, int potencia) {
		super(nome, fabricante, custo);
		this.velocidade = velocidade;
		this.potencia = potencia;
	}
	public void setVelocidade(Long velocidade) {
		this.velocidade = velocidade;
	}
	@Override
	public String toString() {
		return "Motor [velocidade=" + velocidade + ", potencia=" + potencia + ", fabricante=" + getFabricante() +", custo=" + getCusto() +", nome=" + getNome()+"]";
	}
	public int getPotencia() {
		return potencia;
	}
	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}
	

}
