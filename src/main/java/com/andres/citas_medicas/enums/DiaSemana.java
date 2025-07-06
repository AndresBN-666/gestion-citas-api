package com.andres.citas_medicas.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = DiaSemanaDeserializer.class)
public enum DiaSemana {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
}
