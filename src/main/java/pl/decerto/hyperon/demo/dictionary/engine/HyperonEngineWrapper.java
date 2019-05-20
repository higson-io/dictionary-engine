package pl.decerto.hyperon.demo.dictionary.engine;

import java.util.Date;
import java.util.List;
import org.smartparam.engine.core.context.ParamContext;
import org.smartparam.engine.core.output.ParamValue;
import pl.decerto.hyperon.runtime.core.HyperonEngine;
import pl.decerto.hyperon.runtime.decoder.MpCascadeDecoder;
import pl.decerto.hyperon.runtime.model.HyperonDomainObject;

/**
 * Wrapper for HyperonEngine used to simplify handling of the engine
 */
public class HyperonEngineWrapper {

	private final HyperonEngine engine;

	private final String profile;

	private final MpCascadeDecoder mpCascadeDecoder;

	public HyperonEngineWrapper(HyperonEngine engine, String profile) {
		this.engine = engine;
		this.profile = profile;
		this.mpCascadeDecoder = new MpCascadeDecoder(engine);
	}

	/*
	 * parameter and function API
	 */
	public ParamValue get(String parameterName, ParamContext ctx) {
		return engine.get(parameterName, ctx);
	}

	public Object call(String functionName, ParamContext ctx, Object... args) {
		if (isParameterSubstitutedFunction(functionName)) {
			if (args.length > 0) {
				throw new IllegalArgumentException(String.format("cannot call function with substituted parameter and pass own args: %s", functionName));
			}

			return mpCascadeDecoder.cascadeCall(functionName, ctx);
		}

		return engine.call(functionName, ctx, args);
	}

	public void setEffectiveVersion(String region, String version) {
		engine.setEffectiveVersion(region, version);
	}

	public void setEffectiveDate(Date date) {
		engine.setEffectiveDate(date);
	}

	public void clearEffectiveVersion(String region) {
		engine.clearEffectiveVersion(region);
	}

	public void clearEffectiveVersions() {
		engine.clearEffectiveVersions();
	}

	public void clearEffectiveDate() {
		engine.clearEffectiveDate();
	}

	public void clearEffectiveSetup() {
		engine.clearEffectiveSetup();
	}

	/*
	 * domain API
	 */
	public List<String> getProfiles() {
		return engine.getProfiles();
	}

	public HyperonDomainObject getDomain(String profile, String objectPath) {
		return engine.getDomain(profile, objectPath);
	}

	public HyperonDomainObject getDomainObj(String objectPath, Object... args) {
		String path = String.format(objectPath, args);
		HyperonDomainObject domain = engine.getDomain(profile, path);

		if (domain == null) {
			throw IncorrectDomainPathException.wrongPath(profile, path);
		}

		return domain;
	}

	private boolean isParameterSubstitutedFunction(String functionName) {
		return functionName.contains("(") || functionName.contains("[");
	}

}
