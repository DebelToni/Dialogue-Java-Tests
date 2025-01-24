package com.example;

import java.util.List;
import java.util.ArrayList;

class DialogueOption {
	private String line;
	private DialogueStep step;

	private List<IOptional> optionalModifiers;
	private List<IReward> rewardModifiers;
	private List<IRequirement> requirementModifiers;

	public DialogueOption(String line, DialogueStep step) {
		if (line == null || line.isEmpty()) {
			throw new IllegalArgumentException("Line cannot be null");
		}

		// Закоментирам го, защото иначе няма как да започнем създаването на
		// структурата, защото класовете зависят един от друг, за да се
		// инициализизрат, а и имаме опция за край на играта, което ще приема, че е
		// null
		// if (step == null) { throw new IllegalArgumentException("Step cannot
		// be null");
		// }

		this.line = line;
		this.step = step;

		this.optionalModifiers = new ArrayList<IOptional>();
		this.rewardModifiers = new ArrayList<IReward>();
		this.requirementModifiers = new ArrayList<IRequirement>();
	}
	public void setStep(DialogueStep step) {
		this.step = step;
	}

	public String getLine() {
		return line;
	}

	public DialogueStep getStep() {
		return step;
	}

	public void addOptionalModifier(IOptional modifier) {
		if (modifier == null) {
			throw new IllegalArgumentException("Modifier cannot be null");
		}
		optionalModifiers.add(modifier);
	}

	public void addRewardModifier(IReward modifier) {
		if (modifier == null) {
			throw new IllegalArgumentException("Modifier cannot be null");
		}
		rewardModifiers.add(modifier);
	}

	public void addRequirementModifier(IRequirement modifier) {
		if (modifier == null) {
			throw new IllegalArgumentException("Modifier cannot be null");
		}
		requirementModifiers.add(modifier);
	}

	public boolean isAvailable(Player player) {
		for (IOptional modifier : optionalModifiers) {
			if (!modifier.test(player)) {
				return false;
			}
		}
		return true;
	}

	public void applyRewards(Player player) {
		for (IReward modifier : rewardModifiers) {
			modifier.reward(player);
		}
	}

	public void applyRequirements(Player player) throws Exception {
		for (IRequirement modifier : requirementModifiers) {
			modifier.take(player);
		}
	}
}
