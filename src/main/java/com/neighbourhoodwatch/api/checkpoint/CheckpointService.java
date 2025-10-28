package com.neighbourhoodwatch.api.checkpoint;

import com.neighbourhoodwatch.api.checkpoint.dto.CheckpointDTO;
import com.neighbourhoodwatch.api.house.House;
import com.neighbourhoodwatch.api.house.HouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckpointService {

    private final CheckpointRepository repository;
    private final HouseRepository houseRepository;

    private CheckpointDTO toDTO(Checkpoint entity) {
        return CheckpointDTO.builder()
                .checkpointUUID(entity.getCheckpointUUID())
                .name(entity.getName())
                .type(entity.getType())
                .description(entity.getDescription())
                .houseUUID(entity.getHouse() != null ? entity.getHouse().getHouseUUID() : null)
                .location(entity.getLocation())
                .build();
    }

    public List<CheckpointDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public CheckpointDTO getByUUID(String uuid) {
        return toDTO(repository.findByCheckpointUUID(uuid)
                .orElseThrow(() -> new RuntimeException("Checkpoint not found")));
    }

    public CheckpointDTO create(CheckpointDTO dto) {
        House house = null;
        if (dto.getHouseUUID() != null) {
            house = houseRepository.findByHouseUUID(dto.getHouseUUID())
                    .orElseThrow(() -> new RuntimeException("House not found"));
        }

        Checkpoint checkpoint = Checkpoint.builder()
                .name(dto.getName())
                .type(dto.getType())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .house(house)
                .build();

        return toDTO(repository.save(checkpoint));
    }

    public CheckpointDTO update(String uuid, CheckpointDTO dto) {
        Checkpoint checkpoint = repository.findByCheckpointUUID(uuid)
                .orElseThrow(() -> new RuntimeException("Checkpoint not found"));

        if (dto.getName() != null) checkpoint.setName(dto.getName());
        if (dto.getDescription() != null) checkpoint.setDescription(dto.getDescription());
        if (dto.getLocation() != null) checkpoint.setLocation(dto.getLocation());
        if (dto.getType() != null) checkpoint.setType(dto.getType());

        if (dto.getHouseUUID() != null) {
            House house = houseRepository.findByHouseUUID(dto.getHouseUUID())
                    .orElseThrow(() -> new RuntimeException("House not found"));
            checkpoint.setHouse(house);
        }

        return toDTO(repository.save(checkpoint));
    }

    public void delete(String uuid) {
        Checkpoint checkpoint = repository.findByCheckpointUUID(uuid)
                .orElseThrow(() -> new RuntimeException("Checkpoint not found"));
        repository.delete(checkpoint);
    }
}
