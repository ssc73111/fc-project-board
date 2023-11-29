package com.fc.projectboard.config;


import com.fc.projectboard.dto.UserAccountDto;
import com.fc.projectboard.service.UserAccountService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class SecurityConfigTest {

    @MockBean
    private UserAccountService userAccountService;
//    private UserAccountRepository userAccountRepository;

    @BeforeTestMethod
    public void securitySetUp() {
//        given(userAccountRepository.findById(anyString()))
//                .willReturn(Optional.of(
//                        UserAccount.of(
//                                "unoTest",
//                                "password",
//                                "uno-test@mail.com",
//                                "uno-test",
//                                "memo test"
//                        )));
        given(userAccountService.searchUser(anyString()))
                .willReturn(Optional.of(createUserAccountDto()));
        given(userAccountService.saveUser(anyString(), anyString(), anyString(), anyString(), anyString()))
                .willReturn(createUserAccountDto());
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "unoTest",
                "password",
                "uno-test@mail.com",
                "uno-test",
                "memo test"
        );
    }
}
