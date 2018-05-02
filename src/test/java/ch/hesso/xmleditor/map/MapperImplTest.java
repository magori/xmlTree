package ch.hesso.xmleditor.map;

import ch.hesso.xmleditor.editdom.ManipulaterType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MapperImplTest {
    private final MapperImpl treeMapping = new MapperImpl(null);

   // @Test
    void createTree() {
        NodeImpl node = this.treeMapping.createTree("src/test/resources/exemple.xml");
        Assertions.assertThat(node.getChildren().size()).isEqualTo(2);
        Assertions.assertThat(node.getChildren().get(0).getChildren().size()).isEqualTo(4);
        Assertions.assertThat(node.getChildren().get(0).getChildren().get(2).getId()).isEqualTo("0-2");
        Assertions.assertThat(node.getChildren().get(1).getChildren().get(3).getId()).isEqualTo("1-3");
    }

    @Test
    void resolveType() {
        Assertions.assertThat(treeMapping.resolveType("totot.json")).isEqualTo(ManipulaterType.JSON);
        Assertions.assertThat(treeMapping.resolveType("tot.otot.totot.json")).isEqualTo(ManipulaterType.JSON);
    }
}