package com.mateomate1.Comparator;

/**
 * Enum that defines the available sorting methods used by FileNameComparator.
 * Each constant represents one type of comparison rule that can be applied
 * to File objects during sorting operations.
 *
 * @author mateomate1
 * @version 1.0.0
 */
public enum SortingMethod {
    DIRECTORIES_FIRST,
    EXTENSION,
    SIZE,
    LAST_MODIFIED,
    ALPHABETICAL
}
