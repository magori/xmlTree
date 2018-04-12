package ch.hesso.xmleditor.map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TreeMappingTest {
    private TreeMapping treeMapping = new TreeMapping();

    @Test
    void createTree() {
        Node node = this.treeMapping.createTree("src/test/resources/exemple.xml");
        Assertions.assertThat(node.getChildren().size()).isEqualTo(2);
        Assertions.assertThat(node.getChildren().get(0).getChildren()).isEqualTo(4);
        Assertions.assertThat(node.getChildren().get(0).getChildren().get(2).getId()).isEqualTo("0-2");
        Assertions.assertThat(node.getChildren().get(1).getChildren().get(3).getId()).isEqualTo("1-3");
    }
}