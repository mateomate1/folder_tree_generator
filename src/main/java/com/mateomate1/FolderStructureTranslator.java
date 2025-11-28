package com.mateomate1;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class that generates a visual text representation of a folder
 * structure similar to the output of the tree command. The generated tree
 * is written into an output file.
 *
 * Supports optional filtering (FileFilter) and optional file ordering
 * using FileNameComparator to customize how directory entries are sorted.
 *
 * @author mateomate1
 * @version 1.0.0
 */
public class FolderStructureTranslator {
    private final Logger log = LoggerFactory.getLogger(FolderStructureTranslator.class);

    private final StringBuilder sb = new StringBuilder();

    /**
     * Generates a folder tree using default options. No filtering and no
     * custom comparison rules are applied.
     *
     * @param carpeta root folder to translate into a tree structure
     */
    public void generateTree(File carpeta) {
        sb.setLength(0);
        print(carpeta, "", true, null, null);
    }

    /**
     * Generates a folder tree using both a FileFilter and a FileNameComparator.
     * The filter decides which files or directories are included, while the
     * comparator determines the sorting order among directory entries.
     *
     * @param carpeta    root folder to translate
     * @param filter     filter used to accept or reject files
     * @param comparator comparator used to sort files in each directory
     */
    public void generateTree(File carpeta, FileFilter filter, Comparator<File> comparator) {
        sb.setLength(0);
        print(carpeta, "", true, filter, comparator);
    }

    /**
     * Generates a folder tree using a custom sorting comparator but without
     * applying any filter. All files are included.
     *
     * @param carpeta    root folder to translate
     * @param comparator comparator used to sort directory contents
     */
    public void generateTree(File carpeta, Comparator<File> comparator) {
        sb.setLength(0);
        print(carpeta, "", true, null, comparator);
    }

    /**
     * Generates a folder tree using a filter but without a custom comparator.
     * Directory entries will be in the default file system order.
     *
     * @param carpeta root folder to translate
     * @param filter  filter applied to file entries
     */
    public void generateTree(File carpeta, FileFilter filter) {
        sb.setLength(0);
        print(carpeta, "", true, filter, null);
    }

    /**
     * Internal recursive method that prints a file or directory entry into the
     * internal StringBuilder, applying the prefix formatting and tree symbols.
     *
     * If the entry is a directory, its children are listed recursively after
     * sorting them (if a comparator is provided) and filtering them.
     *
     * @param f          current file or directory to print
     * @param prefix     indentation characters accumulated from parent levels
     * @param isLast     indicates whether this entry is the last child of its
     *                   parent
     * @param filter     file filter used to include or exclude entries
     * @param comparator comparator used to sort children of directories
     */
    private void print(File f, String prefix, boolean isLast, FileFilter filter, Comparator<File> comparator) {
        if (filter != null && !filter.accept(f))
            return;
        sb.append(prefix);
        if (!prefix.isEmpty())
            sb.append(isLast ? "└── " : "├── ");
        sb.append(f.getName());
        if (f.isDirectory())
            sb.append("/");
        sb.append("\n");

        if (f.isDirectory()) {
            File[] hijos = f.listFiles(filter);
            if (hijos != null) {
                Arrays.sort(hijos, comparator);
                for (int i = 0; i < hijos.length; i++) {
                    boolean ultimoHijo = (i == hijos.length - 1);
                    String nuevoPrefijo = prefix + (isLast ? "    " : "│   ");
                    print(hijos[i], nuevoPrefijo, ultimoHijo, filter, comparator);
                }
            }
        }
    }

    /**
     * Writes the generated tree stored in the StringBuilder into the output file.
     * Logs a trace message if the operation succeeds and an error message if it
     * fails.
     *
     * @param salida output file where the tree will be written
     * @throws IOException if an error occurs while writing the file
     */
    public void flushTree(File salida) throws IOException {
        Writer fw = null;
        PrintWriter pw = null;
        try {
            fw = new OutputStreamWriter(new FileOutputStream(salida), StandardCharsets.UTF_8);
            pw = new PrintWriter(fw);

            pw.print(sb);
            pw.flush();
        } catch (IOException e) {
            log.error("Error durante el acceso para escritura del fichero [" + salida.getAbsolutePath() + "]");
            throw e;
        } finally {
            if (pw != null)
                pw.close();
        }
    }

    /**
     * Returns the internal StringBuilder that contains the generated tree
     * structure.
     *
     * @return
     */
    public StringBuilder getTree() {
        return sb;
    }

}