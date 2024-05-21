package com.mft.classgenerator.withString;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("All")
public class Optimized {
        private static final Map<String, String> primitiveToWrapperMap = new HashMap<>();

        static {

            // Primitive data types to their wrapper classes
            primitiveToWrapperMap.put("byte", "Byte");
            primitiveToWrapperMap.put("short", "Short");
            primitiveToWrapperMap.put("integer", "Integer");
            primitiveToWrapperMap.put("int", "int");
            primitiveToWrapperMap.put("long", "Long");
            primitiveToWrapperMap.put("float", "Float");
            primitiveToWrapperMap.put("double", "Double");
            primitiveToWrapperMap.put("char", "Character");
            primitiveToWrapperMap.put("boolean", "Boolean");
            primitiveToWrapperMap.put("string", "String");
        }

        public static void main(String[] args) {
            try (Scanner scanner = new Scanner(System.in)) {

                // Class name
                System.out.print("Enter the class name: ");
                String className = capitalizeFirstLetter(scanner.nextLine());

                // Package path
                String packagePath = "src/main/java/com/mft/classgenerator/withString/entity/";

                // File name
                String fileName = packagePath + className + ".java";


                List<String> fields = getFieldsFromUser(scanner);

                // Content to write to file
                String fileContent = generateClassSyntax(className, fields);


                writeToFile(fileName, fileContent);

                System.out.println(".java file created successfully: " + fileName);
            } catch (IOException e) {
                System.out.println("An error occurred while creating the file: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }

        private static String capitalizeFirstLetter(String str) {
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        }

        private static List<String> getFieldsFromUser(Scanner scanner) {
            List<String> fields = new ArrayList<>();
            while (true) {
                System.out.print("Enter field name (type 'done' to finish): ");
                String fieldName = scanner.nextLine().trim();
                if ("done".equalsIgnoreCase(fieldName)) {
                    break;
                }

                System.out.print("Enter field data type: ");

                String fieldType = scanner.nextLine();

                fieldType = primitiveToWrapperMap.getOrDefault(fieldType, fieldType);

                fields.add(fieldType + " " + fieldName);
            }
            return fields;
        }

        private static String generateClassSyntax(String className, List<String> fields) {
            StringBuilder classContent = new StringBuilder();
           // Add package statement
            classContent.append("package com.mft.classgenerator.withString.entity;\n\n");

            // Add required imports
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

            // Add annotations to the class
            classContent.append("@Entity(name = \"").append(className.toLowerCase()).append("Entity\")\n");
            classContent.append("@Table(name = \"").append(className.toLowerCase()).append("_tbl\")\n");

            classContent.append("@Getter\n");
            classContent.append("@Setter\n");
            classContent.append("@NoArgsConstructor\n");
            classContent.append("@AllArgsConstructor\n");
            classContent.append("@Builder\n");

            // Add class declaration
            classContent.append("public class ").append(className).append(" {\n\n");

            // Add @Id annotation above default id field
            classContent.append("\t@Id\n");
            classContent.append("\tprivate Long id;\n\n");

            // Add private fields
            for (String field : fields) {
                String[] parts = field.split(" ");
                String fieldName = parts[1];
                classContent.append("\t@Column(name = \"").append(fieldName).append("\")\n");
                classContent.append("\tprivate ").append(parts[0]).append(" ").append(fieldName).append(";\n");
            }
            classContent.append("\n");

            // Add toString method using Gson
            classContent.append("\t@Override\n");
            classContent.append("\tpublic String toString() {\n");
            classContent.append("\t\treturn new Gson().toJson(this);\n");
            classContent.append("\t}\n\n");

            classContent.append("}");

            return classContent.toString();
        }

        private static void writeToFile(String fileName, String fileContent) throws IOException {
//            System.out.println(fileContent);
            System.out.println(fileName);

            File file = new File(fileName);
            file.getParentFile().mkdirs(); // Create parent directories if they don't exist
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(fileContent);
            }
        }
    }
