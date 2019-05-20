package pl.decerto.hyperon.demo.dictionary.dict.impl;

import lombok.experimental.UtilityClass;

/**
 * Class which normalizes the keys
 */
@UtilityClass
class MultiValueKeyNormalizer {

	static String normalize(String key) {
		if (key == null) {
			return null;
		}
		return key.toLowerCase();
	}

	static String normalizeBCKey(String backwardsCompatibilityKey) {
		if (backwardsCompatibilityKey == null) {
			return null;
		}
		return "${" + backwardsCompatibilityKey.toLowerCase() + "}";
	}
}
