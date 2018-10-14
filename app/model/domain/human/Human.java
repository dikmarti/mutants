package model.domain.human;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name = "Human.findByDna", query = "SELECT h FROM Human h WHERE h.dna = :dna")
})

@Entity
@Table(name="human")
public class Human {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private String dna;
	
	@Column
	private int mutant;
	
	public String getDna() {
		return dna;
	}
	public void setDna(String dna) {
		this.dna = dna;
	}
	public int getMutant() {
		return mutant;
	}
	public void setMutant(int mutant) {
		this.mutant = mutant;
	}
	
}
