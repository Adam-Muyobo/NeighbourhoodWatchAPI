package com.neighbourhoodwatch.api.checkpoint;

import com.neighbourhoodwatch.api.checkpoint.dto.CheckpointDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkpoints")
@RequiredArgsConstructor
public class CheckpointController {

    private final CheckpointService service;

    @GetMapping
    public ResponseEntity<List<CheckpointDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CheckpointDTO> getByUUID(@PathVariable String uuid) {
        return ResponseEntity.ok(service.getByUUID(uuid));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<CheckpointDTO> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(service.getByCode(code));
    }

    @PostMapping
    public ResponseEntity<CheckpointDTO> create(@RequestBody CheckpointDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CheckpointDTO> update(
            @PathVariable String uuid,
            @RequestBody CheckpointDTO dto) {
        return ResponseEntity.ok(service.update(uuid, dto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable String uuid) {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
