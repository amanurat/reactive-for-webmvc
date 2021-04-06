package demo.client;

import demo.Person;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
public class Step3a {

	private static WebClient client = WebClient.create("http://localhost:8081");


	public static void main(String[] args) {

		client.get().uri("/persons/events")
				.retrieve()
				.bodyToFlux(Person.class)
				.doOnNext(person -> log.info("Got " + person))
				.take(4)
				.blockLast();
	}

}
