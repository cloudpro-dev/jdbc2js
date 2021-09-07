package consulting.cloudpro.j2js.generator;

import consulting.cloudpro.j2js.metadata.ColumnDefinition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

/**
 * Generates a JSON schema based on a database table.
 */
public class JsonSchemaGenerator {

    public static final String BASE_URL = "http://x.y.z/";

    private final ObjectMapper mapper;

    public JsonSchemaGenerator() {
        this.mapper = new ObjectMapper();
    }

    /**
     * Generate a JSON schema for a set of database columns.
     * @param columns the database columns
     * @return the JSON schema
     */
    public String generateSchema(Set<ColumnDefinition> columns) throws JsonProcessingException {
        final Schema root = new Schema();
        // id is based on database schema and table name
        // all columns should have the same schema and table
        root.setId(convertId(columns.stream().findFirst().orElseThrow()));
        root.setSchema(Schema.SCHEMA_URL);

        // convert all ColumnDefinition objects to Schema objects
        columns.forEach(c -> {
            Schema schema = new Schema();

            // convert data type
            schema.setType(convertDataType(c));
            schema.setMaximum(convertMaximum(c));
            schema.setMaxLength(convertMaxLength(c));
            schema.setDescription(c.getRemarks());
            if(c.getExampleValue() != null) {
                schema.addExample(c.getExampleValue());
            }

            // TODO PK default value may be a sequence
            // (NEXT VALUE FOR \"PUBLIC\".\"SYSTEM_SEQUENCE_550414A9_AD88_47C7_A4E4_05E7CAAB30D9\")
            schema.setDefaultValue(c.getDefaultValue());

            root.addProperty(c.getName(), schema);
            if(c.getNullable().equalsIgnoreCase("NO")) {
                root.addRequired(c.getName());
            }
        });

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
    }

    private String convertId(ColumnDefinition columnDefinition) {
        // "http://x.y.z/rootschema.json#"
        String id;
        if(columnDefinition.getCatalog() != null) {
            id = columnDefinition.getCatalog() + ".";
        }
        else {
            id = columnDefinition.getSchema() + ".";
        }
        id += columnDefinition.getTable() + ".";
        id += "schema.json";
        return BASE_URL + id;
    }

    private String convertDataType(ColumnDefinition columnDefinition) {
        switch(columnDefinition.getType()) {
            case "CHAR":
            case "VARCHAR":
            case "LONGVARCHAR":
                return "string";
            case "TINYINT":
            case "SMALLINT":
            case "INTEGER":
            case "BIGINT":
                return "integer";
            case "DECIMAL":
            case "NUMERIC":
            case "REAL":
            case "DOUBLE":
            case "FLOAT":
                return "number";
            // default to string
            default: return "string";
        }
    }

    private Number convertMaximum(ColumnDefinition columnDefinition) {
        switch(columnDefinition.getType()) {
            case "TINYINT":
            case "SMALLINT":
            case "INTEGER":
            case "BIGINT":
                return new BigInteger("9".repeat(columnDefinition.getSize()));
            case "DECIMAL":
            case "NUMERIC":
            case "REAL":
            case "DOUBLE":
            case "FLOAT":
                // Generate a number in the format 99999.99 based on column definition
                // Math.max ensure number cannot be lower than 0
                int precision = columnDefinition.getSize()-Math.max(columnDefinition.getDecimals(), 0);
                int scale = Math.max(columnDefinition.getDecimals(), 0);
                // default if no precision is defined
                if(precision == 0) {
                    // default precision to 12 digits
                    precision = 12;
                }
                return new BigDecimal("9".repeat(precision) + "." + "9".repeat(scale));
            default:
                return null;
        }
    }

    private Integer convertMaxLength(ColumnDefinition columnDefinition) {
        switch(columnDefinition.getType()) {
            case "CHAR":
            case "VARCHAR":
            case "LONGVARCHAR":
                return columnDefinition.getSize();
            default:
                return null;
        }
    }

}
