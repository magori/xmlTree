package ch.hesso.xmleditor.editdom;

import java.util.List;

/**
 * Pour représenter de manière simple un element xml et permet de ne pas avoir de dépance sur une librairie xml.
 */
public interface Element {
    /**
     * Doit retourner tout les enfants du noeud courrant.
     *
     * @return Les enfants du noeud.
     */
    List<Element> getChildren();

    /**
     * Retourne le text du noeud courant.
     *
     * @return le text du noeud.
     */
    String getText();

    /**
     * Retourne le nom du tag du noeud.
     *
     * @return le nom tu tag.
     */
    String getName();
}
