package com.example.deliveryoutsourcing.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.deliveryoutsourcing.domain.user.dto.UserResponseDto;
import com.example.deliveryoutsourcing.domain.user.entity.User;
import com.example.deliveryoutsourcing.domain.user.repository.UserRepository;
import com.example.deliveryoutsourcing.global.enums.Role;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;  // 가짜 객체 생성

    @InjectMocks
    private UserServiceImpl userService;  // 가짜 repository가 주입된 userServiceImpl 생성

    @Test
    void 주소가_null이여도_내정보_정상조회_되는지_확인_왜냐_가입시_주소_입력_안받기_때문에() {
        // given
        Long userId = 1L;
        User mockUser = User.builder()  // 테스트용 가짜 user객체
            .id(userId)
            .email("test@test.com")
            .nickname("유저")
            .role(Role.USER)
            .address(null) // 가입할때 주소는 입력 안 받은 상태
            .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));  // findById 호출하면 가짜 user객체 리턴

        // when : 내 정보 조회
        UserResponseDto result = userService.getMyInfo(userId);

        // then
        assertThat(result).isNotNull(); // 결과가 null이 아닌지
        assertThat(result.getId()).isEqualTo(userId); // id값이 일치하는지
        assertThat(result.getEmail()).isEqualTo("test@test.com"); // email이 일치하는지
        assertThat(result.getNickname()).isEqualTo("유저"); // nickname 일치하는지
        assertThat(result.getAddress()).isNull(); // address는 null 맞는지!!!!!!
    }
}
