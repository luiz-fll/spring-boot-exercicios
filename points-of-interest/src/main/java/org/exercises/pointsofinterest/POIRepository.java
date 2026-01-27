package org.exercises.pointsofinterest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface POIRepository extends JpaRepository<POIEntity, Integer> {

    @Query(
            value = """
                    SELECT *
                    FROM poi
                    WHERE ((x - :x)*(x - :x) + (y - :y)*(y - :y)) <= (:d * :d)
                    ORDER BY (x - :x)*(x - :x) + (y - :y)*(y - :y) ASC
                    """,
            nativeQuery = true
    )
    List<POIEntity> findByDistance(@Param("x") int x,
                                   @Param("y") int y,
                                   @Param("d") int d);

}
