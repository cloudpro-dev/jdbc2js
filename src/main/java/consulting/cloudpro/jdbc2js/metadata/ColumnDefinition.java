package consulting.cloudpro.jdbc2js.metadata;

/**
 * Domain object containing all the meta-data for a single database column.
 */
public class ColumnDefinition {
    /** TABLE_CAT String => table catalog (may be null) **/
    private String catalog;
    /** TABLE_SCHEM String => table schema (may be null) **/
    private String schema;
    /** TABLE_NAME String => table name **/
    private String table;
    /** COLUMN_NAME String => column name **/
    private String name;
    /** DATA_TYPE int => SQL type from java.sql.Types **/
    private String type;
    /** COLUMN_SIZE int => column size.
     * The COLUMN_SIZE column specifies the column size for the given column.
     * For numeric data, this is the maximum precision.
     * For character data, this is the length in characters.
     * For datetime datatypes, this is the length in characters of the String representation (assuming the maximum allowed precision of the fractional seconds component).
     * For binary data, this is the length in bytes.
     * For the ROWID datatype, this is the length in bytes.
     * Null is returned for data types where the column size is not applicable.
     **/
    private Integer size;
    /** DECIMAL_DIGITS int => the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable. **/
    private Integer decimals;
    /** IS_NULLABLE String => ISO rules are used to determine the nullability for a column. **/
    private String nullable;
    /** REMARKS String => comment describing column (may be null) **/
    private String remarks;
    /** COLUMN_DEF String => default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null) **/
    private String defaultValue;
    private String exampleValue;

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getDecimals() {
        return decimals;
    }

    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getExampleValue() {
        return exampleValue;
    }

    public void setExampleValue(String exampleValue) {
        this.exampleValue = exampleValue;
    }

    @Override
    public String toString() {
        return "ColumnDefinition{" +
                "catalog='" + catalog + '\'' +
                ", schema='" + schema + '\'' +
                ", table='" + table + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                ", decimals=" + decimals +
                ", nullable='" + nullable + '\'' +
                ", remarks='" + remarks + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", exampleValue='" + exampleValue + '\'' +
                '}';
    }
}
