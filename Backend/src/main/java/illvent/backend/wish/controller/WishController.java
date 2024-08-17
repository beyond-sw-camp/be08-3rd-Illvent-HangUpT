package illvent.backend.wish.controller;

import illvent.backend.wish.dto.WishDeleteRequestDTO;
import illvent.backend.wish.dto.WishRegisterRequestDTO;
import illvent.backend.wish.service.WishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Wish", description = "관심 행사 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/wish")
public class WishController {

        private final WishService wishService;

        @Operation(summary = "관심 행사를 등록하는 API")
        @PostMapping("/register")
        public ResponseEntity<String> registerWish(@RequestBody WishRegisterRequestDTO wishRegisterRequestDTO){
            wishService.registerWish(wishRegisterRequestDTO);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        @Operation(summary = "특정 관심 행사를 삭제하는 API")
        @DeleteMapping("/delete/")
        public ResponseEntity<String> deleteWish(@RequestBody WishDeleteRequestDTO wishDeleteRequestDTO) {
            wishService.deleteWish(wishDeleteRequestDTO);

            return ResponseEntity.ok().build();
        }
}