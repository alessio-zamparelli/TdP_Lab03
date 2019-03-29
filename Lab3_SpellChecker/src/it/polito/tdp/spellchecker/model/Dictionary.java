package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Dictionary {

	private List<RichWord> dict;

	public Dictionary() {
		this.dict = new ArrayList<RichWord>();
	}

	public void loadDictionary(String language) {

		String languageFile = "rsc/" + language + ".txt";

		try {

			FileReader fr = new FileReader(languageFile);
			BufferedReader br = new BufferedReader(fr);
			String word;
			while ((word = br.readLine()) != null) {
				dict.add(new RichWord(word, true));
			}
			br.close();

		} catch (IOException e) {
			System.out.println("Errore nella lettura del file");
		}
	}

	/*
	 * Questo metodo esegue il controllo ortografico sul testo in input e
	 * restituisce una lista di richword con il valore corretto segnato
	 */
	public List<RichWord> spellCheckText(List<String> inputTextList) {

		Collections.sort(inputTextList);
		Set<String> inputTextSet = new HashSet<String>(inputTextList);
		List<RichWord> res = new LinkedList<RichWord>();

		for (String word : inputTextSet) {
			if (dict.contains(new RichWord(word)))
				res.add(new RichWord(word, true));
			else
				res.add(new RichWord(word, false));

		}

		return res;

	}

	public List<RichWord> getCorrect() {
		return this.dict.stream().filter(a->a.isCorrect()).collect(Collectors.toList());
	}

	public List<RichWord> getWrong() {
		return this.dict.stream().filter(a->!a.isCorrect()).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return String.format("Dictionary [dict=%s]\n", dict);
	}

}
