package edu.byu.cs.tweeter.model.domain.status_members;

import java.util.ArrayList;

import edu.byu.cs.tweeter.model.utils.StringParser;

public class UrlMentions {
    private String[] urls;
    private static final String[] urlEndingIdentities = { ".com", ".net", ".gov" };

    public UrlMentions(String message) {
        this.urls = findUrls(message);
    }

    public String[] getUrls() {
        return urls;
    }

    private String[] findUrls(String message) {
        String[] indivStrings = StringParser.parseBySpaces(message);
        return urlIdentifier(indivStrings);
    }

    private String[] urlIdentifier(String[] indivStrings) {
        ArrayList<String> urlsFound = new ArrayList<>();
        for (int i = 0; i < urlEndingIdentities.length; i++) {
            for (int j = 0; j < indivStrings.length; j++) {
                String tempStr = indivStrings[j];
                if (tempStr.endsWith(urlEndingIdentities[i]) || tempStr.contains(urlEndingIdentities[i] + "/")) {
                    urlsFound.add(tempStr);
                }
            }
        }
        return (String[]) urlsFound.toArray();
    }
}
