package token;

import java.util.Map;
import java.util.regex.Pattern;

public class TokenCreator {

    public Token createToken(String s, int row, int index) {
        String type = getTypeFromValue(s);
        Position position = new Position(row, index);

        return new Token(type, s, position);
    }

    private String getTypeFromValue(String s) {
        Map<String, String> typesMap = TypesMapGenerator.getTypesMap();
        String type = null;

        for (Map.Entry<String, String> entry : typesMap.entrySet()) {

            Pattern patternInMap = Pattern.compile(entry.getKey());
            boolean hasType = patternInMap.matcher(s).matches();

            if (hasType) {
                type = entry.getValue();
                break;
            }
        }

        if (type == null) {

            boolean isValidVariableName = TypesMapGenerator.isValidVariableName(s);

            if (isValidVariableName) {
                return "IDENTIFIER";
            }else{
                return "UNKNOWN";
            }
        }

        return type;
    }
}
