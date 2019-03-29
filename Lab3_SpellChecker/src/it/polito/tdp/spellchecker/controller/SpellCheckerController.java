package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class SpellCheckerController {

	private static final String[] avaibleLanguages = new String[] { "Italian", "English" };
	private Dictionary model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ChoiceBox<String> choiceLanguage;

	@FXML
	private TextArea txtText;

	@FXML
	private TextArea txtResult;

	@FXML
	void doSpellCheck(ActionEvent event) {
		
		// lingua selezionata
		String selectedLang = choiceLanguage.getSelectionModel().getSelectedItem();
		// controlli sulla correttezza della lingua selezionata
		if (selectedLang==null) {
			txtText.setText("Selezionare prima la lingua");
			return;
		}
		
//		List<String> strings = Arrays.asList(txtText.getText().split(" "));
//		List<String> strings = new ArrayList<String>(Arrays.asList(txtText.getText().split(" ")));
//		strings.stream().forEach(a->a.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", ""));
//		System.out.println(strings);
		List<String> words = new ArrayList<String>();
//		strings.forEach(a->words.add(a));
//		txtText.setText(strings.toString());
		for(String s : txtText.getText().split(" ")) {
			s=s.replaceAll("[.,\\/#!$%\\^&£\\*;:{}=\\-_`~()\\[\\]\"]", "");
			words.add(s);
		}
		txtText.setText(words.toString());
		
		model.loadDictionary(selectedLang);
		
		List<RichWord> res = model.spellCheckText(words);
		
		txtText.setText(res.toString());
		
		/*
		 * va messo il discorso dizionario giusto dizionario sbagliato
		 */
		
		txtResult.setText(model.getCorrect());
		
		return;
		
		
	}
	@FXML
	void doClearText(ActionEvent event) {

	}

	

	@FXML
	void initialize() {
		assert choiceLanguage != null : "fx:id=\"choiceLanguage\" was not injected: check your FXML file 'SpellChecker.fxml'.";
		assert txtText != null : "fx:id=\"txtText\" was not injected: check your FXML file 'SpellChecker.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SpellChecker.fxml'.";

		choiceLanguage.setItems(FXCollections.observableArrayList(avaibleLanguages));

	}

	public void setModel(Dictionary model) {
		this.model = model;
	}
}
