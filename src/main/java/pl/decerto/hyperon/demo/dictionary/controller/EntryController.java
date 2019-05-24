package pl.decerto.hyperon.demo.dictionary.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.decerto.hyperon.demo.dictionary.dict.ContextAwareDictionaryEntry;
import pl.decerto.hyperon.demo.dictionary.dict.InputLevelContext;
import pl.decerto.hyperon.demo.dictionary.dto.ContextAwareDictionaryEntryDto;
import pl.decerto.hyperon.demo.dictionary.dto.ContextAwareEntryValueDto;
import pl.decerto.hyperon.demo.dictionary.dto.ControllerResponseDto;
import pl.decerto.hyperon.demo.dictionary.dto.DictionaryEntryDto;
import pl.decerto.hyperon.demo.dictionary.service.DictionaryService;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pl.decerto.hyperon.demo.dictionary.controller.ControllerResponseFactory.response;
import static pl.decerto.hyperon.demo.dictionary.controller.DictionaryController.*;

@RestController
@RequestMapping("/entries")
public class EntryController {

	private static final String ENTRY_NOT_FOUND_MESSAGE = "Entry for key : %s in dictionary : %s was not found";
	private static final String LEVEL_ENTRY_NOT_FOUND_MESSAGE
			= "Entry for key : %s and level : %s in dictionary : %s was not found";
	private static final String KEY_CODE_DESC = "dictionary key";
	private static final String INPUT_LEVELS_MAP_DESC = "values for input levels";

	private final DictionaryService dictionaryService;

	@Autowired
	public EntryController(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	@GetMapping(value = "/bykey", produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get entry from dictionary accessible by key", notes = ONLY_FOR_KEY_DICT_NOTE +
			" For multi-value dictionaries returns entry associated with first data column." +
			" Additional columns are ignored.")
	public ResponseEntity<ControllerResponseDto<DictionaryEntryDto>> getEntry(
			@RequestParam @ApiParam(DICT_CODE_DESC) String dictionary,
			@RequestParam @ApiParam(KEY_CODE_DESC) String key) {

		var entryDto = dictionaryService.getDictionaryEntry(dictionary, key)
				.map(DictionaryEntryDto::new)
				.orElse(null);

		return response(entryDto, String.format(ENTRY_NOT_FOUND_MESSAGE, key, dictionary));
	}

	@GetMapping(value = "/bykey/bylevel", produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get entry for specified column (=level) from dictionary accessible by key.",
			notes = ONLY_FOR_KEY_DICT_NOTE + " Returns entry associated with data column specified by level code." +
					" The key for the returned entry is the level code.")
	public ResponseEntity<ControllerResponseDto<DictionaryEntryDto>> getSubEntry(
			@RequestParam @ApiParam(DICT_CODE_DESC) String dictionary,
			@ApiParam(KEY_CODE_DESC) @RequestParam String key,
			@ApiParam(LEVEL_CODE_DESC) @RequestParam String level) {

		var entryDto =  dictionaryService.getDictionaryEntry(dictionary, key, level)
				.map(DictionaryEntryDto::new)
				.orElse(null);

		return response(entryDto, String.format(LEVEL_ENTRY_NOT_FOUND_MESSAGE, key, level, dictionary));
	}

	@PostMapping(value = "/bycontext", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get entry from dictionary accessible by context", notes = "Resulting entry will contain " +
			"collection of available output levels and entries for each level.")
	public ResponseEntity<ControllerResponseDto<ContextAwareDictionaryEntryDto>> getContextAwareDictionaryEntry(
			@RequestParam @ApiParam(DICT_CODE_DESC) String dictionary,
			@RequestBody @ApiParam(INPUT_LEVELS_MAP_DESC) Map<String, String> inputLevels) {

		var entry = getContextAwareEntry(dictionary, inputLevels);
		return response(new ContextAwareDictionaryEntryDto(entry));
	}

	@PostMapping(value = "/bycontext/bylevel", produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get value from dictionary accessible by context for specified data column (= output level)")
	public ResponseEntity<ControllerResponseDto<ContextAwareEntryValueDto>> getContextAwareDictionaryEntryValue(
			@RequestParam @ApiParam(DICT_CODE_DESC) String dictionary,
			@RequestParam  @ApiParam(LEVEL_CODE_DESC) String level,
			@RequestBody @ApiParam(INPUT_LEVELS_MAP_DESC) Map<String, String> inputLevels) {

		var value = getContextAwareEntry(dictionary, inputLevels).getValue(level);
		return response(new ContextAwareEntryValueDto(value));
	}

	private ContextAwareDictionaryEntry getContextAwareEntry(String dictionary, Map<String, String> pathValueMap) {
		var context = new InputLevelContext(pathValueMap);
		return dictionaryService.getDictionaryEntryForContext(dictionary, context);
	}

}
