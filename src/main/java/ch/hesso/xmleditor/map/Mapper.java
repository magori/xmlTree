package ch.hesso.xmleditor.map;

import java.util.List;

/**
 * Permet de faire le mapping des elements(nods).
 */
public interface Mapper {

    /**
     * Permet l'édition du text de notre noeud.
     *
     * @param id      Identifiant du noeud.
     * @param newText Le nouveau text du noeud.
     */
    void editNode(String id, String newText);

    /**
     * Permet l'édition du nom de l'élément de notre noeud.
     *
     * @param id      Identifiant du noeud.
     * @param newName Le nouveau text du noeud.
     */
    void editNodeName(String id, String newName);

    /**
     * Permet de rajouter un nouveau noeud à l'arbre
     * @return Node object
     */
    Node addNodeToParent(String parentId);

    /**
     * Sauvegarde notre document;
     */
    void saveTree();

    /**
     * Créer la structure de l'arbre et charge le document en mémoire
     *
     * @param idDocument Identifier du document(Path)
     *
     * @return L'arbre.
     */
    Node createTree(String idDocument);

    /**
     * Fetch in db the list of existinng documennts
     *
     * @return List of string
     */
    List<String> fetchDbList();

}
