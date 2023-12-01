package com.example.testMetro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для обработки запросов связанных с трамвайными маршрутами.
 */
@RestController
@RequestMapping("/trams")
public class TramRouteController {

    private final TramRouteRepository tramRouteRepository;

    /**
     * Конструктор контроллера.
     *
     * @param tramRouteRepository Репозиторий для работы с данными маршрутов.
     */
    @Autowired
    public TramRouteController(TramRouteRepository tramRouteRepository) {
        this.tramRouteRepository = tramRouteRepository;
    }


    /**
     * Обработка запроса на получение всех маршрутов.
     */

    /**
     * Получение всех трамвайных маршрутов.
     *
     * @return Список всех трамвайных маршрутов.
     */
    @GetMapping
    public List<TramRoute> getAllRoutes() {
        return tramRouteRepository.findAll();
    }

    /**
     * Получение трамвайного маршрута по его идентификатору.
     *
     * @param id Идентификатор трамвайного маршрута.
     * @return ResponseEntity с найденным маршрутом и статусом ОК, либо статусом NOT_FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TramRoute> getRouteById(@PathVariable Long id) {
        return tramRouteRepository.findById(id)
                .map(route -> new ResponseEntity<>(route, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Сохранение нового трамвайного маршрута.
     *
     * @param tramRoute Новый трамвайный маршрут для сохранения.
     * @param result    Результат валидации.
     * @return ResponseEntity с идентификатором сохраненного маршрута и статусом CREATED
     *         или статусом BAD_REQUEST, если есть ошибки валидации.
     */
    @PostMapping
    public ResponseEntity<Long> saveRoute(@RequestBody @Valid TramRoute tramRoute, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(0L, HttpStatus.BAD_REQUEST);
        }
        TramRoute savedRoute = tramRouteRepository.save(tramRoute);
        return new ResponseEntity<>(savedRoute.getId(), HttpStatus.CREATED);
    }

    /**
     * Удаление трамвайного маршрута по его идентификатору.
     *
     * @param id Идентификатор трамвайного маршрута для удаления.
     * @return ResponseEntity со статусом NO_CONTENT после удаления маршрута.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        tramRouteRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * Обновление информации о трамвайном маршруте по его идентификатору.
     *
     * @param id           Идентификатор трамвайного маршрута.
     * @param updatedRoute Обновленные данные для маршрута.
     * @return ResponseEntity с обновленным маршрутом и статусом OK,
     *         или статусом NOT_FOUND, если маршрут не найден по указанному идентификатору.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TramRoute> updateRoute(@PathVariable Long id, @RequestBody TramRoute updatedRoute) {
        return tramRouteRepository.findById(id)
                .map(route -> {
                    route.setCode(updatedRoute.getCode());
                    route.setName(updatedRoute.getName());
                    TramRoute savedRoute = tramRouteRepository.save(route);
                    return new ResponseEntity<>(savedRoute, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
