package model.domain.human;

public class StatsResponseModel {

	private String count_mutant_dna;
	private String count_human_dna;
	private float ratio;
	
	public String getCount_mutant_dna() {
		return count_mutant_dna;
	}
	public void setCount_mutant_dna(String count_mutant_dna) {
		this.count_mutant_dna = count_mutant_dna;
	}
	public String getCount_human_dna() {
		return count_human_dna;
	}
	public void setCount_human_dna(String count_human_dna) {
		this.count_human_dna = count_human_dna;
	}
	public float getRatio() {
		return ratio;
	}
	public void setRatio(float ratio) {
		this.ratio = ratio;
	}
	
}
