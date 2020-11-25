package br.com.superEquipamentos.view.equipamento;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


import br.com.superEquipamentos.dao.motorDAO;
import br.com.superEquipamentos.model.Motor;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerMotor extends Application implements Initializable {
	private ObservableList<Motor> listTabelaMotor = FXCollections.observableArrayList();

	@FXML
	private TextField textFieldNome;

	@FXML
	private TextField textFieldCusto;

	@FXML
	private Button buttonInserir;

	@FXML
	private TextArea textAreaListMotor;

	@FXML
	private TextField textFieldFindID;

	@FXML
	private Label labelLabelID;

	@FXML
	private Label labelID;

	@FXML
	private Button buttonAlterar;

	@FXML
	private TextField textFieldVelocidade;

	@FXML
	private TextField textFieldPotencia;

	@FXML
	private TextField textFieldFabricante;
    @FXML
    private TableView<Motor> tabelaMotor;
	
    @FXML
    private TableColumn<Motor, Integer> columnId;

    @FXML
    private TableColumn<Motor, String> columnNome;

    @FXML
    private TableColumn<Motor, String> columnFabrica;

    @FXML
    private TableColumn<Motor, Double> columnCusto;

    @FXML
    private TableColumn<Motor, Long> columnVelocidade;

    @FXML
    private TableColumn<Motor, Integer> columnPotencia;


	@FXML
	void AlterarMotor(ActionEvent event) {
		Motor motor= pegaDadosId();
    	if(String.valueOf(motor.getId()) == null || String.valueOf(motor.getId()) =="") {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Alerta");
    		alert.setHeaderText("Motor não selecionado");
    		alert.setContentText("Selecione um Motor para alterar");
    	}
    	else {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
        	alert.setTitle("Alterar Motor");
        	alert.setHeaderText("Você está prestes a alterar um motor");
        	alert.setContentText("Tem certeza que deseja altterar o motor?");
        	Optional<ButtonType> result = alert.showAndWait();
        	if (result.get() == ButtonType.OK){	
    		limpaCampos();
    		new motorDAO().alterar(motor);
    		listarMotor();
        	}
    	}
	}

	@FXML
	void DeletarMotor(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Deletar Motor");
    	alert.setHeaderText("Você está prestes a deletar um motor");
    	alert.setContentText("Tem certeza que deseja deletar o motor?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		Motor motor= pegaDadosId();
    		new motorDAO().deletar(motor.getId());
        	limpaCampos();
        	listarMotor();
    	}
	}
	@FXML
	void buscarMotor(ActionEvent event) {
		String idString = textFieldFindID.getText();
		Motor motor = null;
		if (!idString.equals("")) {
			try {
				int id = Integer.valueOf(idString);
				motor = new motorDAO().findByID(id);
			} catch (Exception e) {
			}
			if (motor != null) {
				labelLabelID.setVisible(true);
				labelID.setVisible(true);
				labelID.setText(motor.getId() + "");
				textFieldNome.setText(motor.getNome());
				textFieldFabricante.setText(motor.getFabricante());
				textFieldCusto.setText(motor.getCusto()+ "");
				textFieldPotencia.setText(motor.getPotencia() + "");
				textFieldVelocidade.setText(motor.getVelocidade() + "");
			}
		}
		textFieldFindID.clear();
	}
	@FXML
	void inserirMotor(ActionEvent event) {
    	Motor motor = pegaDados();
		limpaCampos();
		System.out.println("Fabricante "+ motor.getFabricante());
		new motorDAO().inserir(motor);
		listarMotor();
	}

	public void initialize(URL location, ResourceBundle resources) {
		listarMotor();
	}
	@Override
	public void start(Stage stage) {
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("Motor.fxml"));
			Scene sc = new Scene(pane);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void execute() {
		launch();
	}
	public void limpaCampos() {
		textFieldNome.clear();
		textFieldCusto.clear();
		textFieldFabricante.clear();
		textFieldVelocidade.clear();
		textFieldPotencia.clear();
		tabelaMotor.getItems().clear();
		textFieldFindID.clear();
		labelLabelID.setVisible(false);
		labelID.setVisible(false);
		labelID.setText("");
	}
	public void listarMotor() {
		List<Motor> listaMotor = new motorDAO().listAll();
		   for (Motor motor : listaMotor) {
	            Motor mot = new Motor(motor.getId(), motor.getNome(), motor.getFabricante(),
	                    motor.getCusto(), motor.getVelocidade(), motor.getPotencia());
	            listTabelaMotor.add(mot);
	        }
		    columnId.setCellValueFactory(new PropertyValueFactory<Motor, Integer>("Id"));
	        columnNome.setCellValueFactory(new PropertyValueFactory<Motor, String>("Nome"));
	        columnFabrica.setCellValueFactory(new PropertyValueFactory<Motor, String>("Fabricante"));
	        columnCusto.setCellValueFactory(new PropertyValueFactory<Motor, Double>("Custo"));
	        columnVelocidade.setCellValueFactory(new PropertyValueFactory<Motor, Long>("Velocidade"));
	        columnPotencia.setCellValueFactory(new PropertyValueFactory<Motor, Integer>("Potencia"));
	        tabelaMotor.setItems(listTabelaMotor);
	}
	public Motor pegaDados() {
		return new Motor(textFieldNome.getText(), textFieldFabricante.getText(),
				Double.valueOf(textFieldCusto.getText()), Long.valueOf(textFieldVelocidade.getText()),
				Integer.parseInt(textFieldPotencia.getText()));
	}
	public Motor pegaDadosId() {
		return new Motor(Integer.parseInt(labelID.getText()),textFieldNome.getText(), textFieldFabricante.getText(),
				Double.valueOf(textFieldCusto.getText()), Long.valueOf(textFieldVelocidade.getText()),
				Integer.parseInt(textFieldPotencia.getText()));
	}
}
