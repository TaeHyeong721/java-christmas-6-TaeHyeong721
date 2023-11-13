package christmas.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GiftTest {

    @Test
    void 증정품을_요청하면_샴폐인_1개를_제공한다() {
        //given
        Gift gift = Gift.asGiveaway();
        Map<Menu, Integer> expectedGift = Map.of(
                Menu.CHAMPAGNE, 1
        );

        //when
        Map<Menu, Integer> giftItems = gift.getItems();

        //then
        assertThat(giftItems).isEqualTo(expectedGift);
    }

    @Test
    void 증정품의_총금액을_요청하면_값을_반환한다() {
        //given
        Gift gift = Gift.asGiveaway();
        Map<Menu, Integer> giftItems = gift.getItems();
        int expectedGiftAmount = calculateExpectedTotalGiftAmount(giftItems);

        //when
        int giftAmount = gift.getTotalAmount();

        //then
        assertThat(giftAmount).isEqualTo(expectedGiftAmount);
    }

    private static int calculateExpectedTotalGiftAmount(Map<Menu, Integer> giftItems) {
        return giftItems.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    @Test
    void 증정품이_없으면_빈_객체를_반환한다() {
        //given
        Gift gift = Gift.empty();

        //when
        Map<Menu, Integer> giftItems = gift.getItems();

        //then
        assertThat(gift.isEmpty()).isTrue();
        assertThat(giftItems).isEqualTo(Collections.emptyMap());
    }
}