package model.repository;

import java.util.List;

import model.domain.human.Human;

public interface IHumanRepository {
		 
	 public Human getHuman(final String dna);
	 
	 public Human saveHuman(final Human human);
	 
	 public List<Object> getHumanCount();

}
