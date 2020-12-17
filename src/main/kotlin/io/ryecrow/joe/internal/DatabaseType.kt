package io.ryecrow.joe.internal

/**
 * Type of a dbx file
 *
 * @author ryecrow
 * @since 1.0
 */
enum class DatabaseType(val uid: Int) {

    UNKNOWN(0),
    MESSAGE_DATABASE(-0x3a028b91),
    FOLDER_DATABASE(-0x39028b91),
    OFFLINE(0x309dfe26);
}

fun getDatabaseTypeByUid(uid: Int): DatabaseType {
    for (ft in DatabaseType.values()) {
        if (ft.uid == uid) {
            return ft
        }
    }
    return DatabaseType.UNKNOWN
}

