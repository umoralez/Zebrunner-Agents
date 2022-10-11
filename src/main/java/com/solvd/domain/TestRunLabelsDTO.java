package com.solvd.domain;

import java.util.ArrayList;

public class TestRunLabelsDTO {
	private ArrayList<LabelItemDTO> items;

	public TestRunLabelsDTO(ArrayList<LabelItemDTO> items) {
		this.items = items;
	}

	public ArrayList<LabelItemDTO> getItems() {
		return items;
	}

	public void setItems(ArrayList<LabelItemDTO> items) {
		this.items = items;
	}
}