package controllers;

import javax.inject.*;

import javax.persistence.*;

import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.Scheduler;
import akka.actor.ActorSystem;
import play.*;
import play.mvc.*;
import play.mvc.Http.RequestBody;
import play.libs.Json;

import java.util.concurrent.Executor;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.util.List;

import scala.concurrent.java8.*;
import scala.concurrent.duration.Duration;
import scala.concurrent.ExecutionContextExecutor;

import services.HumanService;
import model.domain.human.Human;
import model.domain.human.HumanRequestModel;
import model.domain.human.StatsResponseModel;

import model.repository.JPARepository;;


/**
 * This controller contains an action that demonstrates how to write
 * simple asynchronous code in a controller. It uses a timer to
 * asynchronously delay sending a response for 1 second.
 */
@Singleton
public class HumanController extends Controller {

    private static final String NOT_MUTANT_RESPONSE = "HTTP 403-Forbidden";
	private static final String MUTANT_RESPONSE = "HTTP 200-OK";
	private final ActorSystem actorSystem;
    private final ExecutionContextExecutor exec;
    private final HumanService humanService;
    private final JPARepository jpaRepo;

    @Inject
    public HumanController(ActorSystem actorSystem, ExecutionContextExecutor exec, HumanService humanService, JPARepository jpaRepo) {
      this.actorSystem = actorSystem;
      this.exec = exec;
      this.humanService = humanService;
      this.jpaRepo = jpaRepo;
    }

    public CompletionStage<Result> mutant() {
        return getFutureMutant(1, TimeUnit.SECONDS).thenApplyAsync(Results::ok, exec);
    }

    @BodyParser.Of(BodyParser.Json.class)
    private CompletionStage<String> getFutureMutant(long time, TimeUnit timeUnit) {
    	    	    	
    	RequestBody body = request().body();
    	JsonNode jsonNodeRequest = body.asJson();
    	HumanRequestModel humanRequestModel = Json.fromJson(jsonNodeRequest, HumanRequestModel.class);
    	
    	boolean isMutant = humanService.isMutant(humanRequestModel.getDna());
    
    	saveHuman(humanRequestModel.getDna(), isMutant);
    	
    	String response = getMutantResponse(isMutant);
    	
        CompletableFuture<String> future = new CompletableFuture<>();
        actorSystem.scheduler().scheduleOnce(
            Duration.create(time, timeUnit),
            () -> future.complete(response),
            exec
        );
        return future;
    }

	private String getMutantResponse(boolean isMutant) {
			
		String response = isMutant ? MUTANT_RESPONSE : NOT_MUTANT_RESPONSE;
		
		return response;		
	}
	
	public void saveHuman(String[] dna, boolean isMutant) {
		Human human = new Human();
		human.setDna(Arrays.toString(dna));
		human.setMutant(isMutant ? 1 : 0);
		
		humanService.saveHuman(human);
	}
	
	  public CompletionStage<Result> stats() {
	        return getFutureStats(1, TimeUnit.SECONDS).thenApplyAsync(Results::ok, exec);
	    }

	    private CompletionStage<String> getFutureStats(long time, TimeUnit timeUnit) {
	    	    	    	
	    	StatsResponseModel responseStats = humanService.getStats();
	    	
	       	CompletableFuture<String> future = new CompletableFuture<>();
	        actorSystem.scheduler().scheduleOnce(
	            Duration.create(time, timeUnit),
	            () -> future.complete(Json.toJson(responseStats).toString()),
	            exec
	        );
	        return future;
	    }
    


}
