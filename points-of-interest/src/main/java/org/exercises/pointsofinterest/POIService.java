package org.exercises.pointsofinterest;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class POIService {

    private final POIRepository repository;

    public POIService(POIRepository repository) {
        this.repository = repository;
    }

    public List<POIEntity> findPointsOfInterest(Integer x, Integer y, Integer maxDistance) {
        return repository.findByDistance(x, y, maxDistance);
    }

}
