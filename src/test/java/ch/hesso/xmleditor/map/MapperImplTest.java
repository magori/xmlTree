package ch.hesso.xmleditor.map;

import ch.hesso.xmleditor.editdom.ManipulaterJdomImpl;
import ch.hesso.xmleditor.editdom.ManipulaterJsonImpl;
import ch.hesso.xmleditor.editdom.ManipulaterProvider;
import ch.hesso.xmleditor.editdom.ManipulaterType;
import ch.hesso.xmleditor.persistence.PersisterFileImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MapperImplTest {
    // l'utilisation de mock serai mieux
    private final MapperImpl treeMapping = new MapperImpl(new ManipulaterProvider(new ManipulaterJsonImpl(new PersisterFileImpl()), new ManipulaterJdomImpl(new PersisterFileImpl())));

    @Test
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