package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Dictionary {

	private List<RichWord> dict;
	private String language = "";

	public Dictionary() {
		this.dict = new ArrayList<RichWord>();
	}

	public Dictionary(List<RichWord> listWords) {
		this.dict = listWords;
	}

	public void loadDictionary(String language) {

		String languageFile = "rsc/" + language + ".txt";
		this.language = language;
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

		SortedSet<String> inputTextSet = new TreeSet<String>(inputTextList);
		List<RichWord> res = new LinkedList<RichWord>();

		for (String word : inputTextSet) {
			if (dict.contains(new RichWord(word)))
				res.add(new RichWord(word, true));
			else
				res.add(new RichWord(word, false));

		}

		return res;

	}

	public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {

		SortedSet<String> inputTextSet = new TreeSet<String>(inputTextList);
		List<RichWord> res = new LinkedList<RichWord>();
		boolean find = false;

		for (String inString : inputTextSet) {
			find = false;
			for (RichWord dictWord : dict) {
				if (inString.equals(dictWord.getWord())) {
					res.add(dictWord);
					find = true;
					break;
				}
			}
			if (!find)
				res.add(new RichWord(inString, false));
		}

		return res;

	}

	public List<RichWord> spellCheckTextDicotomic(List<String> inputTextList) {

		SortedSet<String> inputTextSet = new TreeSet<String>(inputTextList);
		List<RichWord> res = new LinkedList<RichWord>();

		for (String inWord : inputTextSet)
			res.add(dicotomicSearch(inWord, 0, dict.size() - 1));

		return res;

	}

	public RichWord dicotomicSearch(String inWord, int p, int q) {

		int k = (p + q) / 2;
		String dictWord = this.dict.get(k).getWord();
//		System.out.format("in ingresso: %s con diz: %s\n", inWord, dictWord);
		// se ho trovato la parola esco
		if (dictWord.equals(inWord))
			return this.dict.get(k);
		// se sono arrivato alla fine senza trovare la parola che cercavo
		if (p >= q)
			return new RichWord(inWord, false);

		if (dictWord.compareTo(inWord) > 0) // parola piu piccola
			return dicotomicSearch(inWord, p, k - 1);
		else // parola piu grande
			return dicotomicSearch(inWord, k + 1, q);

	}

	public List<RichWord> getCorrect() {
		return this.dict.stream().filter(a -> a.isCorrect()).collect(Collectors.toList());
	}

	public List<RichWord> getWrong() {
		return this.dict.stream().filter(a -> !a.isCorrect()).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return String.format("Dictionary [dict=%s]\n", dict);
	}

	public void clear() {
		this.dict.clear();
		this.language = "";

	}

	public String getLanguage() {
		return this.language;
	}

}
