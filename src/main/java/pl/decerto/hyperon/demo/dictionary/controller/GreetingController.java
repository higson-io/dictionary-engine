package pl.decerto.hyperon.demo.dictionary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.decerto.hyperon.demo.dictionary.service.GreetingService;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

	private final GreetingService greetingService;

	@Autowired
	public GreetingController(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	@GetMapping
	public String hello() {
		return greetingService.getGreeting();
	}

}
