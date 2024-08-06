package de.roamingthings;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class HelloTest {

    @Test
    void should_work() {
        var hello = new Hello();

        assertThat(hello).isNotNull();
    }
}
