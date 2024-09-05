package lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenValueExtractor {

    public static List<List<String>> extractTokenValues(String input) {
        if (input.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> adjustedInput = List.of(input.split("\n"));

        String regexPattern = "'[^']*'|\"[^\"]*\"|-?\\d+(\\.\\d+)?|\\w+|[;\\-+*/=:(){},.@!#$%&¡?¿'^`]";

        Pattern regex = Pattern.compile(regexPattern);

        List<List<String>> matches = new ArrayList<>();

        for (String i : adjustedInput) {
            Matcher matcher = regex.matcher(i);
            List<String> matchList = new ArrayList<>();
            while (matcher.find()) {
                matchList.add(matcher.group());
            }
            matches.add(matchList);
        }

        return matches;
    }
}
