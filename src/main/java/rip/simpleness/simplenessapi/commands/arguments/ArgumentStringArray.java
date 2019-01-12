package rip.simpleness.simplenessapi.commands.arguments;

public class ArgumentStringArray extends Argument<String> {

    public ArgumentStringArray(boolean optional, String check) {
        super(optional, check);
    }

    public ArgumentStringArray(String check) {
        super(check);
    }

    @Override
    public String getValue() {
        return check;
    }

    @Override
    public boolean isArgumentType() {
        return true;
    }
}
