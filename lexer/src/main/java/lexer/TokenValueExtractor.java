package lexer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * el constructor de los tokens.
 */
public class TokenValueExtractor {

    public static List<String> extractTokenValues(Iterator<String> lineIterator) {
        List<String> tokens = new ArrayList<>();

        while (lineIterator.hasNext()) {
            String line = lineIterator.next();
            String regexPattern =
                    "'[^']*'|\"[^\"]*\"|\\d+(\\.\\d+)?|[\\w]+|[;\\-+*/=:(){},.@!#$%&¡?¿'^`]";
            Pattern regex = Pattern.compile(regexPattern);
            Matcher matcher = regex.matcher(line);

            while (matcher.find()) {
                tokens.add(matcher.group());
            }
        }

        return polishValues(tokens);
    }

    private static List<String> polishValues(List<String> tokens) {
        List<String> polishedTokens = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if ("-".equals(token) && i > 0) {
                String previousToken = tokens.get(i - 1);
                if ("+".equals(previousToken) || "-".equals(previousToken) ||
                        "*".equals(previousToken) || "/".equals(previousToken) ||
                        "=".equals(previousToken) || "(".equals(previousToken)) {
                    if (i + 1 < tokens.size()) {
                        token = "-" + tokens.get(i + 1);
                        i++;
                    }
                }
            }
            polishedTokens.add(token);
        }
        return polishedTokens;
    }
}

