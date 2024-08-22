package interpreter.inputs;

public class SystemReadEnv implements ReadEnvSource {
    @Override
    public String readEnv(String name) {
        return System.getenv(name);
    }
}
