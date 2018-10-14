package model.domain.human;

import javax.inject.Inject;

public class HumanRequestModel {

	private String dna[];

	@Inject
	public HumanRequestModel() {
		super();		
	}
	
	public String[] getDna() {
		return dna;
	}

	public void setDna(String[] dna) {
		this.dna = dna;
	}
	
	
}
