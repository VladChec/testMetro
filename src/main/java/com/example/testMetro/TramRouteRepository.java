package com.example.testMetro;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с трамвайными маршрутами в базе данных.
 */
@Repository
public interface TramRouteRepository extends JpaRepository<TramRoute, Long> {
}
