package com.github.soyamiyoshi.cli;

import com.beust.jcommander.JCommander;

public class ArgsParser {
    public static CommandLineArgs parse(String[] args) {
        CommandLineArgs cliArgs = new CommandLineArgs();
        JCommander commander = JCommander.newBuilder().addObject(cliArgs).build();
        commander.parse(args);

        if (cliArgs.isHelp()) {
            commander.usage();
            System.exit(1);
        }

        try {
            ArgsValidater.validate(cliArgs);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            commander.usage();
            System.exit(1);
        }

        return cliArgs;
    }

}
