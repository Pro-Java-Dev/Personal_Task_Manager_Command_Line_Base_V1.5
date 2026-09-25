package PERSONAL_TASK_MANAGER.model;

import java.util.Objects;
import java.lang.Object;

public class Category {
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override 
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override 
    public int hashCode() {
        return Objects.hash(name);
    }
}