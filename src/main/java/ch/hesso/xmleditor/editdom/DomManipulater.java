package ch.hesso.xmleditor.editdom;

import org.jdom2.Element;

public interface DomManipulater {
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

    /**
     * Retoure le noeud root de notre document.
     */
    Element getRootElement();
}
