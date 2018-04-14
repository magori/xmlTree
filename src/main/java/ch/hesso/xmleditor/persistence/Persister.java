package ch.hesso.xmleditor.persistence;

/**
 * Pour gérer la persistances des fichiers xml
 */
public interface Persister {
    /**
     * Permet de sauvegarder le document xml.
     *
     * @param id      L'id représente le path de notre fichier.
     * @param content Le content est le cotenue du fichier.
     */
    void save(String id, String content);

    /**
     * Permet de charger le fichier XML
     *
     * @param id L'id du fichier à charger.
     *
     * @return Le contenue du fichier chargé.
     */
    String load(String id);
}
