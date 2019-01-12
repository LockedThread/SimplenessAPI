package rip.simpleness.simplenessapi.commands.arguments;

import java.lang.reflect.InvocationTargetException;

public enum ArgumentType {

    /**
     * Optional Arguments
     */

    ARGUMENT_OPTIONAL_INTEGER(ArgumentInteger.class),
    ARGUMENT_OPTIONAL_LONG(ArgumentLong.class),
    ARGUMENT_OPTIONAL_DOUBLE(ArgumentDouble.class),
    ARGUMENT_OPTIONAL_PLAYER(ArgumentPlayer.class),
    ARGUMENT_OPTIONAL_STRING_ARRAY(ArgumentStringArray.class),

    /**
     * Regular Arguments
     */
    ARGUMENT_CUSTOM_ITEM(ArgumentCustomItem.class),
    ARGUMENT_INTEGER(ArgumentInteger.class),
    ARGUMENT_LONG(ArgumentLong.class),
    ARGUMENT_DOUBLE(ArgumentDouble.class),
    ARGUMENT_PLAYER(ArgumentPlayer.class),
    ARGUMENT_STRING_ARRAY(ArgumentStringArray.class);

    private Class<? extends Argument> aClass;

    ArgumentType(Class<? extends Argument> aClass) {
        this.aClass = aClass;
    }

    public Argument build(String check) {
        try {
            return name().contains("OPTIONAL") ?
                    aClass.getConstructor(boolean.class, String.class).newInstance(true, check) :
                    aClass.getConstructor(String.class).newInstance(check);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
