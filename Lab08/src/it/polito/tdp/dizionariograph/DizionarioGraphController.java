/**
 * Sample Skeleton for 'DizionarioGraph.fxml' Controller Class
 */

package it.polito.tdp.dizionariograph;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionariograph.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioGraphController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="TextLenght"
    private TextField TextLength; // Value injected by FXMLLoader

    @FXML // fx:id="TextWord"
    private TextField TextWord; // Value injected by FXMLLoader

    @FXML // fx:id="handleDegreeMax"
    private Button handleDegreeMax; // Value injected by FXMLLoader

    @FXML // fx:id="TextLog"
    private TextArea TextLog; // Value injected by FXMLLoader

    @FXML
    void handleGraph(ActionEvent event) {
    	int length;
    	try {
			length = Integer.parseInt(TextLength.getText());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return;
		}
    	
    	model.createGraph(length);
    }

    @FXML
    void handleNeighbours(ActionEvent event) {
    	TextLog.clear();
    	List<String> neighbours = model.displayNeighbours(TextWord.getText());
    	if(neighbours.isEmpty())
    		TextLog.appendText("Premere tasto GENERA GRAFO prima");
    	else {
	    	for(String a: neighbours)
	    		TextLog.appendText(a+"\n");
    	}
    }

    @FXML
    void handleReset(ActionEvent event) {
    	TextLog.clear();
    	TextWord.clear();
    	TextLength.clear();

    }
    
    @FXML
    void handleDegreeMax(ActionEvent event) {
    	TextLog.appendText("Il vertice di grado massimo e': "+model.getVertMax()+" con valore "+model.findMaxDegree());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert TextLength != null : "fx:id=\"TextLength\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert TextWord != null : "fx:id=\"TextWord\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert handleDegreeMax != null : "fx:id=\"handleDegreeMax\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert TextLog != null : "fx:id=\"TextLog\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
	}
}
