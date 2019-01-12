package rip.simpleness.simplenessapi.managers;

import rip.simpleness.simplenessapi.SimplenessAPI;
import rip.simpleness.simplenessapi.modules.Module;

public abstract class Manager<M extends Module> {

    public M instance;
    public SimplenessAPI simplenessAPI;

    public Manager(M instance) {
        this.instance = instance;
        this.simplenessAPI = SimplenessAPI.getInstance();
    }
}
