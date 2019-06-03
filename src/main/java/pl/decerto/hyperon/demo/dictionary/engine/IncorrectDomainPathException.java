package pl.decerto.hyperon.demo.dictionary.engine;

/**
 * Exception thrown on incorrect configuration of path used for querying hyperon's domain
 */
public class IncorrectDomainPathException extends RuntimeException {

	private static final String MESSAGE = "Unable to find domain object for path : %s in profile : %s";

	private IncorrectDomainPathException(String message) {
		super(message);
	}

	static IncorrectDomainPathException wrongPath(String profile, String path) {
		return new IncorrectDomainPathException(String.format(MESSAGE, path, profile));
	}
}
