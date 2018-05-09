package it.polito.tdp.dizionariograph.bean;

import java.util.Map;

public class IdMapWord {
	
	private Map<Integer, Word> map;
	
	public Word get(Word word) {
		Word old = map.get(word.getId());
		if(old == null) {
			map.put(word.getId(), word);
			return word;
		}
		return old;
	}
	
	public void put(int id, Word w) {
		map.put(id, w);
	}

}