package ch.hesso.xmleditor.display;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PropertiePersisterTypeTest {

    @Test
    void getKey() {
        Assertions.assertThat(PropertiePersisterType.getKey()).isEqualTo("persister");
    }

    @Test
    void isDb() {
        Assertions.assertThat(PropertiePersisterType.DB.isDb()).isTrue();
    }

    @Test
    void isFile() {
        Assertions.assertThat(PropertiePersisterType.FILE.isFile()).isTrue();
    }
}