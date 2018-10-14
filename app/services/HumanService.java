package services;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.domain.human.Human;
import model.domain.human.StatsResponseModel;
import model.repository.JPARepository;


public class HumanService {
	
	private static int SECUENCE_NUMBER = 4;    	 
	private static int MATRIZ_LENGTH = 6;
	private static int MIN_SEQUENCE_MUTANT = 1;
	private static String SECUENCE_LETTERS = "ATCG";
	
	private final JPARepository jpaRepo;
	
	@Inject
	public HumanService(JPARepository jpaRepository) {
		super();	
		this.jpaRepo = jpaRepository;
	}

	 public static boolean isMutant(String[] dna) {
		 
		 	String[] adnToVerifyRows = dna;
		 	
	    	char position = '0';
	    	int counter = 0;

	    	Map<String, Boolean> sequence = new HashMap<String, Boolean>();    	
		
			for(int r = 0; r < MATRIZ_LENGTH; r++) {
	    		 for(int c = 0; c < MATRIZ_LENGTH; c++) {
	    			 position = adnToVerifyRows[r].charAt(c);
	    			 
	    			 if(SECUENCE_LETTERS.indexOf(position) == -1) {
	    				 // Solo puede contener las letras de SECUENCE_LETTERS
	    				 return false;
	    			 }
	    			
	    			 // Verifica horizontalmente
	    	
					if(!sequence.containsKey("r_" + r) && (c + SECUENCE_NUMBER <= MATRIZ_LENGTH) && position == adnToVerifyRows[r].charAt(c+MIN_SEQUENCE_MUTANT) &&
	    							 position == adnToVerifyRows[r].charAt(c+2) &&
	    									 position == adnToVerifyRows[r].charAt(c+3)) { 
						
						 
						 
	    				 counter ++; 
	    				 sequence.put("r_" + r, true);
	    			 }

	    			 // Verifica verticalmente
	    			 if(!sequence.containsKey("c_" + c) && (r + SECUENCE_NUMBER <= MATRIZ_LENGTH) && position == adnToVerifyRows[r+MIN_SEQUENCE_MUTANT].charAt(c) &&
							 position == adnToVerifyRows[r+2].charAt(c) &&
									 position == adnToVerifyRows[r+3].charAt(c)) {			 
	    				 	counter ++;	
	    				 	sequence.put("c_" + c, true);
	    			 }
	    			 
	    			 // Verifica Diagonal
	    			 if(c == r) {
	    				 //Diagonal central    				 
	    				 if(!sequence.containsKey("d_" + r + "_" + c) && (c + SECUENCE_NUMBER <= MATRIZ_LENGTH) && position == adnToVerifyRows[r+MIN_SEQUENCE_MUTANT].charAt(c+MIN_SEQUENCE_MUTANT) &&
	    						 position == adnToVerifyRows[r+2].charAt(c+2) &&
	    								 position == adnToVerifyRows[r+3].charAt(c+3)) {			 
	        				 	counter ++;		
	        				 	sequence.put("d_" + r + "_" + c, true);
	        			 }    				 
	    			 }
	    			 
	    			 if((c + SECUENCE_NUMBER <= MATRIZ_LENGTH)) {
	        			 position = adnToVerifyRows[r].charAt(c+MIN_SEQUENCE_MUTANT);
	    			 } else {
	    				 continue;
	    			 }

	    			 if(!sequence.containsKey("d_" + r + "_" + c) && (c + SECUENCE_NUMBER <= MATRIZ_LENGTH) && (r + SECUENCE_NUMBER <= MATRIZ_LENGTH) && position == adnToVerifyRows[r+MIN_SEQUENCE_MUTANT].charAt(c+2) &&
							 position == adnToVerifyRows[r+2].charAt(c+3) &&
									 position == adnToVerifyRows[r+3].charAt(c+4)) {			 
	    				 	counter ++;	
	    				 	sequence.put("d_" + r + "_" + c, true);
	    			 }
	    			 
	        	 }
	    	 }
	    	 
	    	 return counter > MIN_SEQUENCE_MUTANT;
	    	 
	     }
	 
	  public Human getHuman(final String dna) {
 	
		  return jpaRepo.getJpaApi().withTransaction(entityManager -> {
	            Query query = entityManager.createNamedQuery("Human.findByDna").setParameter("dna", dna);
	            List<Human> result = (List<Human>) query.getResultList();	            
	            
	            return result.size() == 0 ? null: result.get(0);
	      });
		  
	  }
	  
	  public Human saveHuman(final Human human) {
		  
		  Human humanFinded = getHuman(human.getDna());
		  
		  if(humanFinded != null) {
			  return null;
		  }
		  
		  return jpaRepo.getJpaApi().withTransaction(entityManager -> {
	           entityManager.persist(human);	           
	           return human;
	      });

	  }
	  
	  public StatsResponseModel getStats() {
		  return jpaRepo.getJpaApi().withTransaction(entityManager -> {
	            Query query = entityManager.createNativeQuery("SELECT count(*) FROM Human GROUP BY mutant ORDER BY mutant ASC");
	            
	            List<Object> resultList = (List<Object>)query.getResultList();
	            
	            // Cant no mutant
	            Object human = resultList.get(0);
	            
	            // Cant mutant
	            Object mutant = resultList.get(1);
	            System.out.println(resultList);
	            
	            StatsResponseModel responseModel = new StatsResponseModel();
	            responseModel.setCount_human_dna(String.valueOf(human));
	            responseModel.setCount_mutant_dna(String.valueOf(mutant));   
	            responseModel.setRatio(((BigInteger)mutant).floatValue()/((BigInteger)(human)).floatValue());
	            	            
	            return responseModel;
	      });
	  }

}
