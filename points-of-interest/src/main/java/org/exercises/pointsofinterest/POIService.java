package org.exercises.pointsofinterest;

import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@Service
public class POIService {

    private final POIRepository repository;

    public POIService(POIRepository repository) {
        this.repository = repository;
    }

    public List<POIEntity> findPointsOfInterest(Integer x, Integer y, Double maxDistance) {
        List<POIEntity> points = repository.findAll();

        return points
                .stream()
                .filter(point -> {
                    double distance = pow(point.getX() - x, 2) + pow(point.getY() - y, 2);
                    distance = sqrt(distance);
                    return distance <= maxDistance;
                })
                .toList();
    }

}
