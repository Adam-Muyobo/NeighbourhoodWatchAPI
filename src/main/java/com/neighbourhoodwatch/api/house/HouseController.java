package com.neighbourhoodwatch.api.house;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/houses")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @PostMapping
    public ResponseEntity<House> createHouse(@RequestBody House house) {
        return ResponseEntity.ok(houseService.createHouse(house));
    }

    @GetMapping
    public ResponseEntity<List<House>> getAllHouses() {
        return ResponseEntity.ok(houseService.getAllHouses());
    }

    @GetMapping("/{houseUUID}")
    public ResponseEntity<House> getHouseByUUID(@PathVariable String houseUUID) {
        return houseService.getHouseByUUID(houseUUID)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{houseUUID}")
    public ResponseEntity<House> updateHouse(@PathVariable String houseUUID,
                                             @RequestBody House updatedHouse) {
        return ResponseEntity.ok(houseService.updateHouse(houseUUID, updatedHouse));
    }

    @DeleteMapping("/{houseUUID}")
    public ResponseEntity<Void> deleteHouse(@PathVariable String houseUUID) {
        houseService.deleteHouse(houseUUID);
        return ResponseEntity.ok().build();
    }
}
