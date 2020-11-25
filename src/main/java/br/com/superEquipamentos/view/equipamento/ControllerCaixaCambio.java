package br.com.superEquipamentos.view.equipamento;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JTextField;

import br.com.superEquipamentos.dao.caixaCambioDAO;
import br.com.superEquipamentos.model.CaixaCambio;
import br.com.superEquipamentos.util.NumeroDocument;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerCaixaCambio extends Application implements Initializable {
	private ObservableList<CaixaCambio> listTabelaCaixaCambio = FXCollections.observableArrayList();

    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldCusto;

    @FXML
    private Button buttonInserir;

    @FXML
    private TextArea textAreaListCaixaCambio;

    @FXML
    private TextField textFieldFindID;

    @FXML
    private Label labelLabelID;

    @FXML
    private Label labelID;

    @FXML
    private Button buttonAlterar;

    @FXML
    private Button buttonDeletar;
    
    @FXML
    private TableView<CaixaCambio> tabelaCaixaCambio;

    @FXML
    private TableColumn<CaixaCambio, Integer> columnId;

    @FXML
    private TableColumn<CaixaCambio, String> columnNome;

    @FXML
    private TableColumn<CaixaCambio, String> columnFabrica;

    @FXML
    private TableColumn<CaixaCambio, Double> columnCusto;

    @FXML
    private TableColumn<CaixaCambio, String> columnTipo;

    @FXML
    private TableColumn<CaixaCambio, String> columnDescricao;


    @FXML
    private TextField textFieldDescricao;

    @FXML
    private TextField textFieldFabricante;
    @FXML
    private ComboBox<String> comboBoxTipo;

    @FXML
    void AlterarCaixaCambio(ActionEvent event) {
		CaixaCambio caixaCambio= pegaDadosId();
    	if(String.valueOf(caixaCambio.getId()) == null || String.valueOf(caixaCambio.getId()) =="") {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Alerta");
    		alert.setHeaderText("Caixa de cambio não selecionado");
    		alert.setContentText("Selecione uma caixa de cambio para alterar");
    	}
    	else {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
        	alert.setTitle("Alterar Caixa de Cambio");
        	alert.setHeaderText("Você está prestes a alterar uma caixa de cambio");
        	alert.setContentText("Tem certeza que deseja altterar o caixa de Cambio?");
        	Optional<ButtonType> result = alert.showAndWait();
        	if (result.get() == ButtonType.OK){	
        	caixaCambio.setTipo(caixaCambio.getTipo().equals("Automatico") ? "A" : "M");
    		limpaCampos();
    		new caixaCambioDAO().alterar(caixaCambio);
    		listarCaixaCambio();        	
    		}
    	}
    }
    @FXML
    void DeletarCaixaCambio(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Deletar Caixa Cambio");
    	alert.setHeaderText("Você está prestes a deletar uma caixa de cambio");
    	alert.setContentText("Tem certeza que deseja deletar a caixa de cambio?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		CaixaCambio caixaCambio= pegaDadosId();
    		new caixaCambioDAO().deletar(caixaCambio.getId());
        	limpaCampos();
        	listarCaixaCambio();
    	}
    }
    @FXML
    void buscarCaixaCambio(ActionEvent event) {
    	String idString = textFieldFindID.getText();
		CaixaCambio caixaCambio = null;
		if (!idString.equals("")) {
			try {
				int id = Integer.valueOf(idString);
				caixaCambio = new caixaCambioDAO().findByID(id);
			} catch (Exception e) {
			}
			if (caixaCambio != null) {
				labelLabelID.setVisible(true);
				labelID.setVisible(true);
				labelID.setText(caixaCambio.getId() + "");
				textFieldNome.setText(caixaCambio.getNome());
				textFieldFabricante.setText(caixaCambio.getFabricante());
				textFieldCusto.setText(caixaCambio.getCusto()+ "");
				comboBoxTipo.setValue(caixaCambio.getTipo().equals("A") ? "Automatico" : "Manual");
				textFieldDescricao.setText(caixaCambio.getDescricao());
			}
		}
		textFieldFindID.clear();
    }

    @FXML
    void textoCusto() {
    	JTextField campo = new JTextField();
    	campo.setDocument(new NumeroDocument(2,4));
    }
    @FXML
    void inserirCaixaCambio(ActionEvent event) {
    	CaixaCambio caixaCambio = pegaDados();
    	caixaCambio.setTipo(tipoControle(caixaCambio.getTipo()));
		limpaCampos();
		new caixaCambioDAO().inserir(caixaCambio);
		listarCaixaCambio();
    }
    public CaixaCambio pegaDados() {
		return new CaixaCambio(textFieldNome.getText(), textFieldFabricante.getText(),
				Double.valueOf(textFieldCusto.getText()), comboBoxTipo.getValue(),
				textFieldDescricao.getText());
	}
	public CaixaCambio pegaDadosId() {
		return new CaixaCambio(Integer.parseInt(labelID.getText()),textFieldNome.getText(), textFieldFabricante.getText(),
				Double.valueOf(textFieldCusto.getText()), comboBoxTipo.getValue(),
				textFieldDescricao.getText());
	}
	public void listarCaixaCambio() {
		List<CaixaCambio> listaCaixaCambio = caixaCambioDAO.listAll();
        if (!listaCaixaCambio.isEmpty()) {
		   for (CaixaCambio caixaCambio : listaCaixaCambio) {
	            CaixaCambio caixaC = new CaixaCambio(caixaCambio.getId(), caixaCambio.getNome(), caixaCambio.getFabricante(),
	                    caixaCambio.getCusto(), caixaCambio.getTipo(), caixaCambio.getDescricao());
	            listTabelaCaixaCambio.add(caixaC);
	        }
     	    columnId.setCellValueFactory(new PropertyValueFactory<CaixaCambio, Integer>("Id"));
	        columnNome.setCellValueFactory(new PropertyValueFactory<CaixaCambio, String>("Nome"));
	        columnFabrica.setCellValueFactory(new PropertyValueFactory<CaixaCambio, String>("Fabricante"));
	        columnCusto.setCellValueFactory(new PropertyValueFactory<CaixaCambio, Double>("Custo"));
	        columnTipo.setCellValueFactory(new PropertyValueFactory<CaixaCambio, String>("Tipo"));
	        columnDescricao.setCellValueFactory(new PropertyValueFactory<CaixaCambio, String>("Descricao"));
	        tabelaCaixaCambio.setItems(listTabelaCaixaCambio);
        }
	}
	public void limpaCampos() {
		textFieldNome.clear();
		textFieldCusto.clear();
		textFieldFabricante.clear();
		textFieldDescricao.clear();
		textFieldFindID.clear();
		tabelaCaixaCambio.getItems().clear();
		labelLabelID.setVisible(false);
		labelID.setVisible(false);
		labelID.setText("");
	}
	
	ObservableList<String> options = 
		    FXCollections.observableArrayList(
		        "Automatico",
		        "Manual"
		    );
	
	private String tipoControle(String tipo) {
		if(tipo == "Automatico") {
			return "A";
		}
		if(tipo == "Manual") {
			return "M";
		}
		return null;
	}
	public void initialize(URL location, ResourceBundle resources) {
		listarCaixaCambio();
		comboBoxTipo.getItems().addAll(options);
	}
	@Override
	public void start(Stage stage) {
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("CaixaCambio.fxml"));
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
	
}

