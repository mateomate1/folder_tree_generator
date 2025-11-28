package com.mateomate1.Filter;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * File filter that allows including or excluding files based on their
 * names or extensions. Directories are accepted unless their name is
 * explicitly excluded.
 *
 * The filter supports four optional lists:
 * - includedExtensions: only files with these extensions will be accepted
 * - excludedExtensions: files with these extensions will be rejected
 * - includedFiles: file names that are always accepted
 * - excludedFiles: file names that are always rejected
 *
 * If multiple lists are configured, the logic inside accept() determines
 * the priority between them.
 *
 * @author mateomate1
 * @version 1.0.0
 */
public class FileStructureFilter implements FileFilter {

    private List<String> excludedExtensions = null;
    private List<String> includedExtensions = null;
    private List<String> excludedFiles = null;
    private List<String> includedFiles = null;

    /**
     * Decides whether a given File should be accepted based on the configured
     * extension and file name rules.
     *
     * Logic summary:
     * - Directories are accepted unless their name is inside excludedFiles.
     * - If includedExtensions is defined, a file must match one of them.
     * - If excludedExtensions is defined, files matching one of them are rejected,
     * unless includedFiles explicitly contains the file name.
     * - File name lists (includedFiles and excludedFiles) override extension rules.
     *
     * @param f file to evaluate
     * @return true if the file is accepted, false otherwise
     */
    @Override
    public boolean accept(File f) {
        String name = f.getName();
        if (f.isDirectory()) {
            if (excludedFiles != null && excludedFiles.contains(name)) {
                return false;
            }
            return true;
        }

        int dot = name.lastIndexOf('.');
        String ext = dot != -1 ? name.substring(dot + 1).toLowerCase() : "";

        if (includedExtensions != null && includedExtensions.contains(ext)) {
            if (excludedFiles != null && excludedFiles.contains(name))
                return false;
            return true;
        } else if (excludedExtensions != null && excludedExtensions.contains(ext)) {
            if (includedFiles != null && includedFiles.contains(name))
                return true;
            return false;
        }
        return true;
    }

    /**
     * Gets the list of excluded extensions.
     *
     * @return list of extensions that should be excluded, or null if not defined
     */
    public List<String> getExcludedExtensions() {
        return excludedExtensions;
    }

    /**
     * Sets the list of extensions that should be excluded.
     *
     * @param excludedExtensions list of extensions to exclude
     */
    public void setExcludedExtensions(List<String> excludedExtensions) {
        this.excludedExtensions = excludedExtensions;
    }

    /**
     * Gets the list of included extensions.
     *
     * @return list of extensions that should be included, or null if not defined
     */
    public List<String> getIncludedExtensions() {
        return includedExtensions;
    }

    /**
     * Sets the list of extensions that should be included.
     *
     * @param includedExtensions list of extensions to include
     */
    public void setIncludedExtensions(List<String> includedExtensions) {
        this.includedExtensions = includedExtensions;
    }

    /**
     * Gets the list of excluded file names.
     *
     * @return list of file names that should be excluded, or null if not defined
     */
    public List<String> getExcludedFiles() {
        return excludedFiles;
    }

    /**
     * Sets the list of file names that should be excluded.
     *
     * @param excludedFiles list of file names to exclude
     */
    public void setExcludedFiles(List<String> excludedFiles) {
        this.excludedFiles = excludedFiles;
    }

    /**
     * Gets the list of included file names.
     *
     * @return list of file names that should be included, or null if not defined
     */
    public List<String> getIncludedFiles() {
        return includedFiles;
    }

    /**
     * Sets the list of file names that should be included.
     *
     * @param includedFiles list of file names to include
     */
    public void setIncludedFiles(List<String> includedFiles) {
        this.includedFiles = includedFiles;
    }

    /**
     * Adds an extension to the list of excluded extensions.
     *
     * @param ext extension to exclude
     */
    public void addExcludedExtension(String ext) {
        if (excludedExtensions == null) {
            excludedExtensions = new ArrayList<>();
        }
        excludedExtensions.add(ext);
    }

    /**
     * Adds an extension to the list of included extensions.
     *
     * @param ext extension to include
     */
    public void addIncludedExtension(String ext) {
        if (includedExtensions == null) {
            includedExtensions = new ArrayList<>();
        }
        includedExtensions.add(ext);
    }

    /**
     * Adds a file name to the list of excluded files.
     *
     * @param file file name to exclude
     */
    public void addExcludedFile(String file) {
        if (excludedFiles == null) {
            excludedFiles = new ArrayList<>();
        }
        excludedFiles.add(file);
    }

    /**
     * Adds a file name to the list of included files.
     *
     * @param file file name to include
     */
    public void addIncludedFile(String file) {
        if (includedFiles == null) {
            includedFiles = new ArrayList<>();
        }
        includedFiles.add(file);
    }

    /**
     * Adds multiple extensions to the list of excluded extensions.
     *
     * @param list list of extensions to exclude
     */
    public void addExcludedExtensions(List<String> list) {
        if (excludedExtensions == null) {
            excludedExtensions = new ArrayList<>();
        }
        excludedExtensions.addAll(list);
    }

    /**
     * Adds multiple extensions to the list of included extensions.
     *
     * @param list list of extensions to include
     */
    public void addIncludedExtensions(List<String> list) {
        if (includedExtensions == null) {
            includedExtensions = new ArrayList<>();
        }
        includedExtensions.addAll(list);
    }

    /**
     * Adds multiple file names to the list of excluded files.
     *
     * @param list list of file names to exclude
     */
    public void addExcludedFiles(List<String> list) {
        if (excludedFiles == null) {
            excludedFiles = new ArrayList<>();
        }
        excludedFiles.addAll(list);
    }

    /**
     * Adds multiple file names to the list of included files.
     *
     * @param list list of file names to include
     */
    public void addIncludedFiles(List<String> list) {
        if (includedFiles == null) {
            includedFiles = new ArrayList<>();
        }
        includedFiles.addAll(list);
    }

    /**
     * Loads patterns from a .gitignore file and adds them to the filter.
     *
     * @implNote implement gitignore parsing and list population
     * @hidden
     */
    public void addExcludeFromGitignore() {
        // TODO: hacer la implementacion
    }

}
