package consulting.cloudpro.j2js.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class SchemaTest {
    @Test
    public void whenSerializing_thenCorrect() throws JsonProcessingException {
        Schema schema = new Schema();
        schema.setId("http://yourdomain.com/schemas/test/h.schema.json");
        schema.setTitle("Object H");
        schema.addRequired("id");

        Schema idProp = new Schema();
        idProp.setType("string");
        idProp.setDescription("The code for the object");
        idProp.setDefaultValue("ABC12345");
        idProp.setMaximum(999);
        idProp.setMinimum(0);
        idProp.setMinLength(0);
        idProp.setMaxLength(8);
        idProp.addExample("ABC-12345");
        idProp.addExample("DEF-12345");
        idProp.addExample("GHI-12345");

        schema.addProperty("id", idProp);

        String result = new ObjectMapper().writeValueAsString(schema);

        System.out.println(result);
    }
}
