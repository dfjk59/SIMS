package com.sims.common.util;

import java.util.List;
import java.util.Set;

public class ExtJsonResult<T> {
	
	private List<T> lists;
	private Set<T> sets;
	
	public ExtJsonResult(List<T> lists) {
		this.lists = lists;
	}
	
	public ExtJsonResult(Set<T> sets) {
		this.sets = sets;
	}

	public List<T> getLists() {
		return lists;
	}

	public Set<T> getSets() {
		return sets;
	}



}
