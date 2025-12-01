# Folder Tree Generator

Folder Tree Generator is a Java library that generates a directory tree structure in text format. It allows the use of custom file filters and comparators to control which files are included and how they are ordered.

## Features

- Generates a directory tree from a root folder.
- Supports FileFilter to include or exclude files.
- Supports a custom Comparator to sort directory entries.
- Exports the final tree to a text file.
- Includes Javadoc and source code jars in the Maven build.

## Requirements

- Java 17 or higher (compiled with release 17 for compatibility).
- Maven 3.8 or higher.

## Installation

### Prueba el repositorio

## 1. Create/modify the file ~/.m2/settings.xml

```xml
<settings>
    <servers>
        <server>
            <id>github</id>
            <username>YOUR_USER</username>
            <!-- The token must be created with read and write permision at least-->
            <password>TOKEN</password>
        </server>
    </servers>
</settings>

````

## 2. Configurate the file pom.xml

````xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/mateomate1/folder_tree_generator.git</url>
    </repository>
</repositories>

<dependencies>
  <dependency>
        <groupId>com.mateomate1</groupId>
        <artifactId>folder_tree_generator</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
````

## 3. Download and install the dependecy

````bash
mvn clean install
````

### Basic usage

```java
File folder = new File("path/to/source");
File output = new File("tree.txt");


FileStructureFilter filter = new FileStructureFilter();
FileNameComparator<File> comparator = new FileNameComparator();

FolderStructureTranslator translator = new FolderStructureTranslator();
translator.generateTree(folder, output, filter, comparator);
````

### How it works

1. A **FolderStructureTranslator** instance reads the directory recursively.
2. A **FileFilter** controls which files and folders are included.
3. A **Comparator** defines the order of the elements in each directory.
4. The generated tree is saved to the output file.

### Example output

````txt
root/
├── src/
│   ├── main/
│   │   └── java/
│   └── test/
└── README.md
````

### Project structure

````txt
folder_tree_generator/
    ├── src/
    │   └── main/
    │       ├── java/
    │       │   └── com/
    │       │       └── mateomate1/
    │       │           ├── Comparator/
    │       │           │   ├── FileNameComparator.java
    │       │           │   └── SortingMethod.java
    │       │           ├── Filter/
    │       │           │   └── FileStructureFilter.java
    │       │           ├── FolderStructureTranslator.java
    │       │           └── Main.java
    │       └── resources/
    │           └── Logback.xml
    ├── pom.xml
    ├── Readme.md
````

### MIT License

Copyright (c) 2025 Mateo Ayarra

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
