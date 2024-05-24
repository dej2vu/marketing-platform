package io.github.dej2vu.constant;

public class Constants {

    public final static String APPLICATION_NAME = "marketing-platform";
    public final static String SPLIT = ",";
    public final static String COLON = ":";
    public final static String SPACE = " ";
    public final static String UNDERLINE = "_";

    /**
     * 定义出缓存key的前缀标识，
     */
    public static class RedisKey {

        public static String ACTIVITY_KEY = "marketing_platform_activity_key_";
        public static String ACTIVITY_SKU_KEY = "marketing_platform_activity_sku_key_";
        public static String ACTIVITY_COUNT_KEY = "marketing_platform_activity_count_key_";
        public static String RAFFLE_STRATEGY_KEY = "marketing_platform_strategy_key_";
        public static String RAFFLE_STRATEGY_PRIZE_KEY = APPLICATION_NAME + COLON + "raffle_strategy:prize"+ COLON;
        public static String RAFFLE_STRATEGY_PRIZE_LIST_KEY = APPLICATION_NAME + COLON + "raffle_strategy:prize_list"+ COLON;
        public static String RAFFLE_STRATEGY_RATE_RANGE_KEY = APPLICATION_NAME + COLON + "raffle_strategy:rate_range"+ COLON;
        public static String RAFFLE_STRATEGY_PRIZE_SEARCH_TABLE_KEY = APPLICATION_NAME + COLON + "raffle_strategy:prize_search_table"+ COLON;

        public static String RULE_TREE_VO_KEY = "rule_tree_vo_key_";
        public static String STRATEGY_AWARD_COUNT_KEY = "strategy_award_count_key_";
        public static String STRATEGY_AWARD_COUNT_QUERY_KEY = "strategy_award_count_query_key";
        public static String ACTIVITY_SKU_COUNT_QUERY_KEY = "activity_sku_count_query_key";
        public static String ACTIVITY_SKU_STOCK_COUNT_KEY = "activity_sku_stock_count_key_";
    }

}
