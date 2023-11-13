package christmas.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.domain.fixture.TestDataFactory;
import christmas.domain.restaurant.Orders;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CustomerTest {

    private static Orders sampleOrders;

    @BeforeAll
    public static void beforeAll() {
        sampleOrders = TestDataFactory.createSampleOrders();
    }

    @Test
    void 방문일자_또는_주문내역이_null일_경우_예외_발생() {
        assertThatThrownBy(() -> Customer.reserveVisit(new VisitDate(1), null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Customer.reserveVisit(null, sampleOrders))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Customer.reserveVisit(null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 총주문_금액을_반환한다() {
        //given
        Customer customer = Customer.reserveVisit(new VisitDate(1), sampleOrders);

        //when
        int totalOrderAmount = customer.getOrderAmount();

        //then
        assertThat(totalOrderAmount).isEqualTo(168_000);
    }
}