//package com.company;
//
//import java.io.Serializable;
//
//public class Class  implements Serializable {
//    int id;
//    String cls ;
//    public String toString(){
//        return "Class\n" + cls ;
//    }
//    public int getId() {
//        return id;
//    }
//
//    public String getCls() {
//        return cls;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public void setCls(String cls) {
//        this.cls = cls;
//    }
//
//}
package com.company;

import java.io.Serializable;

public class Class implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String cls;

    public String toString() {
        return "Class: " + cls;
    }

    public String toDetailedString() {
        return "\n───────────────────────────────────────\n" +
                "Class          : " + cls + "\n" +
                "───────────────────────────────────────";
    }

    // Getters
    public int getId() { return id; }
    public String getCls() { return cls; }

    // Setters with validation
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        this.id = id;
    }

    public void setCls(String cls) {
        if (cls == null || cls.trim().isEmpty()) {
            throw new IllegalArgumentException("Class name cannot be null or empty");
        }
        this.cls = cls.trim().toUpperCase(); // Standardize to uppercase
    }
}
