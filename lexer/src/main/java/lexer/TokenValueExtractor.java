package lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenValueExtractor {

    public static List<String> extractTokenValues(String input) {
        if (input.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> adjustedInput = List.of(input.split("\n"));

        String regexPattern = "'[^']*'|\"[^\"]*\"|-?\\d+(\\.\\d+)?|\\w+|[;\\-+*/=:(){},.@!#$%&¡?¿'^`]";

        Pattern regex = Pattern.compile(regexPattern);

        List<String> values = new ArrayList<>();

        createValues(adjustedInput, regex, values);

        return values;
    }

    private static void createValues(List<String> adjustedInput, Pattern regex, List<String> values) {
        for (String i : adjustedInput) {
            Matcher matcher = regex.matcher(i);
            while (matcher.find()) {
                values.add(matcher.group());
            }
        }
    }
}
