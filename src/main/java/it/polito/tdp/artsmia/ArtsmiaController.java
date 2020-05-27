package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola artisti connessi\n");
    	List<Adiacenza> result = model.getAdiacenze();
    	if(result != null) {
    		for(Adiacenza a: result)
    			txtResult.appendText(String.format("Artista %d Artista %d %d\n", a.getIdArtista1(), a.getIdArtista2(), a.getPeso()));
    	}
    	else
    		txtResult.appendText("Grafo non creato!\n");
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola percorso");
    	int id;
    	try {
    		id = Integer.parseInt(txtArtista.getText()); 
    	} catch(NumberFormatException e) {
    		txtResult.appendText("Non hai inserito un numero intero!");
    		return;
    	}
    	if(model.grafoContiene(id)) {
    		List<Integer> percorso = model.trovaPercorso(id);
    		txtResult.appendText("Percorso più lungo: "+percorso.size()+"\n");
    		for(Integer i: percorso)
    			txtResult.appendText(i+"\n");
    	}
    	else
    		txtResult.appendText("ID non corrispondente a nessun artista");
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Crea grafo");
    	String ruolo = boxRuolo.getValue();
    	if(ruolo == "" || ruolo == null) {
    		txtResult.appendText("Devi selezionare un ruolo!");
    		return;
    	}
    	model.creaGrafo(ruolo);
    	txtResult.appendText(String.format("\nGrafo creato con %d vertici e %d archi!\n", model.nVertici(), model.nArchi()));
    	btnArtistiConnessi.setDisable(false);
    	btnCalcolaPercorso.setDisable(false);
    }

    public void setModel(Model model) {
    	this.model = model;
    	setBoxRuolo();
    	btnCalcolaPercorso.setDisable(true);
    	btnArtistiConnessi.setDisable(true);
    }
    
    private void setBoxRuolo() {
		this.boxRuolo.getItems().addAll(model.getAllRoles());
	}

	@FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
