package pl.decerto.hyperon.demo.dictionary.dom;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.smartparam.engine.core.output.MultiValue;
import org.smartparam.engine.core.output.ParamValue;
import pl.decerto.hyperon.runtime.core.AdhocContext;
import pl.decerto.hyperon.runtime.core.HyperonContext;
import pl.decerto.hyperon.runtime.helper.TypeConverter;
import pl.decerto.hyperon.runtime.model.HyperonDomainObject;

/**
 * Base class for classes which represent domains
 *
 * @param <T>
 */
abstract class AbstractSimpleDom<T extends AbstractSimpleDom> {

	private static final String DOMAIN = "domain";

	private static final String CODE = "code";

	private static final String NAME = "name";

	private static final String TYPE = "type";

	private static final String PATH_DOMAIN_CODE = DOMAIN + "." + CODE;

	private static final String PATH_DOMAIN_TYPE = DOMAIN + "." + TYPE;

	private static final String PATH_DOMAIN_NAME = DOMAIN + "." + NAME;

	private static final TypeConverter typeConverter = new TypeConverter();

	protected final HyperonDomainObject domObj;

	protected HyperonContext ctx;

	AbstractSimpleDom(HyperonDomainObject domObj) {
		this.domObj = domObj;
		ctx = defaultCtx(null);
	}

	protected HyperonContext defaultCtx(HyperonContext parent) {
		var context = parent != null ? new AdhocContext(parent) : new AdhocContext();

		setStandardFields(context);

		return context;
	}

	@SuppressWarnings("unchecked")
	public T with(HyperonContext ctx) {
		this.ctx = defaultCtx(ctx);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T with(String key, HyperonContext subContext) {

		if (!usesAdhocContext()) {
			ctx = new AdhocContext(ctx);
		}

		ctx.with(key, subContext, true);

		return (T) this;
	}

	protected List<HyperonDomainObject> getChildren(String propertyCode) {
		return domObj.getChildren(propertyCode);
	}

	protected HyperonDomainObject getChild(String propertyCode, String code) {
		return domObj.getChild(propertyCode, code);
	}

	protected boolean hasChild(String propertyCode, String code) {
		return domObj.getChild(propertyCode, code) != null;
	}

	protected <T extends AbstractSimpleDom> List<T> all(String childCode, Function<? super HyperonDomainObject, T> constr) {
		return getChildren(childCode)
				.stream()
				.map(childDomObj -> {
					T dom = constr.apply(childDomObj);
					dom.with(ctx);
					return dom;
				})
				.collect(Collectors.toList());
	}

	protected <T extends AbstractSimpleDom> T one(String childCode, String elementCode, Function<? super HyperonDomainObject, T> constr) {
		T dom = constr.apply(getChild(childCode, elementCode));
		dom.with(ctx);
		return dom;
	}

	protected <T extends AbstractSimpleDom> T one(String childCode, Function<? super HyperonDomainObject, T> constr) {
		return one(childCode, childCode, constr);
	}

	protected ParamValue getAttrValue(String code) {
		var attr = domObj.getAttribute(null, code);
		if (attr == null) {
			throw AttributeNotFoundException.wrongCode(domObj.getPath(), code);
		}
		return attr.getValue(ctx);
	}

	protected String getAttrString(String code) {
		return typeConverter.getString(holder(code));
	}

	protected BigDecimal getAttrDecimal(String code) {
		return typeConverter.getDecimal(holder(code));
	}

	protected double getAttrNumber(String code) {
		return typeConverter.getNumber(holder(code));
	}

	protected Integer getAttrInteger(String code) {
		return typeConverter.getInteger(holder(code));
	}

	protected Long getAttrLong(String code) {
		return typeConverter.getLong(holder(code));
	}

	protected Date getAttrDate(String code) {
		return typeConverter.getDate(holder(code));
	}

	protected boolean getAttrBoolean(String code) {
		return typeConverter.getBoolean(holder(code));
	}

	protected Object getAttrObject(String code) {
		return holder(code);
	}

	protected List<String> getAttrStringList(String code) {
		return getAttrList(code, mv -> typeConverter.getString(holder(mv)));
	}

	private <T> List<T> getAttrList(String code, Function<MultiValue, T> valueConverter) {
		ParamValue pv = getAttrValue(code);
		return StreamSupport.stream(pv.spliterator(), false)
				.map(valueConverter)
				.collect(Collectors.toList());
	}

	private Object holder(String attrCode) {
		var holder = getAttrValue(attrCode).getHolder();
		return holder != null ? holder.getValue() : null;
	}

	private Object holder(MultiValue mv) {
		var holder = mv.getHolder(0);
		return holder != null ? holder.getValue() : null;
	}

	public String code() {
		return domObj.getCode();
	}

	public String name() {
		return domObj.getName();
	}

	private boolean usesAdhocContext() {
		return ctx instanceof AdhocContext;
	}

	private void setStandardFields(HyperonContext ctx) {
		ctx.with(PATH_DOMAIN_CODE, domObj.getCode(), false);
		ctx.with(PATH_DOMAIN_TYPE, domObj.getTypeCode(), false);
		ctx.with(PATH_DOMAIN_NAME, domObj.getName(), false);
	}

}
