package com.mft.classgenerator.withString;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@Named
//@ViewScoped
@SessionScoped
@SuppressWarnings("All")
public class CreateFileBeanSecond implements Serializable {

    private String className;
    private Map<String, String> fields;
    private String fieldName;
    private String fieldType;


    public void addField() {
       fields.put(fieldName , fieldType);
        System.out.println("field name and type added to map");
//        fieldName = null;
//        fieldType = null;
//        System.out.println("flushed");
    }



//    @PostConstruct
//    public void init() {
//
//        // TODO: I initialized Map here. thats why even if i push next class btn , and add new fields , privous values are still in the map .
//        // Initialize the fields map
//        fields = new HashMap<>();
//        System.out.println("Map init");
//    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public Map<String, String> getFields() {
        if (fields == null) {
            fields = new HashMap<>();
            System.out.println("Map initialized lazily in getter method");
        }

        return fields;
    }

    public void setFields(Map<String, String> fields) {

        this.fields = fields;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public void createJavaFile() {
        // Capitalize the class name
        String capitalizedClassName = capitalizeFirstLetter(className);
        System.out.println("capitalizedClassName :  " +capitalizedClassName );


        // Call the createJavaFile() with capitalizedClassName and fields
        try {
            System.out.println(" createJavaFile() is being called");

            OptimizedSecond.createJavaFile(capitalizedClassName, fields);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Java file created successfully!"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occurred: " + e.getMessage()));
        }
    }


    public void clearFields() {
        fields.clear(); // Clear the fields

    }

    private String capitalizeFirstLetter(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

}
