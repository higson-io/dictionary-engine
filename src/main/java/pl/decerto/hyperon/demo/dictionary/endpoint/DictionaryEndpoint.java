package pl.decerto.hyperon.demo.dictionary.endpoint;

import io.hyperon.demos.dictionary_engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.decerto.hyperon.demo.dictionary.converter.DictionaryConverter;
import pl.decerto.hyperon.demo.dictionary.converter.EntryConverter;
import pl.decerto.hyperon.demo.dictionary.dict.ContextAwareDictionaryEntry;
import pl.decerto.hyperon.demo.dictionary.dict.InputLevelContext;
import pl.decerto.hyperon.demo.dictionary.service.DictionaryService;

import static pl.decerto.hyperon.demo.dictionary.configuration.WebServiceConfiguration.NAMESPACE_URI;

@Endpoint
public class DictionaryEndpoint {

	private final DictionaryService dictionaryService;

	@Autowired
	public DictionaryEndpoint(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDictionaryCodesRequest")
	@ResponsePayload
	public GetDictionaryCodesResponse getDictionaryCodes() {
		var response = new GetDictionaryCodesResponse();
		response.getDictionaryCode().addAll(dictionaryService.getAllDictionariesCodes());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDictionaryByCodeRequest")
	@ResponsePayload
	public GetDictionaryResponse getDictionaryByCode(@RequestPayload GetDictionaryByCodeRequest request) {
		var dictionary = dictionaryService.getDictionaryByCode(request.getDictionaryCode());
		return DictionaryConverter.toDictionaryResponse(dictionary);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDictionaryByCodeAndLevelRequest")
	@ResponsePayload
	public GetDictionaryResponse getDictionaryByCodeAndLevel(@RequestPayload GetDictionaryByCodeAndLevelRequest request) {
		var dictionary = dictionaryService.getDictionaryLevel(request.getDictionaryCode(), request.getLevel());
		return DictionaryConverter.toDictionaryResponse(dictionary);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDictionaryLevelsRequest")
	@ResponsePayload
	public GetDictionaryLevelsResponse getDictionaryLevels(@RequestPayload GetDictionaryLevelsRequest request) {
		var response = new GetDictionaryLevelsResponse();
		response.getLevel().addAll(dictionaryService.getDictionaryLevels(request.getDictionaryCode()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEntryByKeyRequest")
	@ResponsePayload
	public GetEntryResponse getEntryByKey(@RequestPayload GetEntryByKeyRequest request) {
		var dictionaryCode = request.getDictionaryCode();
		var key = request.getKey();
		var entry = dictionaryService.getDictionaryEntry(dictionaryCode, key);

		return EntryConverter.toEntryResponse(entry.orElseThrow(() -> new EntryNotFoundException(dictionaryCode, key)));
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEntryByKeyAndLevelRequest")
	@ResponsePayload
	public GetEntryResponse getEntryByKeyAndLevel(@RequestPayload GetEntryByKeyAndLevelRequest request) {
		var dictionaryCode = request.getDictionaryCode();
		var key = request.getKey();
		var level = request.getLevel();
		var entry = dictionaryService.getDictionaryEntry(dictionaryCode, key, level);

		return EntryConverter.toEntryResponse(entry.orElseThrow(() ->
				new EntryNotFoundException(dictionaryCode, key, level)));
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getContextAwareEntryRequest")
	@ResponsePayload
	public GetContextAwareEntryResponse getContextAwareEntry(@RequestPayload GetContextAwareEntryRequest request) {
		var entry = findContextAwareEntry(request.getDictionaryCode(), request.getInputLevels());
		return EntryConverter.toContextAwareEntryResponse(entry);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getContextAwareEntryValueRequest")
	@ResponsePayload
	public GetContextAwareEntryValueResponse getContextAwareEntry(@RequestPayload GetContextAwareEntryValueRequest request) {
		var entry = findContextAwareEntry(request.getDictionaryCode(), request.getInputLevels());
		var result = new GetContextAwareEntryValueResponse();
		result.setValue(entry.getValue(request.getLevel()));
		return result;
	}

	private ContextAwareDictionaryEntry findContextAwareEntry(
			String dictionaryCode, LevelValuesType levelValues) {

		var context = new InputLevelContext();
		levelValues.getLevelValue()
				.forEach(levelValue -> context.withInputLevel(levelValue.getLevel(), levelValue.getValue()));

		return dictionaryService.getDictionaryEntryForContext(dictionaryCode, context);
	}

}
