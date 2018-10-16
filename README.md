[<img src="https://img.shields.io/travis/playframework/play-java-starter-example.svg"/>](https://travis-ci.org/playframework/play-java-starter-example)

# mutant-challenge


## Requirements

```
Java SE 1.8
sbt (https://www.scala-sbt.org/download.html) 
```

## Running App

```
From the root project directory execute: sbt run
```

## Execute App

```
http://localhost:9000/
```
## Controllers

- HumanController:
```
Shows if an ADN belongs to mutant

  Method: POST
  Url: http://localhost:9000/mutant 
  Input: {"dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","TTTTTA","TCACTG"]}
  Output: - HTTP 200-OK (If the human it's a mutant)
	  - 403-Forbiden (If the human it's not a mutant)
			
Shows statdistics of humans and mutants

  Method: GET
  Url: http://localhost:9000/stats
  Output: {"count_mutant_dna":"2","count_human_dna":"2","ratio":1.0} 
```
