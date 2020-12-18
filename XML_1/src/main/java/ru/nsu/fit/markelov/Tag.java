package ru.nsu.fit.markelov;

import java.util.HashSet;
import java.util.Set;

class Tag {

    private final String name;
    private final String parentName;
    private final Set<String> attrNames;

    public Tag(String name, String parentName) {
        this.name = name;
        this.parentName = parentName;
        attrNames = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Tag{" +
            "name='" + name + '\'' +
            ", parentName='" + parentName + '\'' +
            ", attrNames=" + attrNames +
            '}';
    }

    /**
     * Returns name.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns parentName.
     *
     * @return parentName.
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * Returns attrNames.
     *
     * @return attrNames.
     */
    public Set<String> getAttrNames() {
        return attrNames;
    }
}
