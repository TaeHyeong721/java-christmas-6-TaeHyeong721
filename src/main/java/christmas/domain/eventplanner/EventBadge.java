package christmas.domain.eventplanner;

import java.util.Arrays;
import java.util.Comparator;

public enum EventBadge {
    SANTA(20_000, "산타"),
    TREE(10_000, "트리"),
    STAR(5_000, "별"),
    NONE(0, "없음");

    private final int threshold;
    private final String name;

    EventBadge(int threshold, String name) {
        this.threshold = threshold;
        this.name = name;
    }

    public int getThreshold() {
        return threshold;
    }

    public String getName() {
        return name;
    }

    public static EventBadge fromBenefitAmount(int benefitAmount) {
        return Arrays.stream(EventBadge.values())
                .filter(badge -> benefitAmount >= badge.getThreshold())
                .max(Comparator.comparingInt(EventBadge::getThreshold))
                .orElse(NONE);
    }
}
