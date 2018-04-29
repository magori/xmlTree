package ch.hesso.xmleditor.editdom;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ManipulaterJsonImplTest {

    @Test
    void parseJson() {
        ManipulaterJsonImpl manipulaterJson = new ManipulaterJsonImpl(null);
        manipulaterJson.parse("{\"brand\" : \"Toyota\", \"doors\" : 5, test:{\"brand\" : \"Toyota\", \"doors\" : 5}}");
        Assertions.assertThat(manipulaterJson.getRootElement().getChildren().get(0).getText()).isEqualTo("Toyota");
        Assertions.assertThat(manipulaterJson.getRootElement().getChildren().get(2).getChildren().get(1).getText()).isEqualTo("5");
    }

    @Test
    void editElement() {
        ManipulaterJsonImpl manipulaterJson = new ManipulaterJsonImpl(null);
        manipulaterJson.parse("{\"brand\" : \"Toyota\", \"doors\" : 5, option:{\"transmition\" : \"auto\", \"motor\" : 1.8}}");
        Assertions.assertThat(manipulaterJson.editElement("2-1", "3").getText()).isEqualTo("3");
    }

}