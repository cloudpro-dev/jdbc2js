package consulting.cloudpro.j2js.generator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.*;

/**
 * Domain object which represents a JSON schema node.
 * <p></p>
 * <b>Note:</b> It does not contain all the properties defined in the
 * <a href="http://json-schema.org/draft-04/schema">JSON Schema - draft-04</a> specification.  Only the properties
 * which we can generate based on database meta and row data are included.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"schema", "id", "title", "type", "description", "minimum", "maximum", "minLength", "maxLength", "default", "examples", "properties", "required" })
public class Schema {
    public static String SCHEMA_URL = "http://json-schema.org/draft-04/schema#";

    @JsonProperty("$id")
    private String id;
    @JsonProperty("$schema")
    private String schema;
    private String type;
    private String title;
    private String description;
    private Number minimum;
    private Number maximum;
    private Integer minLength;
    private Integer maxLength;
    @JsonProperty("default")
    private Object defaultValue;
    private Map<String, Schema> properties;
    private List<String> examples;
    private Set<String> required;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Number getMinimum() {
        return minimum;
    }

    public void setMinimum(Number minimum) {
        this.minimum = minimum;
    }

    public Number getMaximum() {
        return maximum;
    }

    public void setMaximum(Number maximum) {
        this.maximum = maximum;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Map<String, Schema> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Schema> properties) {
        this.properties = properties;
    }

    public void addProperty(String key, Schema schema) {
        if(this.properties == null) {
            this.properties = new HashMap<>();
        }
        this.properties.put(key, schema);
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    public void addExample(String example) {
        if(this.examples == null) {
            this.examples = new ArrayList<>();
        }
        this.examples.add(example);
    }

    public Set<String> getRequired() {
        return required;
    }

    public void setRequired(Set<String> required) {
        this.required = required;
    }

    public void addRequired(String required) {
        if(this.required == null) {
            this.required = new HashSet<>();
        }
        this.required.add(required);
    }
}
