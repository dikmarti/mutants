package model.repository;

import play.db.jpa.JPAApi;

import javax.inject.*;
import javax.persistence.*;
import java.util.concurrent.*;
import java.util.List;

@Singleton
public class JPARepository {
    private JPAApi jpaApi;
    private DatabaseExecutionContext executionContext;
    
    @Inject
    public JPARepository(JPAApi api, DatabaseExecutionContext executionContext) {
        this.jpaApi = api;
        this.executionContext = executionContext;
    }
    
    public JPAApi getJpaApi() {
    	return jpaApi;
    }   
    
}