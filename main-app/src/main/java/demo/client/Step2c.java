package demo.client;

import demo.Person;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;

@Slf4j
public class Step2c {

	private static final Logger logger = LoggerFactory.getLogger(Step2c.class);

	private static WebClient client = WebClient.create("http://localhost:8081?delay=2");


	public static void main(String[] args) {

		Instant start = Instant.now();

		Flux.range(1, 3)
				.doOnNext(i -> log.info("Getting id=" + i))
				.flatMap(i -> client.get().uri("/person/{id}", i).retrieve().bodyToMono(Person.class))
				.doOnNext(person -> log.info("Got " + person))
				.blockLast();

		logTime(start);
	}


	private static void logTime(Instant start) {
		logger.debug("Total: " + Duration.between(start, Instant.now()).toMillis() + " millis");
	}

}
