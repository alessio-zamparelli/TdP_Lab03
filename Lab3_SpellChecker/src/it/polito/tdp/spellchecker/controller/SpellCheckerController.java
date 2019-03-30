package it.polito.tdp.spellchecker.controller;

import javafx.scene.control.Label;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.collections.FXCollections;
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
    private Label lblErrors;

    @FXML
    private Label lblComplTime;

	@FXML
	void doSpellCheck(ActionEvent event) {
		
		long timeStart = System.nanoTime();
		// lingua selezionata
		String selectedLang = choiceLanguage.getSelectionModel().getSelectedItem();
		// controlli sulla correttezza della lingua selezionata
		if (selectedLang == null) {
			txtText.setText("Selezionare prima la lingua");
			return;
		}

		// controllo se il dizionario selezionato è gia stato caricato
		if (!model.getLanguage().equals(selectedLang)) {
			model.clear();
			model.loadDictionary(selectedLang);
			System.out.println("caricato il dizionario " + selectedLang);
		}

//		List<String> strings = Arrays.asList(txtText.getText().split(" "));
//		List<String> strings = new ArrayList<String>(Arrays.asList(txtText.getText().split(" ")));
//		strings.stream().forEach(a->a.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", ""));
//		System.out.println(strings);
		List<String> words = new ArrayList<String>();
//		strings.forEach(a->words.add(a));
//		txtText.setText(strings.toString());
		for (String s : txtText.getText().split(" ")) {
			s = s.replaceAll("[.,\\/#!$%\\^&£\\*;:{}=\\-_`~()\\[\\]\"]", "");
			words.add(s);
		}
//		txtText.setText(words.toString());

		List<RichWord> res = model.spellCheckText(words);

		List<String> wrongWords = res.stream()
				.filter(a -> !a.isCorrect())
				.map(a -> a.getWord())
				.collect(Collectors.toList());

		for (String string : wrongWords) {
			txtResult.appendText(string + "\n");
		}
		
		lblErrors.setText(String.format("The text contains %d errors", wrongWords.size()));
		
		Double executionTime = new Double(System.nanoTime()-timeStart);
		executionTime = executionTime/1000000000;
		
		lblComplTime.setText(String.format("Spell check completed in %.9f seconds", executionTime));
		return;

	}

	@FXML
	void doClearText(ActionEvent event) {

		txtResult.clear();
		txtText.clear();
		model.clear();
		lblErrors.setText("The text contains 0 errors");

	}




    @FXML
    void initialize() {
        assert choiceLanguage != null : "fx:id=\"choiceLanguage\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtText != null : "fx:id=\"txtText\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblErrors != null : "fx:id=\"lblErrors\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblComplTime != null : "fx:id=\"lblComplTime\" was not injected: check your FXML file 'SpellChecker.fxml'.";
		
        choiceLanguage.setItems(FXCollections.observableArrayList(avaibleLanguages));

    }
	public void setModel(Dictionary model) {
		this.model = model;
	}
}
