package com.example.j2js.generator;

import com.example.j2js.metadata.ColumnDefinition;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonSchemaGeneratorTest {

    @Test
    public void givenMetadata_whenValid_thenGenerateJsonSchema() throws JsonProcessingException {
        Set<ColumnDefinition> cols = new HashSet<>();

        ColumnDefinition idCol = new ColumnDefinition();
        idCol.setType("INTEGER");
        idCol.setName("ID");
        idCol.setNullable("NO");
        idCol.setSize(10);
        idCol.setRemarks("Customer identifier");

        ColumnDefinition nameCol = new ColumnDefinition();
        nameCol.setType("VARCHAR");
        nameCol.setName("NAME");
        nameCol.setNullable("NO");
        nameCol.setSize(50);
        nameCol.setRemarks("Customer name");

        ColumnDefinition balanceCol = new ColumnDefinition();
        balanceCol.setType("DECIMAL");
        balanceCol.setName("BALANCE");
        balanceCol.setNullable("NO");
        balanceCol.setSize(10);
        balanceCol.setDecimals(2);
        balanceCol.setRemarks("Customer balance");

        cols.addAll(Arrays.asList(idCol, nameCol, balanceCol));

        JsonSchemaGenerator generator = new JsonSchemaGenerator();
        String schema = generator.generateSchema(cols);

        assertThat(schema, is(not(nullValue())));
        System.out.println(schema);
    }
}
