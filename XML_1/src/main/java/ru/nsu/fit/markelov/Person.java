package ru.nsu.fit.markelov;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Person {

    private String id;
    private String name;
    private String surname;
    private String gender;

    private String spouseFullName;
    private String husbandId;
    private String wifeId;

    private final Set<String> parentIdSet = new TreeSet<>();
    private String fatherFullName;
    private String motherFullName;

    private final Set<String> childFullNameSet = new TreeSet<>();
    private final Set<String> sonIdSet = new TreeSet<>();
    private final Set<String> daughterIdSet = new TreeSet<>();

    private final Set<String> siblingIdSet = new TreeSet<>();
    private final Set<String> brotherFullNameSet = new TreeSet<>();
    private final Set<String> sisterFullNameSet = new TreeSet<>();

    private Integer childrenNumber;
    private Integer siblingsNumber;

    // Extra Fields -- not being checked for equality

    private String fatherId;
    private String motherId;
    private final Set<String> brotherIdSet = new TreeSet<>();
    private final Set<String> sisterIdSet = new TreeSet<>();
    private boolean childRemoved = false;
    private boolean siblingRemoved = false;

    @Override
    public String toString() {
        return id + ": " + name + " " + surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(surname, person.surname) && Objects.equals(gender, person.gender) && Objects.equals(spouseFullName, person.spouseFullName) && Objects.equals(husbandId, person.husbandId) && Objects.equals(wifeId, person.wifeId) && Objects.equals(parentIdSet, person.parentIdSet) && Objects.equals(fatherFullName, person.fatherFullName) && Objects.equals(motherFullName, person.motherFullName) && Objects.equals(childFullNameSet, person.childFullNameSet) && Objects.equals(sonIdSet, person.sonIdSet) && Objects.equals(daughterIdSet, person.daughterIdSet) && Objects.equals(siblingIdSet, person.siblingIdSet) && Objects.equals(brotherFullNameSet, person.brotherFullNameSet) && Objects.equals(sisterFullNameSet, person.sisterFullNameSet) && Objects.equals(siblingsNumber, person.siblingsNumber) && Objects.equals(childrenNumber, person.childrenNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, gender, spouseFullName, husbandId, wifeId, parentIdSet, fatherFullName, motherFullName, childFullNameSet, sonIdSet, daughterIdSet, siblingIdSet, brotherFullNameSet, sisterFullNameSet, siblingsNumber, childrenNumber);
    }

    public void merge(Person person) {
        if (person == null) return;

        if (id == null) id = person.getId();
        if (person.getId() == null) person.setId(id);

        if (name == null) name = person.getName();
        if (person.getName() == null) person.setName(name);

        if (surname == null) surname = person.getSurname();
        if (person.getSurname() == null) person.setSurname(surname);

        if (gender == null) gender = person.getGender();
        if (person.getGender() == null) person.setGender(gender);

        if (spouseFullName == null) spouseFullName = person.getSpouseFullName();
        if (person.getSpouseFullName() == null) person.setSpouseFullName(spouseFullName);

        if (husbandId == null) husbandId = person.getHusbandId();
        if (person.getHusbandId() == null) person.setHusbandId(husbandId);

        if (wifeId == null) wifeId = person.getWifeId();
        if (person.getWifeId() == null) person.setWifeId(wifeId);

        parentIdSet.addAll(person.getParentIdSet());
        person.getParentIdSet().addAll(parentIdSet);

        if (fatherFullName == null) fatherFullName = person.getFatherFullName();
        if (person.getFatherFullName() == null) person.setFatherFullName(fatherFullName);

        if (motherFullName == null) motherFullName = person.getMotherFullName();
        if (person.getMotherFullName() == null) person.setMotherFullName(motherFullName);

        childFullNameSet.addAll(person.getChildFullNameSet());
        person.getChildFullNameSet().addAll(childFullNameSet);

        sonIdSet.addAll(person.getSonIdSet());
        person.getSonIdSet().addAll(sonIdSet);

        daughterIdSet.addAll(person.getDaughterIdSet());
        person.getDaughterIdSet().addAll(daughterIdSet);

        siblingIdSet.addAll(person.getSiblingIdSet());
        person.getSiblingIdSet().addAll(siblingIdSet);

        brotherFullNameSet.addAll(person.getBrotherFullNameSet());
        person.getBrotherFullNameSet().addAll(brotherFullNameSet);

        sisterFullNameSet.addAll(person.getSisterFullNameSet());
        person.getSisterFullNameSet().addAll(sisterFullNameSet);

        if (siblingsNumber == null) siblingsNumber = person.getSiblingsNumber();
        if (person.getSiblingsNumber() == null) person.setSiblingsNumber(siblingsNumber);

        if (childrenNumber == null) childrenNumber = person.getChildrenNumber();
        if (person.getChildrenNumber() == null) person.setChildrenNumber(childrenNumber);
    }

    public boolean isFullNameSet() {
        return name != null && surname != null;
    }

    public String getFullName() {
        if (name == null || surname == null) {
            return null;
        }

        return name + " " + surname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpouseFullName() {
        return spouseFullName;
    }

    public void setSpouseFullName(String spouseFullName) {
        this.spouseFullName = spouseFullName;
    }

    public String getHusbandId() {
        return husbandId;
    }

    public void setHusbandId(String husbandId) {
        this.husbandId = husbandId;
    }

    public String getWifeId() {
        return wifeId;
    }

    public void setWifeId(String wifeId) {
        this.wifeId = wifeId;
    }

    public Set<String> getParentIdSet() {
        return parentIdSet;
    }

    public String getFatherFullName() {
        return fatherFullName;
    }

    public void setFatherFullName(String fatherFullName) {
        this.fatherFullName = fatherFullName;
    }

    public String getMotherFullName() {
        return motherFullName;
    }

    public void setMotherFullName(String motherFullName) {
        this.motherFullName = motherFullName;
    }

    public Set<String> getChildFullNameSet() {
        return childFullNameSet;
    }

    public Set<String> getSonIdSet() {
        return sonIdSet;
    }

    public Set<String> getDaughterIdSet() {
        return daughterIdSet;
    }

    public Set<String> getSiblingIdSet() {
        return siblingIdSet;
    }

    public Set<String> getBrotherFullNameSet() {
        return brotherFullNameSet;
    }

    public Set<String> getSisterFullNameSet() {
        return sisterFullNameSet;
    }

    public Integer getSiblingsNumber() {
        return siblingsNumber;
    }

    public void setSiblingsNumber(Integer siblingsNumber) {
        this.siblingsNumber = siblingsNumber;
    }

    public Integer getChildrenNumber() {
        return childrenNumber;
    }

    public void setChildrenNumber(Integer childrenNumber) {
        this.childrenNumber = childrenNumber;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getMotherId() {
        return motherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    public Set<String> getBrotherIdSet() {
        return brotherIdSet;
    }

    public Set<String> getSisterIdSet() {
        return sisterIdSet;
    }

    public boolean isSiblingRemoved() {
        return siblingRemoved;
    }

    public void setSiblingRemoved(boolean siblingRemoved) {
        this.siblingRemoved = siblingRemoved;
    }

    public boolean isChildRemoved() {
        return childRemoved;
    }

    public void setChildRemoved(boolean childRemoved) {
        this.childRemoved = childRemoved;
    }
}
