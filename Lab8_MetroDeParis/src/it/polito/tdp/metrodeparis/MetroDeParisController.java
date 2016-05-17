package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class MetroDeParisController {

	private Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> cbPartenza;

    @FXML
    private ChoiceBox<String> cbArrivo;

    @FXML
    private Button btnPercorso;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	String a = cbPartenza.getValue();
    	String b = cbArrivo.getValue();
    	if(a.compareTo("")==0){
    		txtResult.setText("Scegliere fermata di partenza");
    		return;
    	}
    	if(b.compareTo("")==0){
    		txtResult.setText("Scegliere fermata di arrivo");
    		return;
    	}
    	Fermata A = model.getFermata(a);
    	Fermata B = model.getFermata(b);
    	List<Fermata> cammino = model.calcolaPercorso(A, B);
    	if(cammino==null){
    		txtResult.setText("Stazioni non connesse");
    		return;
    	}
    	txtResult.setText("Percorso: "+cammino);
    	int ore = model.getTime(cammino)/3600;
    	int minuti = (model.getTime(cammino)-ore*3600)/60;
    	int secondi = model.getTime(cammino)-ore*3600-minuti*60;
    	txtResult.appendText("\n\nTempo di percorrenza stimato: "+ore+":"+minuti+":"+secondi);
    }

    public void setModel(Model model){
    	this.model = model;
    	ObservableList<String> fermate = FXCollections.observableArrayList(model.caricaFermate());
        cbPartenza.setItems(fermate);
        cbArrivo.setItems(fermate);
    	model.createGraph();
//        model.calcolaCammini();
        btnPercorso.setDisable(false);
    }
    
    @FXML
    void initialize() {
        assert cbPartenza != null : "fx:id=\"cbPartenza\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert cbArrivo != null : "fx:id=\"cbArrivo\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        
    	cbPartenza.setValue("");
    	cbArrivo.setValue("");
    	btnPercorso.setDisable(true);
    	txtResult.setWrapText(true);
    }
    
}
