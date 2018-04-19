package ch.hesso.xmleditor.map;

import ch.hesso.xmleditor.editdom.DomManipulaterJdomImpl;
import ch.hesso.xmleditor.persistence.PersisterFileImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MapperJdomImplTest {
    private final MapperJdomImpl treeMapping = new MapperJdomImpl(new DomManipulaterJdomImpl(new PersisterFileImpl()));

    @Test
    void createTree() {
        NodeImpl node = this.treeMapping.createTree("src/test/resources/exemple.xml");
        Assertions.assertThat(node.getChildren().size()).isEqualTo(2);
        Assertions.assertThat(node.getChildren().get(0).getChildren().size()).isEqualTo(4);
        Assertions.assertThat(node.getChildren().get(0).getChildren().get(2).getId()).isEqualTo("0-2");
        Assertions.assertThat(node.getChildren().get(1).getChildren().get(3).getId()).isEqualTo("1-3");
    }
}