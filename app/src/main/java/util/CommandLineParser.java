package util;

import java.util.HashMap;

public class CommandLineParser {
	private HashMap<String, String> argsMap;

	public CommandLineParser(String[] args) {
		argsMap = new HashMap<String, String>();
		parse(args);
	}

	private void parse(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("-")) {
				if (i < args.length - 1 && !args[i + 1].startsWith("-")) {
					argsMap.put(args[i], args[i + 1]);
					i++;
				} else {
					argsMap.put(args[i], "");
				}
			}
		}
	}

	public String getArgument(String arg) {
		return argsMap.get(arg);
	}
}
