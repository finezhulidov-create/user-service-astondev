package com.zhulidov.user_service_astondev.vision;

import com.zhulidov.user_service_astondev.config.annotations.AppComponent;
import com.zhulidov.user_service_astondev.config.annotations.Inject;
import com.zhulidov.user_service_astondev.util.ConsoleRenderer;
import com.zhulidov.user_service_astondev.util.HibernateUtil;

@AppComponent
public class MenuHandler {
    @Inject
    private final ConsoleRenderer renderer;

    public MenuHandler(ConsoleRenderer renderer) {
        this.renderer = renderer;
    }

    public Operation showMainMenu() {
        renderer.printMenu();
        int choice = renderer.promptInt("");
        return Operation.fromCode(choice);
    }
}
