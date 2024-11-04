package africa.semicolon.ppay.infrastructure.adapter.input.rest;

import africa.semicolon.ppay.application.ports.input.premblyUseCase.VerifyIdentityUseCase;
import africa.semicolon.ppay.domain.service.PremblyIdentityVerificationService;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.VerifyUserIdentityDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/prembly")
public class PremblyVerificationController {
    private final VerifyIdentityUseCase verifyIdentityUseCase;

    public PremblyVerificationController( VerifyIdentityUseCase verifyIdentityUseCase) {
        this.verifyIdentityUseCase = verifyIdentityUseCase;
    }

    @PostMapping("/verify")
    @PreAuthorize("hasRole('USERS')")
    public ResponseEntity<?> verifyIdentity(@Valid VerifyUserIdentityDto dto){
        var response = verifyIdentityUseCase.verifyUserIdentity(dto);
        return ResponseEntity.status(OK).body(new ApiResponse<>("SuccessFull", response,true));
    }
}
