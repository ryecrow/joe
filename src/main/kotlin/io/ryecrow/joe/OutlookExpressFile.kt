package io.ryecrow.joe

import io.ryecrow.joe.internal.DatabaseType
import io.ryecrow.joe.internal.getDatabaseTypeByUid
import java.io.Closeable
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.ByteBuffer

/**
 * An Outlook Express Database File
 */
class OutlookExpressFile private constructor(file: RandomAccessFile) : Closeable {

    /**
     * The content of the DBX file,
     * presented in the form of a RandomAccessFile
     * @see RandomAccessFile
     */
    private val dbxFile: RandomAccessFile?

    /**
     *
     */
    private var dbType: DatabaseType? = null

    constructor(file: File?) : this(RandomAccessFile(file, "r")) {}
    constructor(filePath: String?) : this(RandomAccessFile(filePath, "r")) {}

    /**
     * Close the dbx file
     */
    @kotlin.jvm.Throws(IOException::class)
    fun dispose() {
        close()
    }

    @kotlin.jvm.Throws(IOException::class)
    override fun close() {
        dbxFile?.close()
    }

    @kotlin.jvm.Throws(IOException::class)
    private fun checkFile() {
        val header = ByteArray(28)
        dbxFile!!.read(header)
        val buf = ByteBuffer.wrap(header)
        val magic = buf.int
        if (magic != -0x3052ed02) {
            throw IOException("Invalid file header. Expected: 0xcfad12fe. Actual: 0x" + Integer.toHexString(magic))
        }
        val ft = buf.int
        val dbType = getDatabaseTypeByUid(ft)
        if (dbType == DatabaseType.UNKNOWN) {
            throw IOException("Invalid file type: 0x" + Integer.toHexString(ft))
        }
        this.dbType = dbType
        if (buf.long != 0x66e3d1119a4e00c0L && buf.long != 0x4fa309d405000000L && buf.int != 0x05000000) {
            throw IOException("Invalid DBX file format")
        }
    }

    init {
        dbxFile = file
        checkFile()
    }
}