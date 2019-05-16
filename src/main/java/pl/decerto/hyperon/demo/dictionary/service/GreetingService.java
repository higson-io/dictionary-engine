package pl.decerto.hyperon.demo.dictionary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.decerto.hyperon.runtime.core.HyperonContext;
import pl.decerto.hyperon.runtime.core.HyperonEngine;

@Service
public class GreetingService {

	private static final String HYPERON_GREETING_FUNCTION = "helloworld";

	private final HyperonEngine hyperonEngine;

	@Autowired
	public GreetingService(HyperonEngine hyperonEngine) {
		this.hyperonEngine = hyperonEngine;
	}

	public String getGreeting() {
		return (String) hyperonEngine.call(HYPERON_GREETING_FUNCTION, new HyperonContext());
	}
}
