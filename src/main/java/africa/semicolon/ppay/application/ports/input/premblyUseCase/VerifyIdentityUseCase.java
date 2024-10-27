package africa.semicolon.ppay.application.ports.input.premblyUseCase;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.VerifyUserIdentityDto;

public interface VerifyIdentityUseCase {
    StringBuilder verifyUserIdentity(VerifyUserIdentityDto dto);
}
