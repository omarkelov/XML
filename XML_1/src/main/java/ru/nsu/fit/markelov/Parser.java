package ru.nsu.fit.markelov;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Parser {

    private final List<Person> personRawList = new ArrayList<>();
    private final Map<String, Person> idMap = new HashMap<>();
    private final Map<String, Person> fullNameMap = new HashMap<>();
    private final Map<String, String> collisionIdToFullNameMap = new HashMap<>();
    private final Map<String, Set<String>> collisionFullNameToIdsMap = new HashMap<>();

    public Parser() {
        parseFileToPersonList();
        fillMaps();

        if (mapValuesEqual()) {
            fullNameMap.clear();
            for (Person person : idMap.values()) { // store every object in two maps
                fullNameMap.put(person.getFullName(), person);
            }

            System.out.println("Inconsistent persons are" + (areInconsistentPersonsValid() ? " " : " ___NOT___ ") + "valid");

            for (Person person : idMap.values()) {
                provideConsistency(person);
            }

            boolean allPersonsAreValid = true;
            for (Person person : idMap.values()) {
                if (!isValid(person)) {
                    System.out.println(person.getId() + " - INVALID PERSON");
                    allPersonsAreValid = false;
                }
            }

            if (allPersonsAreValid) {
                System.out.println("ALL PERSONS ARE VALID!");
            }
        } else {
            System.out.println("Persons were merged incorrectly!");
        }
    }

    public Collection<Person> getIdMap() {
        return idMap.values();
    }

    private String normalizeSpace(String str) {
        return str.trim().replaceAll("\\s+", " ");
    }

    private void parseFileToPersonList() {
        try (InputStream inputStream = new FileInputStream("src\\main\\resources\\ru\\nsu\\fit\\markelov\\people.xml")) {
            XMLInputFactory streamFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = streamFactory.createXMLStreamReader(inputStream);

            Person currentPerson = null;
            for (; reader.hasNext(); reader.next()) {
                int eventType = reader.getEventType();
                if (eventType == XMLStreamConstants.START_ELEMENT) {
                    switch (reader.getLocalName()) {
                        case "person":
                            currentPerson = new Person();
                            for (int j = 0; j < reader.getAttributeCount(); j++) {
                                String attrValue = normalizeSpace(reader.getAttributeValue(j));
                                if (reader.getAttributeLocalName(j).equals("id")) {
                                    currentPerson.setId(attrValue);
                                } else { // .equals("name")
                                    String[] fullNameParts = attrValue.split(" ");
                                    currentPerson.setName(fullNameParts[0]);
                                    currentPerson.setSurname(fullNameParts[1]);
                                }
                            }
                            break;
                        case "id":
                            String idAttr = reader.getAttributeValue(0).trim();
                            currentPerson.setId(idAttr);
                            break;
                        case "firstname":
                            if (reader.getAttributeCount() == 0) {
                                reader.next();
                                String firstNameText = reader.getText().trim();
                                currentPerson.setName(firstNameText);
                            } else {
                                String firstNameAttr = reader.getAttributeValue(0).trim();
                                currentPerson.setName(firstNameAttr);
                            }
                            break;
                        case "surname":
                            String surnameAttr = reader.getAttributeValue(0).trim();
                            currentPerson.setSurname(surnameAttr);
                            break;
                        case "family-name":
                            reader.next();
                            String familyNameText = reader.getText().trim();
                            currentPerson.setSurname(familyNameText);
                            break;
                        case "first":
                            reader.next();
                            String firstText = reader.getText().trim();
                            currentPerson.setName(firstText);
                            break;
                        case "family":
                            reader.next();
                            String familyText = reader.getText().trim();
                            currentPerson.setSurname(familyText);
                            break;
                        case "gender":
                            String gender;
                            if (reader.getAttributeCount() == 0) {
                                reader.next();
                                gender = reader.getText().trim();
                            } else {
                                gender = reader.getAttributeValue(0).trim();
                            }
                            currentPerson.setGender(gender.toLowerCase(Locale.ROOT).contains("f") ? "Female" : "Male");
                            break;
                        case "spouce":
                            String spouseFullNameText = "NONE";
                            if (reader.getAttributeCount() == 0) {
                                if (reader.hasText()) {
                                    spouseFullNameText = normalizeSpace(reader.getText());
                                }
                            } else {
                                spouseFullNameText = normalizeSpace(reader.getAttributeValue(0));
                            }
                            if (!spouseFullNameText.equals("NONE")) {
                                currentPerson.setSpouseFullName(spouseFullNameText);
                            }
                            break;
                        case "husband":
                            String husbandIdAttr = reader.getAttributeValue(0).trim();
                            currentPerson.setHusbandId(husbandIdAttr);
                            break;
                        case "wife":
                            String wifeIdAttr = reader.getAttributeValue(0).trim();
                            currentPerson.setWifeId(wifeIdAttr);
                            break;
                        case "parent":
                            String parentId = "UNKNOWN";
                            if (reader.getAttributeCount() != 0) {
                                parentId = reader.getAttributeValue(0).trim();
                            }
                            if (!parentId.equals("UNKNOWN")) {
                                currentPerson.getParentIdSet().add(parentId);
                            }
                            break;
                        case "father":
                            reader.next();
                            String fatherFullNameText = normalizeSpace(reader.getText());
                            currentPerson.setFatherFullName(fatherFullNameText);
                            break;
                        case "mother":
                            reader.next();
                            String motherFullNameText = normalizeSpace(reader.getText());
                            currentPerson.setMotherFullName(motherFullNameText);
                            break;
                        case "child":
                            reader.next();
                            String childFullNameText = normalizeSpace(reader.getText());
                            currentPerson.getChildFullNameSet().add(childFullNameText);
                            break;
                        case "son":
                            String sonIdAttr = reader.getAttributeValue(0).trim();
                            currentPerson.getSonIdSet().add(sonIdAttr);
                            break;
                        case "daughter":
                            String daughterIdAttr = reader.getAttributeValue(0).trim();
                            currentPerson.getDaughterIdSet().add(daughterIdAttr);
                            break;
                        case "siblings":
                            if (reader.getAttributeCount() != 0) {
                                String siblingIdsAttr = normalizeSpace(reader.getAttributeValue(0));
                                String[] siblingIds = siblingIdsAttr.split(" ");
                                currentPerson.getSiblingIdSet().addAll(new ArrayList<>(Arrays.asList(siblingIds)));
                            }
                            break;
                        case "brother":
                            reader.next();
                            String brotherFullNameText = normalizeSpace(reader.getText());
                            currentPerson.getBrotherFullNameSet().add(brotherFullNameText);
                            break;
                        case "sister":
                            reader.next();
                            String sisterFullNameText = normalizeSpace(reader.getText());
                            currentPerson.getSisterFullNameSet().add(sisterFullNameText);
                            break;
                        case "siblings-number":
                            String siblingsNumberText = reader.getAttributeValue(0).trim();
                            try {
                                currentPerson.setSiblingsNumber(Integer.parseInt(siblingsNumberText));
                            } catch (NumberFormatException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case "children-number":
                            String childrenNumberText = reader.getAttributeValue(0).trim();
                            try {
                                currentPerson.setChildrenNumber(Integer.parseInt(childrenNumberText));
                            } catch (NumberFormatException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                    }
                } else if (eventType == XMLStreamConstants.END_ELEMENT) {
                    switch (reader.getLocalName()) {
                        case "person":
                            personRawList.add(currentPerson);
                            currentPerson = null;
                            break;
                    }
                }
            }

            reader.close();

            System.out.println(personRawList.size() + " - raw person list size");
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void fillMaps() {
        for (Person person : personRawList) {
            if (person.getId() != null) {
                if (!idMap.containsKey(person.getId())) {
                    idMap.put(person.getId(), person);
                } else {
                    idMap.get(person.getId()).merge(person);
                }
            }

            if (person.isFullNameSet()) {
                if (!fullNameMap.containsKey(person.getFullName())) {
                    fullNameMap.put(person.getFullName(), person);
                } else {
                    fullNameMap.get(person.getFullName()).merge(person);

                    Person p = fullNameMap.get(person.getFullName());
                    if (person.getId() != null && p.getId() != null && !p.getId().equals(person.getId())) {
                        collisionIdToFullNameMap.put(p.getId(), p.getFullName());
                        collisionIdToFullNameMap.put(person.getId(), person.getFullName());
                    }
                }
            }
        }

        for (Person person : idMap.values()) {
            if (person.isFullNameSet()) {
                if (!fullNameMap.containsKey(person.getFullName())) {
                    fullNameMap.put(person.getFullName(), person);
                } else {
                    fullNameMap.get(person.getFullName()).merge(person);

                    Person p = fullNameMap.get(person.getFullName());
                    if (person.getId() != null && p.getId() != null && !p.getId().equals(person.getId())) {
                        collisionIdToFullNameMap.put(p.getId(), p.getFullName());
                        collisionIdToFullNameMap.put(person.getId(), person.getFullName());
                    }
                }
            }
        }

        System.out.println(idMap.size() + " - idMap size");
        System.out.println(fullNameMap.size() + " - fullNameMap size");
        System.out.println(collisionIdToFullNameMap.size() + " - collisionsMap size");

        for (Map.Entry<String, String> entry : collisionIdToFullNameMap.entrySet()) {
            idMap.remove(entry.getKey());
            fullNameMap.remove(entry.getValue());
        }

        System.out.println(idMap.size() + " - idMap size (without collisions)");
        System.out.println(fullNameMap.size() + " - fullNameMap size (without collisions)");

        for (Map.Entry<String, String> entry : collisionIdToFullNameMap.entrySet()) {
            String id = entry.getKey();
            String fullName = entry.getValue();

            Set<String> ids = collisionFullNameToIdsMap.get(fullName);
            if (ids == null) {
                collisionFullNameToIdsMap.put(fullName, new TreeSet<String>() {{add(id);}});
            } else {
                ids.add(id);
            }
        }
    }

    private boolean mapValuesEqual() {
        for (Person person : idMap.values()) {
            if (!person.equals(fullNameMap.get(person.getFullName()))) {
                return false;
            }
        }

        return true;
    }

    private void provideConsistency(Person person) {
        if (person.getGender().equals("Male")) {
            if (person.getWifeId() != null && person.getSpouseFullName() == null) {
                Person wife = idMap.get(person.getWifeId());
                if (wife != null) {
                    person.setSpouseFullName(wife.getFullName());
                } else {
                    if (collisionIdToFullNameMap.containsKey(person.getWifeId())) {
                        person.setWifeId(null);
                    } else {
                        System.out.println(person.getWifeId() + " - valid WifeId is ___NOT___ found");
                    }
                }
            }

            if (person.getSpouseFullName() != null && person.getWifeId() == null) {
                Person wife = fullNameMap.get(person.getSpouseFullName());
                if (wife != null) {
                    person.setWifeId(wife.getId());
                } else {
                    if (collisionFullNameToIdsMap.containsKey(person.getSpouseFullName())) {
                        person.setSpouseFullName(null);
                    } else {
                        System.out.println(person.getSpouseFullName() + " - valid SpouseFullName is ___NOT___ found");
                    }
                }
            }
        }

        if (person.getGender().equals("Female")) {
            if (person.getHusbandId() != null && person.getSpouseFullName() == null) {
                Person husband = idMap.get(person.getHusbandId());
                if (husband != null) {
                    person.setSpouseFullName(husband.getFullName());
                } else {
                    if (collisionIdToFullNameMap.containsKey(person.getHusbandId())) {
                        person.setHusbandId(null);
                    } else {
                        System.out.println(person.getHusbandId() + " - valid HusbandId is ___NOT___ found");
                    }
                }
            }

            if (person.getSpouseFullName() != null && person.getHusbandId() == null) {
                Person husband = fullNameMap.get(person.getSpouseFullName());
                if (husband != null) {
                    person.setHusbandId(husband.getId());
                } else {
                    if (collisionFullNameToIdsMap.containsKey(person.getSpouseFullName())) {
                        person.setSpouseFullName(null);
                    } else {
                        System.out.println(person.getSpouseFullName() + " - valid SpouseFullName is ___NOT___ found");
                    }
                }
            }
        }

        if (person.getFatherFullName() != null) {
            Person father = fullNameMap.get(person.getFatherFullName());
            if (father != null) {
                person.setFatherId(father.getId());
            } else {
                if (collisionFullNameToIdsMap.containsKey(person.getFatherFullName())) {
                    person.setFatherFullName(null);
                } else {
                    System.out.println(person.getFatherFullName() + " - valid FatherFullName is ___NOT___ found");
                }
            }
        }

        if (person.getMotherFullName() != null) {
            Person mother = fullNameMap.get(person.getMotherFullName());
            if (mother != null) {
                person.setMotherId(mother.getId());
            } else {
                if (collisionFullNameToIdsMap.containsKey(person.getMotherFullName())) {
                    person.setMotherFullName(null);
                } else {
                    System.out.println(person.getMotherFullName() + " - valid MotherFullName is ___NOT___ found");
                }
            }
        }

        Iterator<String> parentIdIterator = person.getParentIdSet().iterator();
        while (parentIdIterator.hasNext()) {
            String parentId = parentIdIterator.next();
            Person parent = idMap.get(parentId);
            if (parent != null) {
                if (parent.getGender().equals("Male")) {
                    person.setFatherId(parent.getId());
                } else {
                    person.setMotherId(parent.getId());
                }
            } else {
                if (collisionIdToFullNameMap.containsKey(parentId)) {
                    parentIdIterator.remove();
                } else {
                    System.out.println(parentId + " - valid parentId is ___NOT___ found");
                }
            }
        }

        Iterator<String> sonIdIterator = person.getSonIdSet().iterator();
        while (sonIdIterator.hasNext()) {
            String sonId = sonIdIterator.next();
            if (!idMap.containsKey(sonId)) {
                if (collisionIdToFullNameMap.containsKey(sonId)) {
                    sonIdIterator.remove();
                    person.setChildRemoved(true);
                } else {
                    System.out.println(sonId + " - valid sonId is ___NOT___ found");
                }
            }
        }

        Iterator<String> daughterIdIterator = person.getDaughterIdSet().iterator();
        while (daughterIdIterator.hasNext()) {
            String daughterId = daughterIdIterator.next();
            if (!idMap.containsKey(daughterId)) {
                if (collisionIdToFullNameMap.containsKey(daughterId)) {
                    daughterIdIterator.remove();
                    person.setChildRemoved(true);
                } else {
                    System.out.println(daughterId + " - valid daughterId is ___NOT___ found");
                }
            }
        }

        Iterator<String> childFullNameIterator = person.getChildFullNameSet().iterator();
        while (childFullNameIterator.hasNext()) {
            String childFullName = childFullNameIterator.next();
            Person child = fullNameMap.get(childFullName);
            if (child != null) {
                if (child.getGender().equals("Male")) {
                    person.getSonIdSet().add(child.getId());
                } else {
                    person.getDaughterIdSet().add(child.getId());
                }
            } else {
                if (collisionFullNameToIdsMap.containsKey(childFullName)) {
                    childFullNameIterator.remove();
                    person.setChildRemoved(true);
                } else {
                    System.out.println(childFullName + " - valid childFullName is ___NOT___ found");
                }
            }
        }

        Iterator<String> siblingIdIterator = person.getSiblingIdSet().iterator();
        while (siblingIdIterator.hasNext()) {
            String siblingId = siblingIdIterator.next();
            Person sibling = idMap.get(siblingId);
            if (sibling != null) {
                if (sibling.getGender().equals("Male")) {
                    person.getBrotherIdSet().add(siblingId);
                } else {
                    person.getSisterIdSet().add(siblingId);
                }
            } else {
                if (collisionIdToFullNameMap.containsKey(siblingId)) {
                    siblingIdIterator.remove();
                    person.setSiblingRemoved(true);
                } else {
                    System.out.println(siblingId + " - valid siblingId is ___NOT___ found");
                }
            }
        }

        Iterator<String> brotherFullNameIterator = person.getBrotherFullNameSet().iterator();
        while (brotherFullNameIterator.hasNext()) {
            String brotherFullName = brotherFullNameIterator.next();
            Person brother = fullNameMap.get(brotherFullName);
            if (brother != null) {
                person.getBrotherIdSet().add(brother.getId());
            } else {
                if (collisionFullNameToIdsMap.containsKey(brotherFullName)) {
                    brotherFullNameIterator.remove();
                    person.setSiblingRemoved(true);
                } else {
                    System.out.println(brotherFullName + " - valid brotherFullName is ___NOT___ found");
                }
            }
        }

        Iterator<String> sisterFullNameIterator = person.getSisterFullNameSet().iterator();
        while (sisterFullNameIterator.hasNext()) {
            String sisterFullName = sisterFullNameIterator.next();
            Person sister = fullNameMap.get(sisterFullName);
            if (sister != null) {
                person.getSisterIdSet().add(sister.getId());
            } else {
                if (collisionFullNameToIdsMap.containsKey(sisterFullName)) {
                    sisterFullNameIterator.remove();
                    person.setSiblingRemoved(true);
                } else {
                    System.out.println(sisterFullName + " - valid sisterFullName is ___NOT___ found");
                }
            }
        }
    }

    private boolean areInconsistentPersonsValid() {
        for (Person person : idMap.values()) {
            if (person.getId() == null
                || person.getName() == null
                || person.getSurname() == null
                || person.getGender() == null
                || person.getParentIdSet().size() > 2
                || person.getSiblingsNumber() == null
                || person.getChildrenNumber() == null
            ) {
                System.out.println(person.getId() + " - invalid");
                return false;
            }
        }

        return true;
    }

    private boolean isValid(Person person) {
        if (person.isChildRemoved()) return true;
        if (person.isSiblingRemoved()) return true;

        if (person.getBrotherIdSet().size() + person.getSisterIdSet().size() != person.getSiblingsNumber()) return false;
        if (person.getSonIdSet().size() + person.getDaughterIdSet().size() != person.getChildrenNumber()) return false;

        return true;
    }

    // ---------------------------
    // ---------- Debug ----------
    // ---------------------------

    private void printCollisionIdToFullNameMap() {
        System.out.println("-----");
        for (Map.Entry<String, String> entry : collisionIdToFullNameMap.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
        System.out.println("-----");
    }

    private void printCollisionFullNameToIdsMap() {
        System.out.println("-----");
        for (Map.Entry<String, Set<String>> entry : collisionFullNameToIdsMap.entrySet()) {
            System.out.println(entry.getKey());
            for (String id : entry.getValue()) {
                System.out.println(id);
            }
        }
        System.out.println("-----");
    }

    // ------------------------------
    // ---------- Research ----------
    // ------------------------------

    private void printTagNames() {
        Map<String, Set<String>> tags = new TreeMap<>();

        try (InputStream inputStream = new FileInputStream("C:\\people.xml")) {
            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(inputStream);

            for (; reader.hasNext(); reader.next()) {
                int eventType = reader.getEventType();
                if (eventType == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();

                    if (!tags.containsKey(tagName)) {
                        tags.put(tagName, new TreeSet<>());
                    }

                    for (int j = 0; j < reader.getAttributeCount(); j++) {
                        String attr = reader.getAttributeLocalName(j);
                        tags.get(tagName).add(attr);
                    }
                }
            }

            reader.close();
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }

        int i = 0;
        for (Map.Entry<String, Set<String>> tag : tags.entrySet()) {
            System.out.print(++i + ": " + tag.getKey() + " ");
            System.out.println(tag.getValue());
        }
    }

    private void printAllTags() {
        List<String> currentTagNames = new ArrayList<>();
        int level = 0;

        Map<String, Tag> tags = new HashMap<>();

        try (InputStream inputStream = new FileInputStream("C:\\people.xml")) {
            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(inputStream);

            int i = 0;
            for (; /*i < 3726228 / 2 & */reader.hasNext(); reader.next()) {
                int eventType = reader.getEventType();
                if (eventType == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    currentTagNames.add(level, tagName);
                    String parentTagName = level == 0 ? "" : currentTagNames.get(level - 1);

                    Tag tag = tags.get(tagName);
                    if (tag == null) {
                        tag = new Tag(tagName, parentTagName);
                        tags.put(tagName, tag);
                    }

                    for (int j = 0; j < reader.getAttributeCount(); j++) {
                        tag.getAttrNames().add(reader.getAttributeLocalName(j));
                    }

                    level++;
                } else if (eventType == XMLStreamConstants.END_ELEMENT) {
                    level--;
                }

                i++;
            }
            System.out.println(i);

            reader.close();
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }

        for (String tagName : currentTagNames) {
            System.out.println(tagName);
        }

        int i = 0;
        for (Map.Entry<String, Tag> tag : tags.entrySet()) {
            System.out.print(++i + ": " + tag.getKey() + " | ");
            System.out.println(tag.getValue());
        }
    }

    private void printGenderValues() {
        try (InputStream inputStream = new FileInputStream("C:\\people.xml")) {
            XMLInputFactory streamFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = streamFactory.createXMLStreamReader(inputStream);

            Set<String> set = new HashSet<>();
            for (; reader.hasNext(); reader.next()) {
                int eventType = reader.getEventType();
                if (eventType == XMLStreamConstants.START_ELEMENT) {
                    switch (reader.getLocalName()) {
                        case "gender":
                            if (reader.getAttributeCount() == 0) {
                                reader.next();
                                set.add(reader.getText().trim());
                            } else {
                                set.add(reader.getAttributeValue(0).trim());
                            }
                            break;
                    }
                }
            }

            for (String val : set) {
                System.out.println(val);
            }

            reader.close();
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
