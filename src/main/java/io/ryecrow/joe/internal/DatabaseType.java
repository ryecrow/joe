package io.ryecrow.joe.internal;

/**
 * Type of a dbx file
 *
 * @author ryecrow
 * @since 1.0
 */
public enum DatabaseType {

    MESSAGE_DATABASE(0xc5fd746f),
    FOLDER_DATABASE(0xc6fd746f),
    OFFLINE(0x309dfe26);

    private final int uid;

    DatabaseType(int uid) {
        this.uid = uid;
    }

    public static DatabaseType getByUid(int uid) {
        for (DatabaseType ft : values()) {
            if (ft.getUid() == uid) {
                return ft;
            }
        }
        return null;
    }

    public int getUid() {
        return uid;
    }
}
