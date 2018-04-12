package ch.hesso.xmleditor.map;

public interface Mapper {

    /**
     * Permet l'édition du text de notre noeud.
     *
     * @param idDocument          Identifiant du noeud.
     * @param newText     Le nouveau text du noeud.
     */
    void editNode(String idDocument, String newText);

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
}
