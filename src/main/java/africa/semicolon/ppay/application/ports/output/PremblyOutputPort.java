package africa.semicolon.ppay.application.ports.output;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.VerifyUserIdentityDto;

public interface PremblyOutputPort {
    StringBuilder verifyUserIdentity(VerifyUserIdentityDto userIdentity);
}
