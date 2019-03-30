package it.polito.tdp.spellchecker.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;

public class TestClass {

	public static void main(String[] args) {
		TestClass test = new TestClass();
		test.run2();

	}

	public void run2() {
		
		long num = 214362801;
		Double d = new Double(num);
		d = d/1000000000;
		System.out.println(String.format("%.9f", d));
//		num = num/1000000000;
//		System.out.println(Long.toString(num));
		
		
//		System.out.println(String.format("%.9f", new Float(num/1000000000)));
	}
	public void run() {

		Dictionary dict = new Dictionary();
		String language = "Italian";
		dict.loadDictionary(language);

//		System.out.println(dict.toString());

		List<String> inputTextList = new ArrayList<String>();
		inputTextList.add("ciao");
		inputTextList.add("come");
		inputTextList.add("ciao");
		inputTextList.add("come");
		inputTextList.add("ciao");
		inputTextList.add("stai");
		inputTextList.add("staii");
		inputTextList.add("figa");
		inputTextList.add("bella");
		inputTextList.add("donna");
		List<RichWord> res = dict.spellCheckText(inputTextList);
//		System.out.println("caricata la lista");
//		
//		System.out.println("\n\n\n\n");
//		System.out.println("lista immessa:\n");
//		System.out.println(list);
//		System.out.println("lista filtrata");
//		System.out.println(res);
//		
//		
//		
//		//.stream().filter(a->a.isCorrect()).collect(Collectors.toList())
//		System.out.println("stampo solo le corrette");
//		System.out.println(res.stream().filter(a->a.isCorrect()).collect(Collectors.toList()));
//		System.out.println("Stampo solo quelle errate");
//		System.out.println(res.stream().filter(a->!a.isCorrect()).collect(Collectors.toList()));

//		Dictionary inDictionary = new Dictionary(res);
		Dictionary inDictionary = new Dictionary(dict.spellCheckText(inputTextList));
		System.out.println(inDictionary.getCorrect());
		System.out.println(inDictionary.getWrong());

		
		
	}
}
