package christmas.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;

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
    void 총주문_금액을_반환한다() {
        //given
        Customer customer = Customer.reserveVisit(1, sampleOrders);

        //when
        int totalOrderAmount = customer.getOrderAmount();

        //then
        assertThat(totalOrderAmount).isEqualTo(168_000);
    }
}