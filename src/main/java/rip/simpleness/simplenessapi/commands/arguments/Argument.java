package rip.simpleness.simplenessapi.commands.arguments;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public abstract class Argument<R> {

    public R value;
    private boolean optional;
    public String check;

    public Argument(boolean optional, String check) {
        this.optional = optional;
        this.check = check;
    }

    public Argument(String check) {
        this(false, check);
    }

    public boolean isOptional() {
        return optional;
    }

    public abstract R getValue();

    public abstract boolean isArgumentType();

    public void setValue(R value) {
        this.value = value;
    }
}
