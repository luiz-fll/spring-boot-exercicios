package org.exercises.pointsofinterest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface POIRepository extends JpaRepository<POIEntity, Integer> {}
