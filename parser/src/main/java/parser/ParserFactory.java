package parser;

import parser.builder.AssignationASTBuilder;
import parser.builder.ConditionalASTBuilder;
import parser.builder.DeclarationASTBuilder;
import parser.builder.MethodASTBuilder;

import java.util.List;
import java.util.Objects;

public class ParserFactory {

    public static Parser parserVersion(String version){
        if (Objects.equals(version, "1.0")){
            return new Parser("1.0", List.of(
                    new AssignationASTBuilder("1.0"),
                    new DeclarationASTBuilder("1.0"),
                    new MethodASTBuilder("1.0")
            ));
        }else if (Objects.equals(version, "1.1")){
            return new Parser("1.1", List.of(
                    new AssignationASTBuilder("1.1"),
                    new DeclarationASTBuilder("1.1"),
                    new MethodASTBuilder("1.1"),
                    new ConditionalASTBuilder()
            ));
        }else{
            throw new IllegalStateException("Unexpected value: " + version);
        }
    }
}
