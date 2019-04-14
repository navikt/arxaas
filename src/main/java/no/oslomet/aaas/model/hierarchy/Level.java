package no.oslomet.aaas.model.hierarchy;

import java.util.List;

public class Level {

    private final int level;
    private final List<Group> groups;

    public int getLevel() {
        return level;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public Level(int level, List<Group> groups) {
        this.level = level;
        this.groups = groups;
    }

    public static class Group {
        private final int grouping;
        private final String label;

        public Group(int grouping, String label) {
            this.grouping = grouping;
            this.label = label;
        }

        public Group(int grouping) {
            this.grouping = grouping;
            this.label = null;
        }

        public int getGrouping() {
            return grouping;
        }

        public String getLabel() {
            return label;
        }
    }
}

