package org.exercises.pointsofinterest;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RequestDTO(Integer x,
                         Integer y,
                         @JsonProperty("max-distance") Integer maxDistance) {}
