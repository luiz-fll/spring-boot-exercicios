package org.exercises.pointsofinterest;

public record ResponseDTO(String name, Integer x, Integer y) {

    public static ResponseDTO from(POIEntity entity) {
        return new ResponseDTO(entity.getName(), entity.getX(), entity.getY());
    }

}
