package token;

import java.util.Map;
import java.util.regex.Pattern;

public class TokenCreator {
    String fileName;
    Map<String, String> typesMap;

    public TokenCreator(String fileName){
        this.fileName = fileName;
        this.typesMap = TypesMapGenerator.getTypesMap(fileName);
    }

    public Token createToken(String s) {
        String type = getTypeFromValue(s);

        return new Token(type, s);
    }

    private String getTypeFromValue(String s) {
        String type = findType(s);

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

    private String findType(String s) {
        String type = null;
        for (Map.Entry<String, String> entry : typesMap.entrySet()) {
            type = matchType(s, entry);
            if(type != null){
                break;
            }
        }
        return type;
    }

    private String matchType(String s, Map.Entry<String, String> entry) {
        Pattern patternInMap = Pattern.compile(entry.getKey());
        boolean hasType = patternInMap.matcher(s).matches();

        if (hasType) {
            return entry.getValue();
        }else{
            return null;
        }
    }
}
