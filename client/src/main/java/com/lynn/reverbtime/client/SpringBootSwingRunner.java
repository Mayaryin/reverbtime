package com.lynn.reverbtime.client;

import com.lynn.reverbtime.client.UI.mainFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/*
* The run() method of this runner class will be executed after the application starts.
* It starts the GUI.
* */

public class SpringBootSwingRunner {

    @Component
    public class SpringBootSwingCommandLineRunner implements CommandLineRunner {
        private final mainFrame guiController;

        @Autowired
        public SpringBootSwingCommandLineRunner(mainFrame guiController) {
            this.guiController = guiController;
        }


        @Override
        public void run(String... args) {
            guiController.setVisible(true);
        }
    }
}
