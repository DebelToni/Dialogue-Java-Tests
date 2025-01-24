package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DialogueFileParser {

	private static class TempStepData {
		String stepId;
		String npcLine;
		String nextStepId;
		List<ParsedOption> options = new ArrayList<>();
	}

	private static class ParsedOption {
		String text;
		String nextStepId;
		List<String> requirements = new ArrayList<>();
		List<String> rewards = new ArrayList<>();
		List<String> optionalModifiers = new ArrayList<>();
	}

	public static DialogueTree parseDialogueFile(String filename) throws IOException {
		List<String> lines = Files.readAllLines(Path.of(filename));

		Map<String, TempStepData> tempSteps = new LinkedHashMap<>();

		TempStepData currentStep = null;
		for (String rawLine : lines) {
			String line = rawLine.trim();
			if (line.isEmpty()) {
				continue;
			}
			if (line.startsWith("STEP ")) {

				String stepId = line.substring("STEP ".length()).trim();
				currentStep = new TempStepData();
				currentStep.stepId = stepId;
				tempSteps.put(stepId, currentStep);
			} else if (line.startsWith("NPC=")) {
				if (currentStep == null) {
					throw new RuntimeException("Found NPC= line, but not inside a STEP.");
				}
				String npc = line.substring("NPC=".length()).trim();
				currentStep.npcLine = npc;
			} else if (line.startsWith("NEXT=")) {
				if (currentStep == null) {
					throw new RuntimeException("Found NEXT= line, but not inside a STEP.");
				}
				String nextId = line.substring("NEXT=".length()).trim();
				currentStep.nextStepId = nextId;
			} else if (line.startsWith("OPTIONS=")) {
				if (currentStep == null) {
					throw new RuntimeException("Found OPTIONS= line, but not inside a STEP.");
				}

				String afterEq = line.substring("OPTIONS=".length()).trim();

				String[] opts = afterEq.split("\\|");
				for (String optStr : opts) {
					optStr = optStr.trim();
					ParsedOption p = parseOptionLine(optStr);
					currentStep.options.add(p);
				}
			} else if (line.equals("ENDSTEP")) {

				currentStep = null;
			} else {

				System.err.println("Warning: unrecognized line: " + line);
			}
		}

		Map<String, DialogueStep> realSteps = new HashMap<>();

		Map<ParsedOption, DialogueOption> realOptions = new HashMap<>();
		for (TempStepData tsd : tempSteps.values()) {
			for (ParsedOption po : tsd.options) {
				DialogueOption option = new DialogueOption(po.text, null);

				for (String reqItem : po.requirements) {
					if (!reqItem.isEmpty()) {
						option.addRequirementModifier(new ItemRequirement(reqItem));
					}
				}
				for (String rewItem : po.rewards) {
					if (!rewItem.isEmpty()) {
						option.addRewardModifier(new ItemReward(rewItem));
					}
				}
				for (String modClass : po.optionalModifiers) {
					if (!modClass.isEmpty()) {
						option.addOptionalModifier(createOptionalModifier(modClass));
					}
				}
				realOptions.put(po, option);
			}
		}

		Map<String, DialogueStep> stepObjs = new HashMap<>();

		for (TempStepData tsd : tempSteps.values()) {

			List<DialogueOption> stepOptions = null;
			DialogueStep nextStep = null;

			if (tsd.options != null && !tsd.options.isEmpty()) {

				stepOptions = new ArrayList<>();
				for (ParsedOption po : tsd.options) {
					DialogueOption opt = realOptions.get(po);
					stepOptions.add(opt);
				}
			} else {
				stepOptions = new ArrayList<>();
				stepOptions.add(new DialogueOption("Continue", null));
			}

			DialogueStep step = new DialogueStep(tsd.npcLine, stepOptions, null);
			stepObjs.put(tsd.stepId, step);
		}

		for (TempStepData tsd : tempSteps.values()) {
			DialogueStep realStep = stepObjs.get(tsd.stepId);

			if (tsd.nextStepId != null && !tsd.nextStepId.isEmpty()) {
				if (!tsd.nextStepId.equals("-1")) {
					DialogueStep ns = stepObjs.get(tsd.nextStepId);
					if (ns == null) {
						throw new RuntimeException("Unknown next step id: " + tsd.nextStepId);
					}

					realStep.setNextStep(ns);
				}
			}

			for (ParsedOption po : tsd.options) {
				if (!po.nextStepId.equals("-1")) {
					DialogueOption realOpt = realOptions.get(po);
					DialogueStep targetStep = stepObjs.get(po.nextStepId);
					if (targetStep == null) {
						throw new RuntimeException("Unknown step id: " + po.nextStepId);
					}

					setDialogueOptionStep(realOpt, targetStep);
				}
			}
		}

		DialogueStep root = stepObjs.get("s4");
		if (root == null) {

			root = stepObjs.values().iterator().next();
		}

		DialogueTree tree = new DialogueTree(root);
		return tree;
	}

	private static ParsedOption parseOptionLine(String optStr) {
		ParsedOption po = new ParsedOption();

		String[] parts = optStr.split("->");
		if (parts.length != 2) {
			throw new RuntimeException("Option line doesn't contain '->': " + optStr);
		}
		po.text = parts[0].trim();

		String right = parts[1].trim();
		String nextId = right;
		String modsPart = null;

		int bracketIndex = right.indexOf('[');
		if (bracketIndex != -1) {

			nextId = right.substring(0, bracketIndex).trim();

			int closeIndex = right.indexOf(']', bracketIndex);
			if (closeIndex == -1) {
				throw new RuntimeException("Missing closing bracket ] in: " + optStr);
			}
			modsPart = right.substring(bracketIndex + 1, closeIndex).trim();
		}

		po.nextStepId = nextId;

		if (modsPart != null && !modsPart.isEmpty()) {

			String[] tokens = modsPart.split(",");
			for (String token : tokens) {
				token = token.trim();
				if (token.startsWith("REQ=")) {
					String item = token.substring("REQ=".length()).trim();
					if (!item.isEmpty()) {
						po.requirements.add(item);
					}
				} else if (token.startsWith("REW=")) {
					String item = token.substring("REW=".length()).trim();
					if (!item.isEmpty()) {
						po.rewards.add(item);
					}
				} else if (token.startsWith("MOD=")) {
					String cls = token.substring("MOD=".length()).trim();
					if (!cls.isEmpty()) {
						po.optionalModifiers.add(cls);
					}
				}
			}
		}

		return po;
	}

	private static void setDialogueOptionStep(DialogueOption opt, DialogueStep step) {

		try {
			java.lang.reflect.Field f = DialogueOption.class.getDeclaredField("step");
			f.setAccessible(true);
			f.set(opt, step);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static IOptional createOptionalModifier(String className) {
		switch (className) {
			case "CHAOption":
				return new CHAOption();
			case "CHA5Option":
				return new CHA5Option();
			case "CHALess5Option":
				return new CHALess5Option();
			default:
				throw new RuntimeException("Unknown optional modifier: " + className);
		}
	}
}
