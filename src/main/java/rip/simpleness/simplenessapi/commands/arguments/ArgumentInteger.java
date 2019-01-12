package rip.simpleness.simplenessapi.commands.arguments;

public class ArgumentInteger extends Argument<Integer> {

    public ArgumentInteger(boolean optional, String check) {
        super(optional, check);
    }

    public ArgumentInteger(String check) {
        super(check);
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public boolean isArgumentType() {
        try {
            setValue(Integer.parseInt(check));
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
}
