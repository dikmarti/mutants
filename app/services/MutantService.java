package services;

import model.domain.Mutant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.*;

public class MutantService {
	
	@Inject
	public MutantService() {
		super();		
	}

	public boolean isMutant(Mutant mutant) {
		boolean result = false;
		String[] adnToVerify = mutant.getAdn();
		
		String patternResult = "";
		Pattern pattern = Pattern.compile(patternResult);
		
		for(String adnLine: adnToVerify) {
			
			Matcher matcher = pattern.matcher(adnLine);
			
			result = matcher.find();
			
		}
		
		return result;
	}

}
