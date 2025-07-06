package com.andres.citas_medicas.enums;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class DiaSemanaDeserializer extends JsonDeserializer<DiaSemana> {
    @Override
    public DiaSemana deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String value = jsonParser.getText().toUpperCase();
        switch (value) {
            case "LUNES": return DiaSemana.MONDAY;
            case "MARTES": return DiaSemana.TUESDAY;
            case "MIERCOLES": return DiaSemana.WEDNESDAY;
            case "JUEVES": return DiaSemana.THURSDAY;
            case "VIERNES": return DiaSemana.FRIDAY;
            case "SABADO": return DiaSemana.SATURDAY;
            default:
                throw new IllegalArgumentException("Día no válido: " + value);
        }
    }
}
