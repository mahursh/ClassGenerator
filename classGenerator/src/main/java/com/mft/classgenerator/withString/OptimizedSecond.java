package com.mft.classgenerator.withString;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("All")
public class OptimizedSecond {

    private static final Map<String, String> primitiveToWrapperMap = new HashMap<>();

    static {

        // TODO: Date and LocalDateTime and there required imports

        primitiveToWrapperMap.put("byte", "Byte");
        primitiveToWrapperMap.put("short", "Short");
        primitiveToWrapperMap.put("int", "Integer");
        primitiveToWrapperMap.put("long", "Long");
        primitiveToWrapperMap.put("float", "Float");
        primitiveToWrapperMap.put("double", "Double");
        primitiveToWrapperMap.put("char", "Character");
        primitiveToWrapperMap.put("boolean", "Boolean");
        primitiveToWrapperMap.put("string", "String");
    }


    public static void createJavaFile(String className, Map<String, String> fields) throws IOException {


        System.out.println("createJavaFile() called");
        System.out.println("Calss Name :" + className);
        System.out.println("Fields Map :" + fields.entrySet().toString());


        // Content to write to the file
        String fileContent = generateClassSyntax(className, fields);



    }

    private static String generateClassSyntax(String className, Map<String, String> fields) throws IOException {
        // Generate class content

        System.out.println("generateClassSyntax() called");

        StringBuilder classContent = new StringBuilder();
        classContent.append("package com.mft.classgenerator.withString.entity;\n\n");


        // Add imports
        classContent.append("import lombok.Getter;\n");
        classContent.append("import lombok.Setter;\n");
        classContent.append("import lombok.NoArgsConstructor;\n");
        classContent.append("import lombok.AllArgsConstructor;\n");
        classContent.append("import lombok.Builder;\n");

        classContent.append("import jakarta.persistence.Entity;\n");
        classContent.append("import jakarta.persistence.Table;\n");
        classContent.append("import jakarta.persistence.Id;\n");
        classContent.append("import jakarta.persistence.Column;\n\n");

        classContent.append("import com.google.gson.Gson;\n\n");

        // Add annotations
        classContent.append("@Entity(name = \"").append(className.toLowerCase()).append("Entity\")\n");
        classContent.append("@Table(name = \"").append(className.toLowerCase()).append("_tbl\")\n");

        classContent.append("@Getter\n");
        classContent.append("@Setter\n");
        classContent.append("@NoArgsConstructor\n");
        classContent.append("@AllArgsConstructor\n");
        classContent.append("@Builder\n");


        classContent.append("public class ").append(className).append(" {\n\n");

        // Add @Id annotation above default id field
        classContent.append("\t@Id\n");
        classContent.append("\tprivate Long id;\n\n");


        for (Map.Entry<String, String> entry : fields.entrySet()) {
            String fieldName = entry.getKey();
            String fieldType = entry.getValue();

            // Add @Column annotation above each field
            classContent.append("\t@Column(name = \"").append(fieldName).append("\")\n");

            // Add field declaration with data type
            classContent.append("\tprivate ").append(fieldType).append(" ").append(fieldName).append(";\n\n");
        }

        // Add toString method using Gson
        classContent.append("\t@Override\n");
        classContent.append("\tpublic String toString() {\n");
        classContent.append("\t\treturn new Gson().toJson(this);\n");
        classContent.append("\t}\n\n");

        classContent.append("}");



        System.out.println("className in generateClassSyntax() :" + className);
        System.out.println("classContent in generateClassSyntax() :" + classContent);

        writeToFile(className, String.valueOf(classContent));

        return classContent.toString();
//


    }


    private static void writeToFile(String className, String content) throws IOException {
        System.out.println("writeToFile() called");
        System.out.println("className in writeToFile() : "+className);
        System.out.println("content in writeToFile()   : "+"\n"+content);


        String packagePath = "src/main/java/com/mft/classgenerator/withString/entity/";
        String fileName = packagePath + className + ".java";

        System.out.println("File name in writeToFile() : " +fileName);

        File file = new File(fileName);
        file.getParentFile().mkdirs();
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
//            fileWriter.close();

            System.out.println("File created successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
