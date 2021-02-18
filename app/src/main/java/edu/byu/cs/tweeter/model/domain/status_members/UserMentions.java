package edu.byu.cs.tweeter.model.domain.status_members;

import java.util.ArrayList;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.StringParser;

public class UserMentions {
    private final String mentionIndicator = "@";
    private String[] mentionStrings;
    private Map<String, User> supposedMentions;

    public UserMentions(String message) {
        this.mentionStrings = findMentions(message);
    }

    public Map<String, User> getUserMentions() {
        return supposedMentions;
    }

    private String[] findMentions(String message) {
        String[] indivStrings = StringParser.parseBySpaces(message);
        return mentionIdentifier(indivStrings);
    }

    private String[] mentionIdentifier(String[] indivStrings) {
        ArrayList<String> mentionsFound = new ArrayList<>();
        for (int j = 0; j < indivStrings.length; j++) {
            String tempStr = indivStrings[j];
            if (tempStr.startsWith(mentionIndicator)) {
                mentionsFound.add(tempStr);
            }
        }

        return (String[]) mentionsFound.toArray();
    }
}
