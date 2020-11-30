package io.ryecrow.joe;

import io.ryecrow.joe.internal.DatabaseType;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class OutlookExpressFile implements Closeable {

    /**
     * The content of the DBX file,
     * presented in the form of a {@link RandomAccessFile}
     */
    private final RandomAccessFile dbxFile;

    /**
     *
     */
    private DatabaseType dbType;

    public OutlookExpressFile(File file) throws IOException {
        this(new RandomAccessFile(file, "r"));
    }

    public OutlookExpressFile(String filePath) throws IOException {
        this(new RandomAccessFile(filePath, "r"));
    }

    /**
     * Constructor
     *
     * @param file the {@link RandomAccessFile} file
     */
    private OutlookExpressFile(RandomAccessFile file) throws IOException {
        this.dbxFile = file;
        checkFile();
    }

    /**
     * Close the dbx file
     */
    public void dispose() throws IOException {
        close();
    }

    @Override
    public void close() throws IOException {
        if (this.dbxFile != null) {
            this.dbxFile.close();
        }
    }

    private void checkFile() throws IOException {
        int magic = this.dbxFile.readInt();
        if (magic != 0xcfad12fe) {
            throw new IOException("Invalid file header. Expected: 0xcfad12fe. Actual: 0x" + Integer.toHexString(magic));
        }
        int ft = this.dbxFile.readInt();
        DatabaseType dbType = DatabaseType.getByUid(ft);
        if (dbType == null) {
            throw new IOException("Invalid file type: 0x" + Integer.toHexString(ft));
        }
        this.dbType = dbType;

        if ((dbxFile.readLong() != 0x66e3d1119a4e00c0L) && (dbxFile.readLong() != 0x4fa309d405000000L)) {
            throw new IOException("Invalid DBX file format");
        }
        this.dbxFile.seek(0);
    }
}
