package com.example;

import java.util.ArrayList;
import java.util.List;

public class DialogueStep {
	private static int id_count = 0;
	private int id = -1;
	private String line;
	private List<DialogueOption> player_options = new ArrayList<DialogueOption>();
	private DialogueStep next_step;

	public int getId() {
		return id;
	}

	public DialogueStep(String line, List<DialogueOption> player_options, DialogueStep next_step) {
		if (line == null || line.isEmpty()) {
			throw new IllegalArgumentException("Line cannot be null");
		}

		// if (player_options.size() != 0 && next_step != null) {
		if (player_options != null && next_step != null) {
			throw new IllegalArgumentException("Cannot have player options with a next step");
		}
		if (player_options == null && next_step == null) {
			throw new IllegalArgumentException("Must have player options or a next step");
		}

		this.line = line;
		// this.player_options = player_options;
		if (player_options != null) {
			this.player_options.addAll(player_options);
		} else {
			this.player_options = new ArrayList<DialogueOption>();
		}
		this.next_step = next_step;
		this.id = id_count++;
	}

	public String getLine() {
		return line;
	}

	public List<DialogueOption> getPlayerOptions() {
		if (player_options.size() == 0) {
			//return null;
		}
		return player_options;
	}

	public DialogueStep getNextStep() {
		return next_step;
	}

	// това е важно да го има, защото иначе по условие е невъзможно да създадем
	// Loop от степове, защото всяка ще require-ва следващта. Това е Fix на
	// добрия game design:
	public void addOption(DialogueOption option) {
		if (option == null) {
			throw new IllegalArgumentException("Option cannot be null");
		}
		player_options.add(option);
	}
}
