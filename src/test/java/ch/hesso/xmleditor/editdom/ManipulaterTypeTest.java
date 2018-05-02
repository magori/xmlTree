package ch.hesso.xmleditor.editdom;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ManipulaterTypeTest {
    @Test
    void parseJson() {
        Assertions.assertThat(ManipulaterType.parse("json")).isEqualTo(ManipulaterType.JSON);
        Assertions.assertThat(ManipulaterType.parse("JSON")).isEqualTo(ManipulaterType.JSON);
    }

    @Test
    void parseXml() {
        Assertions.assertThat(ManipulaterType.parse("xml")).isEqualTo(ManipulaterType.XML);
        Assertions.assertThat(ManipulaterType.parse("XML")).isEqualTo(ManipulaterType.XML);
    }

    @Test
    void parseKo() {
        Assertions.assertThatThrownBy(() -> ManipulaterType.parse("djddj")).isInstanceOf(RuntimeException.class);
    }

}