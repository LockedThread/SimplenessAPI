package rip.simpleness.simplenessapi.commands.arguments;

public class ArgumentDouble extends Argument<Double> {

    public ArgumentDouble(boolean optional, String check) {
        super(optional, check);
    }

    public ArgumentDouble(String check) {
        super(check);
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public boolean isArgumentType() {
        try {
            setValue(Double.parseDouble(check));
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
}
