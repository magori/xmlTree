package ch.hesso.xmleditor.editdom;


import java.util.List;

/**
 * Permet de manipuler le document;
 */
public interface Manipulater {
    /**
     * Définit le type d'extention que le manipulatuer peut manier.
     */
    String forType();

    /**
     * Retoure le noeud root de notre document.
     */
    Element getRootElement();

    /**
     * @param id      L'identifiant du noeud.
     * @param newText Le nouveau text du noeud.
     *
     * @return L'element modifié.
     */
    Element editElement(String id, String newText);


    /**
     *
     * @param parentId
     * @param name
     * @param text
     * @return
     */
    String addElementToParent(String parentId, String name, String text);

    /**
     * Sauvegarde le document chargé en mémoiore.
     */
    void saveDocument();

    /**
     * Charge le document en mémoire.
     *
     * @param idDocument Peut représenter le chemin de notre fichier.
     */
    void load(String idDocument);

    /**
     * Fetch in db the list of existinng documennts
     *
     * @return List of string
     */
    List<String> fetchDbList();


}
