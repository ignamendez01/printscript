package lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenValueExtractor {

    public static List<String> extractTokenValues(String version, String input) {
        if (input.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> adjustedInput = List.of(input.split("\n"));

        String regexPattern = "'[^']*'|\"[^\"]*\"|\\d+(\\.\\d+)?|[\\w]+|[;\\-+*/=:(){},.@!#$%&¡?¿'^`]";

        Pattern regex = Pattern.compile(regexPattern);

        List<String> values = new ArrayList<>();

        createValues(version, adjustedInput, regex, values);

        return values;
    }

    private static void createValues(String version, List<String> adjustedInput, Pattern regex, List<String> values) {
        List<String> fragment = new ArrayList<>();
        for (String i : adjustedInput) {
            Matcher matcher = regex.matcher(i);
            while (matcher.find()) {
                fragment.add(matcher.group());
            }
            checkValues(version, fragment);
            if(fragment.contains("-")){
                polishValues(fragment);
            }
            values.addAll(fragment);
            fragment.clear();
        }
    }

    private static void polishValues(List<String> fragment) {
        for(int i = 0; i < fragment.size(); i++){
            if(fragment.get(i).equals("-")){
                switch (fragment.get(i-1)){
                    case "-","+","/","*","=","(" -> {
                        fragment.set(i, "-"+fragment.get(i+1));
                        fragment.remove(i+1);
                    }

                }
            }
        }
    }

    private static void checkValues(String version, List<String> values) {
        if (Objects.equals(version, "1.0")){
            if(Objects.equals(values.getFirst(), "const") || Objects.equals(values.getFirst(), "if")) {
                throw new RuntimeException("This version doesn't allow this keyword");
            }else if(!Objects.equals(values.getLast(), ";")){
                throw new RuntimeException("line must end with a ;");
            }
        }else{
            if (!(Objects.equals(values.getFirst(), "}") || Objects.equals(values.getFirst(), "if"))) {
                if (!Objects.equals(values.getLast(), ";")) {
                    throw new RuntimeException("line must end with a ;");
                }
            }
        }
    }
}
