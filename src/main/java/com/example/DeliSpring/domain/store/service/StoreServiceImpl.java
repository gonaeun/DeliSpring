package com.example.DeliSpring.domain.store.service;

import com.example.DeliSpring.config.PasswordEncoder;
import com.example.DeliSpring.domain.menu.dto.MenuResponseDto;
import com.example.DeliSpring.domain.store.dto.StoreRequestDto;
import com.example.DeliSpring.domain.store.dto.StoreResponseDto;
import com.example.DeliSpring.domain.store.entity.Store;
import com.example.DeliSpring.domain.store.repository.StoreRepository;
import com.example.DeliSpring.domain.user.entity.User;
import com.example.DeliSpring.domain.user.repository.UserRepository;
import com.example.DeliSpring.global.error.ApiException;
import com.example.DeliSpring.global.error.response.ErrorType;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createStore(Long ownerId, StoreRequestDto.Create requestDto) {
        User owner = userRepository.findById(ownerId)   // db에서 유저 조회
            .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

        // 가게수 3개 초과하는지 체크
        Long storeCount = storeRepository.countByOwnerIdAndIsStoreClosedFalse(ownerId);
        if (storeCount >= 3) {
            throw new ApiException(ErrorType.STORE_LIMIT_EXCEEDED);
        }

        // Store 엔티티 생성해서 db에 저장
        Store store = Store.builder()
            .owner(owner)
            .name(requestDto.getName())
            .openTime(LocalTime.parse(requestDto.getOpenTime()))
            .closeTime(LocalTime.parse(requestDto.getCloseTime()))
            .minOrderPrice(requestDto.getMinOrderPrice())
            .build();

        storeRepository.save(store);
    }

    @Override
    @Transactional(readOnly = true)  // 읽기 전용(db 조회만)
    public List<StoreResponseDto> getStores(String name) {  // 가게 목록 조회
        List<Store> stores;

        // if 이름으로 검색하거나
        // else 검색어가 없으면 운영 중인 가게 전체에서 조회
        if (name != null && !name.trim().isEmpty()) { // 검색어가 있거나, 공백만 입력하는 경우 제외
            stores = storeRepository.findAllByNameContainingIgnoreCase(name)
                .stream()
                .filter(store -> !store.isStoreClosed())  // 폐업하지 않은 가게만 검색하도록 필터링
                .toList();
        } else {
            stores = storeRepository.findAll()
                .stream()
                .filter(store -> !store.isStoreClosed())  // 폐업하지 않은 가게만 검색하도록 필터링
                .toList();
        }

        return stores.stream()
            .map(store -> StoreResponseDto.builder()
                .storeId(store.getId())
                .name(store.getName())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .minOrderPrice(store.getMinOrderPrice())
                .isClosed(store.isStoreClosed())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StoreResponseDto getStore(Long storeId) {  // 가게 단건 조회
        Store store = storeRepository.findByIdAndIsStoreClosedFalse(storeId)  // 파라미터로 입력받은 가게id를 db에서 조회
            .orElseThrow(() -> new ApiException(ErrorType.STORE_NOT_FOUND));

        // 가게 전체 조회와 다른점
        // 가게 전체 조회는 findById-> builder 하는데
        // 🌟가게 단건 조회는 메뉴도 보여주기 위해, 중간에 store.getMenus() 접근해서 삭제안된 메뉴만 골라서 리스트화 해준다!
        List<MenuResponseDto> menus = store.getMenus().stream()
            .filter(menu -> !menu.isMenuDeleted()) // 삭제 안 된 메뉴만
            .map(menu -> MenuResponseDto.builder()
                .menuId(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .build())
            .toList();

        return StoreResponseDto.builder()
            .storeId(store.getId())
            .name(store.getName())
            .openTime(store.getOpenTime())
            .closeTime(store.getCloseTime())
            .minOrderPrice(store.getMinOrderPrice())
            .isClosed(store.isStoreClosed())
            .menus(menus)
            .build();
    }


    @Override
    @Transactional
    public void updateStore(Long ownerId, Long storeId, StoreRequestDto.Update requestDto) { // 가게 정보 수정
        Store store = storeRepository.findById(storeId)  // 받아온 storeId로 수정할 가게 찾음
            .orElseThrow(() -> new ApiException(ErrorType.STORE_NOT_FOUND));

        if (store.isStoreClosed()) { // 폐업한 가게는 수정 못함
            throw new ApiException(ErrorType.STORE_ALREADY_CLOSED);
        }

        if (!store.getOwner().getId().equals(ownerId)) { // 본인 가게인 경우에만 수정 가능
            throw new ApiException(ErrorType.STORE_OWNER_FORBIDDEN);
        }

        store.update(
            requestDto.getName(),
            LocalTime.parse(requestDto.getOpenTime()),  // dto를 통해서 들어온 문자열을 LocalTime 객체로 변환(parse)
            LocalTime.parse(requestDto.getCloseTime()),
            requestDto.getMinOrderPrice()
        );
    }


    @Override
    @Transactional
    public void closeStore(Long ownerId, Long storeId, String password) { // 가게 폐업
        Store store = storeRepository.findById(storeId)  // storeId로 db에서 가게 조회
            .orElseThrow(() -> new ApiException(ErrorType.STORE_NOT_FOUND));

        if (store.isStoreClosed()) { // 폐업여부 체크
            throw new ApiException(ErrorType.STORE_ALREADY_CLOSED);
        }

        if (!store.getOwner().getId().equals(ownerId)) {  // 사장님이신지 체크
            throw new ApiException(ErrorType.STORE_OWNER_FORBIDDEN);
        }

        User owner = store.getOwner();  // 사장님 맞군여

        if (!passwordEncoder.matches(password, owner.getPassword())) {  // 비밀번호 일치해야 폐업 가능
            throw new ApiException(ErrorType.INVALID_PASSWORD);
        }

        store.close(); // soft delete 실행!
    }

}
