package com.neighbourhoodwatch.api.housemember;

import com.neighbourhoodwatch.api.housemember.dto.HouseMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/house-members")
@RequiredArgsConstructor
public class HouseMemberController {

    private final HouseMemberService houseMemberService;

    @PostMapping("/{userUUID}/{houseUUID}")
    public ResponseEntity<HouseMemberDTO> addMember(
            @PathVariable String userUUID,
            @PathVariable String houseUUID,
            @RequestParam String relationship
    ) {
        return ResponseEntity.ok(
                houseMemberService.addMemberToHouse(userUUID, houseUUID, relationship)
        );
    }

    @GetMapping
    public ResponseEntity<List<HouseMemberDTO>> getAll() {
        return ResponseEntity.ok(houseMemberService.getAllMembers());
    }

    @GetMapping("/{memberUUID}")
    public ResponseEntity<HouseMemberDTO> getByUUID(@PathVariable String memberUUID) {
        return houseMemberService.getByUUID(memberUUID)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{memberUUID}")
    public ResponseEntity<HouseMemberDTO> updateRelationship(
            @PathVariable String memberUUID,
            @RequestParam String relationship
    ) {
        return ResponseEntity.ok(
                houseMemberService.updateRelationship(memberUUID, relationship)
        );
    }

    @PutMapping("/{memberUUID}/end")
    public ResponseEntity<HouseMemberDTO> endMembership(@PathVariable String memberUUID) {
        return ResponseEntity.ok(houseMemberService.endMembership(memberUUID));
    }

    @DeleteMapping("/{memberUUID}")
    public ResponseEntity<Void> delete(@PathVariable String memberUUID) {
        houseMemberService.deleteMembership(memberUUID);
        return ResponseEntity.ok().build();
    }
}