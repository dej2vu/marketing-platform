package io.github.dej2vu.domain;


import io.github.dej2vu.domain.raffle.service.strategy.RaffleStrategyService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * @description 抽奖单元测试
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RaffleStrategyServiceTest {

    @Resource
    private RaffleStrategyService raffleStrategyService;

    @Test
    public void should_success_assemble_strategy() {
        boolean result = raffleStrategyService.assemble("100001");
        Assertions.assertTrue(result);
    }

    @Test
    public void should_success_return_prize_code_when_use_random_strategy() {

        String prizeCodes = IntStream.range(0, 100)
                .mapToObj(i -> raffleStrategyService.dispatchWithRandom("100001"))
                .collect(Collectors.joining(", ", "{", "}"));

        log.info("随机抽取100次：prizeCodes:{}", prizeCodes);
    }


    @Test
    public void should_success_return_prize_code_when_use_weight_strategy() {

        String prizeCodes = IntStream.range(0, 100)
                .mapToObj(i -> raffleStrategyService.dispatchWithWeight("100001", "6000:102,103,104,105,106,107,108,109"))
                .collect(Collectors.joining(", ", "{", "}"));

        log.info("随机抽取100次：prizeCodes:{}", prizeCodes);
    }
}
