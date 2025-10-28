package com.neighbourhoodwatch.api.house;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository houseRepository;

    public House createHouse(House house) {
        return houseRepository.save(house);
    }

    public List<House> getAllHouses() {
        return houseRepository.findAll();
    }

    public Optional<House> getHouseByUUID(String houseUUID) {
        return houseRepository.findByHouseUUID(houseUUID);
    }

    public House updateHouse(String houseUUID, House updated) {
        return houseRepository.findByHouseUUID(houseUUID)
                .map(existing -> {
                    existing.setNameOrNumber(updated.getNameOrNumber());
                    existing.setLocation(updated.getLocation());
                    return houseRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("House not found"));
    }

    public void deleteHouse(String houseUUID) {
        House house = houseRepository.findByHouseUUID(houseUUID)
                .orElseThrow(() -> new RuntimeException("House not found"));
        houseRepository.delete(house);
    }
}
