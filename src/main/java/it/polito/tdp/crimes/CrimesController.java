/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.tools.javac.util.List;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class CrimesController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<Adiacenza> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String categoria = this.boxCategoria.getValue();
    	int mese = this.boxMese.getValue();
    	this.model.creaGrafo(categoria,mese );
    	txtResult.appendText("GRAFO CREATO!"+"\n");
    	txtResult.appendText("# vertici = "+this.model.nVertici()+"\n");
    	txtResult.appendText("# archi = "+this.model.nArchi()+"\n");
    	
    	
    	for (Adiacenza a : this.model.getArchiFiltrati(categoria, mese)) {
    		txtResult.appendText(a.getReato1()+" "+a.getReato2()+" --> "+a.getPeso()+"\n");
    	}
    	
    	this.boxArco.getItems().addAll(this.model.getArchiFiltrati(categoria, mese));
    	
    }
    
    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	String sorgente = this.boxArco.getValue().getReato1();
    	String destinazione = this.boxArco.getValue().getReato2();
    	txtResult.appendText(""+this.model.trovaPercorso(sorgente, destinazione));
    	
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxCategoria.getItems().addAll(this.model.getCategorie());
    	ArrayList<Integer> mesi = new ArrayList<Integer>();
    	for(int i = 1; i<=12; i++) {
    		mesi.add(i);
    	}
    	this.boxMese.getItems().addAll(mesi);
    }
}
