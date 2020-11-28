package io.ryecrow.joe;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class OutlookExpressFile implements Closeable {

    /**
     * The content of the DBX file,
     * presented in the form of a {@link RandomAccessFile}
     */
    private RandomAccessFile dbxFile;

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
}
