package io.github.dej2vu.domain.raffle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleStrategyPrize {

    private String prizeCode;
    private BigDecimal rate;


}
