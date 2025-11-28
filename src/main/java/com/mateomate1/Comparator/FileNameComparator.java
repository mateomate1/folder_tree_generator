package com.mateomate1.Comparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Comparator for File objects with customizable sorting rules.
 * Allows adding multiple sorting methods with priority order.
 * Supports reverse sorting and logs warnings when duplicated or invalid
 * sorting methods are added.
 *
 * @author mateomate1
 * @version 1.0.0
 */
public class FileNameComparator implements Comparator<File> {
    private final Logger log = LoggerFactory.getLogger(FileNameComparator.class);

    private List<SortingMethod> priorityList = new ArrayList<>();
    private boolean reverse = false;

    /**
     * Compares two File objects using the configured sorting methods.
     * If no sorting methods are defined, default sorting rules are used.
     *
     * @param f1 first file
     * @param f2 second file
     * @return negative, zero or positive depending on the comparison result
     */

    @Override
    public int compare(File f1, File f2) {
        int output = 0;
        if (!priorityList.isEmpty()) {
            for (SortingMethod priority : priorityList) {
                switch (priority) {
                    case DIRECTORIES_FIRST -> output = compareDirectoriesFirst(f1, f2);
                    case ALPHABETICAL -> output = compareAlphabetical(f1, f2);
                    case EXTENSION -> output = compareExtension(f1, f2);
                    case LAST_MODIFIED -> output = compareLastModified(f1, f2);
                    case SIZE -> output = compareSize(f1, f2);
                    default -> log.warn("Sorting method [" + priority + "] is not defined");
                }
            }
        } else {
            output = compareDefault(f1, f2);
        }
        return reverse ? -output : output;
    }

    /**
     * Adds a sorting method at a given position in the priority list.
     * Validates duplicates and negative positions.
     *
     * @param method   sorting method to add
     * @param position index where the method will be inserted
     */
    public void addSortingMethod(SortingMethod method, int position) {
        if (priorityList.contains(method)) {
            int index = priorityList.indexOf(method);
            log.warn("The shorting method [" + method + "] already exists at the position " + index);
        } else if (position < 0) {
            log.warn("The position can not be negative");
        } else {
            if (position >= priorityList.size()) {
                log.warn("The position is out of bounds, adding last");
                priorityList.addLast(method);
            } else {
                priorityList.add(position, method);
            }
            log.info("Method added correctly");
        }
    }

    /**
     * Adds multiple sorting methods with their specific positions.
     *
     * @param methodsMap map containing sorting methods as key and their target
     *                   positions as value
     */

    public void addAllSortingMethods(Map<SortingMethod, Integer> methodsMap) {
        Set<SortingMethod> methodsSet = methodsMap.keySet();
        for (SortingMethod method : methodsSet) {
            Integer position = methodsMap.get(method);
            if (position != null && position >= 0)
                addSortingMethod(method, position);
        }
    }

    /**
     * Adds a sorting method at the end of the priority list.
     * Duplicate methods are not allowed.
     *
     * @param method sorting method to add
     */
    public void addLast(SortingMethod method) {
        if (priorityList.contains(method)) {
            int index = priorityList.indexOf(method);
            log.warn("The shorting method [" + method + "] already exists at the position " + index);
        } else {
            priorityList.addLast(method);
            log.info("Method added correctly");
        }
    }

    /**
     * Adds a sorting method at the end of the list.
     * Short alternative for addLast.
     *
     * @param method sorting method to add
     */
    public void addSortingMethod(SortingMethod method) {
        addLast(method);
    }

    /**
     * Adds all sorting methods from a list, placing each at the end.
     *
     * @param methods list of sorting methods to add
     */
    public void addAllSortingMethods(List<SortingMethod> methods) {
        for (SortingMethod method : methods) {
            addLast(method);
        }
    }

    /**
     * Defines the default sorting priority order when no methods are configured.
     * Current implementation returns 0 and should be extended to apply real logic.
     *
     * @param f1 first file
     * @param f2 second file
     * @return always 0 in current implementation
     */
    public int compareDefault(File f1, File f2) {
        List<SortingMethod> defaultPriority = new ArrayList<>();
        defaultPriority.add(SortingMethod.DIRECTORIES_FIRST);
        defaultPriority.add(SortingMethod.ALPHABETICAL);
        defaultPriority.add(SortingMethod.EXTENSION);
        defaultPriority.add(SortingMethod.LAST_MODIFIED);
        defaultPriority.add(SortingMethod.SIZE);
        return 0;
    }

    /**
     * Compares two files giving priority to directories over regular files.
     *
     * @param f1 first file
     * @param f2 second file
     * @return -1 if f1 is directory and f2 is not, 1 in the opposite case, 0
     *         otherwise
     */
    public int compareDirectoriesFirst(File f1, File f2) {
        if (f1.isDirectory() && !f2.isDirectory())
            return -1;
        if (!f1.isDirectory() && f2.isDirectory())
            return 1;
        return 0;
    }

    /**
     * Compares two files alphabetically by name, ignoring case.
     *
     * @param f1 first file
     * @param f2 second file
     * @return comparison result based on file names
     */
    public int compareAlphabetical(File f1, File f2) {
        return f1.getName().compareToIgnoreCase(f2.getName());
    }

    /**
     * Compares two files by size measured in bytes.
     *
     * @param f1 first file
     * @param f2 second file
     * @return comparison result based on file lengths
     */
    public int compareSize(File f1, File f2) {
        long s1 = f1.length();
        long s2 = f2.length();
        return Long.compare(s1, s2);
    }

    /**
     * Compares two files by last modification date.
     *
     * @param f1 first file
     * @param f2 second file
     * @return comparison result based on last modified timestamps
     */
    public int compareLastModified(File f1, File f2) {
        long m1 = f1.lastModified();
        long m2 = f2.lastModified();
        return Long.compare(m1, m2);
    }

    /**
     * Compares two files by extension extracted from their names.
     *
     * @param f1 first file
     * @param f2 second file
     * @return comparison result based on extension strings
     */
    public int compareExtension(File f1, File f2) {
        String e1 = getExtension(f1.getName());
        String e2 = getExtension(f2.getName());
        return e1.compareToIgnoreCase(e2);
    }

    /**
     * Extracts the extension from a file name.
     * Returns empty string if no extension exists.
     *
     * @param name file name
     * @return extracted extension or empty string
     */
    private String getExtension(String name) {
        int i = name.lastIndexOf('.');
        if (i == -1)
            return "";
        return name.substring(i + 1);
    }

    /**
     * Enables or disables reverse sorting order.
     *
     * @param reverse true to invert the comparison result
     */
    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

}
