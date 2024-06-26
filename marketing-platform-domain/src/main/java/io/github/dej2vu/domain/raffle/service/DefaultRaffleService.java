package io.github.dej2vu.domain.raffle.service;


import io.github.dej2vu.constant.enums.ActionType;
import io.github.dej2vu.constant.enums.RuleModel;
import io.github.dej2vu.domain.raffle.model.RaffleFactor;
import io.github.dej2vu.domain.raffle.model.RuleAction;
import io.github.dej2vu.domain.raffle.model.RuleMaterial;
import io.github.dej2vu.domain.raffle.repository.RaffleRepository;
import io.github.dej2vu.domain.raffle.service.rule.RuleHandler;
import io.github.dej2vu.domain.raffle.service.rule.factory.RuleHandlerFactory;
import io.github.dej2vu.domain.raffle.service.strategy.RaffleStrategyDispatcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @description 默认的抽奖业务实现
 *
 * @author dej2vu
 * @create 2024-05-28
 */
@Slf4j
@Service
public class DefaultRaffleService extends AbstractRaffleService {

    private final RuleHandlerFactory ruleHandlerFactory;

    public DefaultRaffleService(RaffleRepository raffleRepository,
                                RaffleStrategyDispatcher raffleStrategyDispatcher,
                                RuleHandlerFactory ruleHandlerFactory) {
        super(raffleRepository, raffleStrategyDispatcher);
        this.ruleHandlerFactory = ruleHandlerFactory;
    }

    @Override
    protected RuleAction<RuleAction.PreAction> doRreAction(RaffleFactor raffleFactor, String... ruleModels) {
        if(Objects.isNull(ruleModels) || ruleModels.length == 0){
            return RuleAction.<RuleAction.PreAction>builder().actionType(ActionType.LET_PASS).build();
        }

        Map<String, RuleHandler<RuleAction.PreAction>> ruleHandlerGroup = ruleHandlerFactory.openRuleHandler();

        // 优先处理黑名单规则
        Optional<String> blacklistRuleOpt = Arrays.stream(ruleModels)
                .filter(ruleModel -> RuleModel.BLACKLIST.getCode().equals(ruleModel))
                .findFirst();
        if(blacklistRuleOpt.isPresent()){
            RuleMaterial ruleMaterial = RuleMaterial.builder()
                    .userCode(raffleFactor.getUserCode())
                    .strategyCode(raffleFactor.getStrategyCode())
                    .build();
            RuleHandler<RuleAction.PreAction> blacklistRuleHandler = ruleHandlerGroup.get(RuleModel.BLACKLIST.getCode());
            RuleAction<RuleAction.PreAction> ruleAction = blacklistRuleHandler.handle(ruleMaterial);
            if (ActionType.TAKE_OVER.equals(ruleAction.getActionType())) {
                return ruleAction;
            }
        }

        // 顺序处理剩余规则
        List<String> ruleModelsWithoutBlacklist = Arrays.stream(ruleModels)
                .filter(ruleModel -> !RuleModel.BLACKLIST.getCode().equals(ruleModel))
                .toList();

        RuleAction<RuleAction.PreAction> ruleAction = null;

        for (String ruleModel : ruleModelsWithoutBlacklist) {

            RuleMaterial ruleMaterial = RuleMaterial.builder()
                    .userCode(raffleFactor.getUserCode())
                    .strategyCode(raffleFactor.getStrategyCode())
                    .build();

            ruleAction = ruleHandlerGroup.get(ruleModel).handle(ruleMaterial);
            log.info("抽奖-前置规则处理 userCode: {}, strategyCode: {}, ruleModel: {} actionType: {}",
                    raffleFactor.getUserCode(), raffleFactor.getStrategyCode(), ruleModel,
                    ruleAction.getActionType().name());
            if (ActionType.TAKE_OVER.equals(ruleAction.getActionType())) {
                return ruleAction;
            }
        }

        return ruleAction;
    }

    @Override
    protected RuleAction<RuleAction.InAction> doInAction(RaffleFactor raffleFactor, String... ruleModels) {
        if(Objects.isNull(ruleModels) || ruleModels.length == 0){
            return RuleAction.<RuleAction.InAction>builder().actionType(ActionType.LET_PASS).build();
        }

        Map<String, RuleHandler<RuleAction.InAction>> ruleHandlerGroup = ruleHandlerFactory.openRuleHandler();

        RuleAction<RuleAction.InAction> ruleAction = null;

        for (String ruleModel : ruleModels) {

            RuleMaterial ruleMaterial = RuleMaterial.builder()
                    .userCode(raffleFactor.getUserCode())
                    .strategyCode(raffleFactor.getStrategyCode())
                    .prizeCode(raffleFactor.getPrizeCode())
                    .build();

            ruleAction = ruleHandlerGroup.get(ruleModel).handle(ruleMaterial);
            log.info("抽奖-中置规则处理 userCode: {}, strategyCode: {}, prizeCode: {}, ruleModel: {} actionType: {}",
                    raffleFactor.getUserCode(), raffleFactor.getStrategyCode(), raffleFactor.getPrizeCode(), ruleModel,
                    ruleAction.getActionType().name());
            if (ActionType.TAKE_OVER.equals(ruleAction.getActionType())) {
                return ruleAction;
            }
        }

        return ruleAction;
    }
}
