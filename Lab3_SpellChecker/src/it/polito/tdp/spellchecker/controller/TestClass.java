package it.polito.tdp.spellchecker.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;

public class TestClass {

	public static void main(String[] args) {
		TestClass test = new TestClass();
		test.run();
		
	}

	public void run() {
		
		Dictionary dict = new Dictionary();
		String language = "Italian";
		dict.loadDictionary(language);
		
//		System.out.println(dict.toString());
		
		List<String> list = new ArrayList<String>();
		list.add("ciao");
		list.add("come");
		list.add("ciao");
		list.add("come");
		list.add("ciao");
		list.add("stai");
		list.add("staii");
		list.add("figa");
		list.add("bella");
		list.add("donna");
		List<RichWord> res = dict.spellCheckText(list);
		System.out.println("caricata la lista");
		
		System.out.println("\n\n\n\n");
		System.out.println("lista immessa:\n");
		System.out.println(list);
		System.out.println("lista filtrata");
		System.out.println(res);
		
		//.stream().filter(a->a.isCorrect()).collect(Collectors.toList())
		System.out.println("stampo solo le corrette\n");
		System.out.println(res.stream().filter(a->a.isCorrect()).collect(Collectors.toList()));
		System.out.println("Stampo solo quelle errate");
		System.out.println(res.stream().filter(a->!a.isCorrect()).collect(Collectors.toList()));
		
		
	}
}
