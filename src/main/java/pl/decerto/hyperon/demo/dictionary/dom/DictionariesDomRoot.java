package pl.decerto.hyperon.demo.dictionary.dom;

import java.util.List;
import pl.decerto.hyperon.demo.dictionary.dict.InputLevelContext;

/**
 * Interface representing domain of dictionaries
 */
public interface DictionariesDomRoot {

	DictionaryDom dictionaryDom(String dictionaryCode);

	ContextDictionaryDom contextDictionaryDom(String dictionaryCode, InputLevelContext ctx);

	List<String> dictionariesCodes();
}
