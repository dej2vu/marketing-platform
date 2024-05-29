package io.github.dej2vu.domain.raffle.model;

import io.github.dej2vu.constant.enums.RuleModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.function.Predicate;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleModels {

    private String[] ruleModels;

    public String[] preActionRuleModels() {
        return filterRuleModels(RuleModel::isPreAction);
    }

    public String[] inActionRuleModels() {
        return filterRuleModels(RuleModel::isInAction);
    }

    public String[] postActionRuleModels() {
        return filterRuleModels(RuleModel::isPostAction);
    }

    private String[] filterRuleModels(Predicate<RuleModel> predicate) {
        return Arrays.stream(ruleModels)
                .filter(code -> {
                    RuleModel ruleModel = RuleModel.codeOf(code);
                    return predicate.test(ruleModel);
                }).toArray(String[]::new);
    }

}
