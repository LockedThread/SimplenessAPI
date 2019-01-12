package rip.simpleness.simplenessapi.commands;

public interface ISubCommand {

    Class<? extends Command> getParentCommand();

}
