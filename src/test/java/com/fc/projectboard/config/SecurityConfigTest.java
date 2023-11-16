package com.fc.projectboard.config;

import com.fc.projectboard.domain.UserAccount;
import com.fc.projectboard.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class SecurityConfigTest {

    @MockBean
    private UserAccountRepository userAccountRepository;

    @BeforeTestMethod
    public void securitySetUp() {
        given(userAccountRepository.findById(anyString()))
                .willReturn(Optional.of(
                        UserAccount.of(
                                "unoTest",
                                "password",
                                "uno-test@mail.com",
                                "uno-test",
                                "memo test"
                        )));
    }
}
