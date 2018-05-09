package ch.hesso.xmleditor.display;

enum PropertiePersisterType {
    DB, FILE;

    public static String getKey() {
        return "persister";
    }

    boolean isDb() {
        return DB.equals(this);
    }

    boolean isFile() {
        return FILE.equals(this);
    }
}
