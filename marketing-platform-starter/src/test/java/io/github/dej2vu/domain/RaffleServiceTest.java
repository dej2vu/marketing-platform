package io.github.dej2vu.domain;


import com.alibaba.fastjson.JSON;
import io.github.dej2vu.domain.raffle.model.RaffleFactor;
import io.github.dej2vu.domain.raffle.model.RafflePrize;
import io.github.dej2vu.domain.raffle.service.RaffleService;
import io.github.dej2vu.domain.raffle.service.rule.impl.WeightRuleHandler;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * @description 抽奖单元测试
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RaffleServiceTest {

    @Resource
    private RaffleService raffleService;

    @Resource
    private WeightRuleHandler weightRuleHandler;

    @Test
    public void should_success_return_prize() {

        ReflectionTestUtils.setField(weightRuleHandler, "userScore", 6000L);

        RaffleFactor raffleFactor = RaffleFactor.builder()
                .strategyCode("100001")
                .userCode("dej2vu")
                .build();
        RafflePrize rafflePrize = raffleService.run(raffleFactor);
        Assertions.assertNotNull(rafflePrize);

        log.info("请求参数：{}", JSON.toJSONString(raffleFactor));
        log.info("测试结果：{}", JSON.toJSONString(rafflePrize));
    }

    @Test
    public void should_success_return_prize_with_blacklist_rule() {

        RaffleFactor raffleFactor = RaffleFactor.builder()
                .strategyCode("100001")
                .userCode("user001")
                .build();
        RafflePrize rafflePrize = raffleService.run(raffleFactor);
        Assertions.assertNotNull(rafflePrize);

        log.info("请求参数：{}", JSON.toJSONString(raffleFactor));
        log.info("测试结果：{}", JSON.toJSONString(rafflePrize));
    }

}
