package pl.decerto.hyperon.demo.dictionary.dom;

public class AttributeNotFoundException extends RuntimeException {

	private static final String MESSAGE
			= "Unable to find attribute with code : %s in domain object for path : %s";

	private AttributeNotFoundException(String message) {
		super(message);
	}

	static AttributeNotFoundException wrongCode(String path, String code) {
		return new AttributeNotFoundException(String.format(MESSAGE, code, path));
	}

}
