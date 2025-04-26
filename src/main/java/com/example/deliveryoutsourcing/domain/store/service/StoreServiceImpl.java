package com.example.deliveryoutsourcing.domain.store.service;

import com.example.deliveryoutsourcing.domain.store.dto.StoreRequestDto;
import com.example.deliveryoutsourcing.domain.store.dto.StoreResponseDto;
import com.example.deliveryoutsourcing.domain.store.repository.StoreRepository;
import com.example.deliveryoutsourcing.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Override
    public void createStore(Long ownerId, StoreRequestDto.Create requestDto) {

    }

}
