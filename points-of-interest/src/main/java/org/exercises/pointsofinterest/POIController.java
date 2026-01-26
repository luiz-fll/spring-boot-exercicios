package org.exercises.pointsofinterest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class POIController {

    private final POIService service;

    public POIController(POIService service) {
        this.service = service;
    }

    @PostMapping("/")
    public List<ResponseDTO> findPointsOfInterest(@RequestBody RequestDTO requestDTO) {
        List<POIEntity> points = service.findPointsOfInterest(requestDTO.x(), requestDTO.y(), requestDTO.maxDistance());
        return points.stream().map(ResponseDTO::from).toList();
    }
}
