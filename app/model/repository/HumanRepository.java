package model.repository;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;

import model.domain.human.Human;

public class HumanRepository implements IHumanRepository {

	private final JPARepository jpaRepo;
	
	@Inject
	public HumanRepository(JPARepository jpaRepo) {
		super();	
		this.jpaRepo = jpaRepo;
	}
	
	@Override
	public Human getHuman(final String dna) {
		 return jpaRepo.getJpaApi().withTransaction(entityManager -> {
	            Query query = entityManager.createNamedQuery("Human.findByDna").setParameter("dna", dna);
	            List<Human> result = (List<Human>) query.getResultList();	            
	            
	            return result.size() == 0 ? null: result.get(0);
	      });
	}

	@Override
	public Human saveHuman(final Human human) {
		 return jpaRepo.getJpaApi().withTransaction(entityManager -> {
	           entityManager.persist(human);	           
	           return human;
	      });
	}

	@Override
	public List<Object> getHumanCount() {
		return jpaRepo.getJpaApi().withTransaction(entityManager -> {
            Query query = entityManager.createNativeQuery("SELECT count(*) FROM Human GROUP BY mutant ORDER BY mutant ASC");
            
            return (List<Object>)query.getResultList();
      });
	}

}
