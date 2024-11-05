package com.test.client_demo;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class ClientDemoController {

	private final RestClient restClient;
	
	private final WebClient webClient;
	
	private static final Logger log = Logger.getLogger(ClientDemoController.class.getName());

	// instanciando RestClient, 
	public ClientDemoController(RestClient.Builder builder, WebClient.Builder webClientBuilder) {
		this.restClient = builder.baseUrl("http://localhost:8081").build();
		
		this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
	}

	@GetMapping("client")
	public String client() {

//		log.log(Level.SEVERE, "error log");
//        log.log(Level.WARNING, "warning log");
//        log.log(Level.INFO, "info log");
//        logger.log(Level.FINE, "debug log");
//        logger.log(Level.FINER, "trace log");
		
		log.info("Request Thread {" + Thread.currentThread().threadId() + "} - {" + Thread.currentThread().getName()+"}");

		this.restClient.get().uri("/demo-service").retrieve().toBodilessEntity();

		log.info("Response Thread {" + Thread.currentThread().threadId() + "} - {" + Thread.currentThread().getName() + "}");
		
		return Thread.currentThread().toString();

	}
	
	@GetMapping("client2")
	public String client2() {

		log.info("Request Thread {" + Thread.currentThread().threadId() + "} - {" + Thread.currentThread().getName()+"}");

		Mono<Void> result = this.webClient.get()
				.uri("/demo-service")
				.exchangeToMono(clientResponse -> {
					log.info("Response Thread {" + Thread.currentThread().threadId() + "} - {" + Thread.currentThread().getName() + "}");
					return Mono.empty();
				});

		result.subscribe();
		
		return Thread.currentThread().toString();

	}

}
