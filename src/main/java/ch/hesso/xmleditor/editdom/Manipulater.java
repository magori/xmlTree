package ch.hesso.xmleditor.editdom;


/**
 * Permet de manipuler le document;
 */
public interface Manipulater {
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
     * Sauvegarde le document chargé en mémoiore.
     */
    void saveDocument();

    /**
     * Charge le document en mémoire.
     *
     * @param idDocument Peut représenter le chemin de notre fichier.
     */
    void load(String idDocument);


}
