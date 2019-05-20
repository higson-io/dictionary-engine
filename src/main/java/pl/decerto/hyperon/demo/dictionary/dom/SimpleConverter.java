package pl.decerto.hyperon.demo.dictionary.dom;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simple functional interface of converter
 *
 * @param <F> type of values to convert
 * @param <T> type of output (converted) values
 */
@FunctionalInterface
public interface SimpleConverter<F, T> {

	T convert(F from);

	default List<T> convertAll(Collection<F> fromElements) {
		return fromElements.stream()
				.map(this::convert)
				.collect(Collectors.toList());
	}
}
