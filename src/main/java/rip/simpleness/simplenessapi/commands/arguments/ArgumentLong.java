package rip.simpleness.simplenessapi.commands.arguments;

public class ArgumentLong extends Argument<Long> {

    public ArgumentLong(boolean optional, String check) {
        super(optional, check);
    }

    public ArgumentLong(String check) {
        super(check);
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public boolean isArgumentType() {
        try {
            setValue(Long.parseLong(check));
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
}
