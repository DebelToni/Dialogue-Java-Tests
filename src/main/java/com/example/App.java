package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
	public void playGame(Player player, DialogueTree dialogueTree) {
		if (dialogueTree == null) {
			throw new IllegalArgumentException("Dialogue tree cannot be null");
		}
		if (player == null) {
			throw new IllegalArgumentException("Player cannot be null");
		}
		DialogueStep currentStep = dialogueTree.getRoot();
		Scanner scanner = new Scanner(System.in);

		while (currentStep != null) {
			System.out.println("Step ID: " + currentStep.getId());
			System.out.println("NPC: " + currentStep.getLine());

			List<DialogueOption> options = currentStep.getPlayerOptions();
			if (options != null && options.size() > 0) {
				for (int i = 0; i < options.size(); i++) {
					DialogueOption option = options.get(i);
					if (option.isAvailable(player)) {
						// System.out.println((i + 1) + ". " + option.getPlayerResponse());
						System.out.println((i + 1) + ". " + option.getLine());
					}
				}

				int choice = -1;
				while (choice < 0 || choice >= options.size()) {
					System.out.print("Choose an option: ");
					choice = scanner.nextInt() - 1;
				}

				DialogueOption selectedOption = options.get(choice);
				try {
					selectedOption.applyRequirements(player);
					selectedOption.applyRewards(player);
					currentStep = selectedOption.getStep();
				} catch (Exception e) {
					System.out.println("Cannot choose this option: " + e.getMessage());
				}
				System.out.println("You got: " + player.getInventory());

			} else {
				currentStep = currentStep.getNextStep();
				System.out.println("Press enter to continue...");
				scanner.nextLine();
				scanner.nextLine();
			}
		}

		System.out.println("Dialogue ended.");
		scanner.close();
	}

	public DialogueTree buildATree() {
		DialogueOption o1 = new DialogueOption("To marry your daughter.", null);
		DialogueOption o2 = new DialogueOption("To end your tyranny!", null);
		// average game of thrones episode

		List<DialogueOption> options = new ArrayList<>();
		options.add(o1);
		options.add(o2);
		DialogueStep s1 = new DialogueStep("I am the king. What do you want?", options, null);
		DialogueOption O3 = new DialogueOption("I want to speak with the king", s1);
		O3.addOptionalModifier(new CHAOption());

		DialogueOption o4 = new DialogueOption("Give me Light armor.", null);
		DialogueOption o5 = new DialogueOption("Give me Light armor and dont cheat!", null);

		o4.addRequirementModifier(new ItemRequirement("Medium bag of gold"));
		o5.addRequirementModifier(new ItemRequirement("Small bag of gold"));

		o4.addRewardModifier(new ItemReward("Light armor"));
		o5.addRewardModifier(new ItemReward("Light armor"));

		o4.addOptionalModifier(new CHALess5Option());
		o5.addOptionalModifier(new CHA5Option());

		List<DialogueOption> options2 = new ArrayList<>();
		options2.add(o4);
		options2.add(o5);
		// DialogueStep s2 = new DialogueStep("I have some armors", List.of(o4, o5),
		// null);
		DialogueStep s2 = new DialogueStep("I have some armors", options2, null);
		DialogueOption O1 = new DialogueOption("I want buy armor", s2);

		// DialogueStep s3 = new DialogueStep("Done. Anything else?", null, );
		List<DialogueOption> options3 = new ArrayList<>();
		options3.add(O1);
		options3.add(O3);
		DialogueStep s4 = new DialogueStep("Welcome to DunerLand!. How do I help you boss?", options3, null);

		DialogueStep s3 = new DialogueStep("Done, anything else?", null, s4);

		DialogueOption O2 = new DialogueOption("I have slain the dragon", s3);
		O2.addRequirementModifier(new ItemRequirement("Dragon head"));
		O2.addRewardModifier(new ItemReward("Small bag of gold"));

		s4.addOption(O2);

		DialogueOption O4 = new DialogueOption("SLAY DROGON!", s3);
		O4.addRewardModifier(new ItemReward("Dragon head"));

		s4.addOption(O4);

		DialogueTree tree = new DialogueTree(s4);
		return tree;
	}

	public static void main(String[] args) {
		App app = new App();
		DialogueTree tree = app.buildATree();
		Player player = new Player(100, 10, 11, 100, new ArrayList<String>());
		app.playGame(player, tree);
	}

	public void collectRewards(Player player, DialogueTree dialogueTree) {
		collectRewardsRecursive(player, dialogueTree.getRoot());
	}

	private void collectRewardsRecursive(Player player, DialogueStep step) {
		if (step == null)
			return;

		List<DialogueOption> options = step.getPlayerOptions();
		if (options != null) {
			for (DialogueOption option : options) {
				if (option.isAvailable(player)) {
					option.applyRewards(player);
					collectRewardsRecursive(player, option.getStep());
				}
			}
		} else {
			collectRewardsRecursive(player, step.getNextStep());
		}
	}
}
