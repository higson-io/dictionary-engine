package pl.decerto.hyperon.demo.dictionary.dom;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class containing mappings to hyperon's paths
 */
@AllArgsConstructor
@Getter
enum Domains {
	ROOT("/"),
	DICTIONARIES("DICTIONARIES"),
	DICTIONARY("/DICTIONARIES[%s]");

	private String path;
}
