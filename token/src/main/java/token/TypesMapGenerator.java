package token;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TypesMapGenerator {

    public static Map<String,String> getTypesMap(String fileName) {
        Map<String, String> map = new HashMap<>();

        InputStream inputStream = TypesMapGenerator.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("Resource not found: " + fileName);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("::");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    List<String> values = Stream.of(parts[1].trim().split(","))
                            .map(String::trim)
                            .collect(Collectors.toList());

                    String pattern = createPattern(values);

                    if (!key.isEmpty() && !pattern.isEmpty()) {
                        map.put(pattern, key);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    private static String createPattern(List<String> symbols) {
        if (symbols.isEmpty()) {
            return "";
        }
        return "^(" + String.join("|", symbols) + ")$";
    }

    public static boolean isValidVariableName(String s) {
        String variableNamePattern = "^[a-zA-Z_][a-zA-Z0-9_]*$";
        Pattern pattern = Pattern.compile(variableNamePattern);
        return pattern.matcher(s).matches();
    }
}
