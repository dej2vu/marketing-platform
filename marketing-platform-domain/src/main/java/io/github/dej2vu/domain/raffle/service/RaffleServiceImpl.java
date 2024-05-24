package io.github.dej2vu.domain.raffle.service;

import io.github.dej2vu.domain.raffle.repository.RaffleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RaffleServiceImpl implements RaffleService {

    private final RaffleRepository raffleRepository;

    @Override
    public boolean assembleStrategy(String strategyCode) {
        return false;
    }

    @Override
    public String getRandomResult(String strategyCode) {
        return "";
    }
}
