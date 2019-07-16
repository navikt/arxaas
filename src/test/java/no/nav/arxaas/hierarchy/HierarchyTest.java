package no.nav.arxaas.hierarchy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HierarchyTest {

    private String[][] rawHierarchy;

    @BeforeEach
    void setUp() {
        rawHierarchy = new String[][]{{"18", "adult", "young", "somthing"},
                {"23", "adult", "young", "somthing"},
                {"2", "child", "young", "somthing"},
                {"24", "adult", "young", "somthing"}};

    }

    @Test
    void getHierarchy() {
        var hierarchy = new Hierarchy(rawHierarchy);
        Assertions.assertEquals(rawHierarchy, hierarchy.getHierarchy());
    }

    @Test
    void equals1() {
        var hierarchy = new Hierarchy(rawHierarchy);
        var sameHierarchy = new Hierarchy(rawHierarchy);
        var notSameHierarchy = new Hierarchy(new String[][]{{"18", "adult", "young", "somthing"}});
        Assertions.assertEquals(hierarchy, hierarchy);
        Assertions.assertEquals(sameHierarchy, hierarchy);
        Assertions.assertNotEquals(notSameHierarchy, hierarchy);
    }

    @Test
    void hashCode1() {
        var hierarchy = new Hierarchy(rawHierarchy);
        var sameHierarchy = new Hierarchy(rawHierarchy);
        var notSameHierarchy = new Hierarchy(new String[][]{{"18", "adult", "young", "somthing"}});
        Assertions.assertThrows(IllegalArgumentException.class, () -> Set.of(hierarchy, sameHierarchy).size());
        Assertions.assertNotEquals(notSameHierarchy.hashCode(), hierarchy.hashCode());
    }
}