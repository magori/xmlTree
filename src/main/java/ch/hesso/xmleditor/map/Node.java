package ch.hesso.xmleditor.map;

import java.util.List;

public interface Node {
    /**
     * Permet d'avoir l'identifiant du noeud.
     *
     * @return L'idendifiant du noeud.
     */
    String getId();

    /**
     * Retourne le text du noeud courant.
     *
     * @return le text du noeud.
     */
    String getName();

    /**
     * Retourne le nom du tag du noeud.
     *
     * @return le nom tu tag.
     */
    String getText();

    /**
     * Retourne la liste des enfants du noeud.
     *
     * @return La liste de ces enfants.
     */
    List<NodeImpl> getChildren();
}
