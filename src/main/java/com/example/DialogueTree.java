package com.example;

import java.util.HashMap;
import java.util.Map;

public class DialogueTree {
	private DialogueStep root;
	private Map<Integer, DialogueStep> nodes;
	private DialogueStep current;

	public DialogueTree(DialogueStep root) {
		if (root == null) {
			throw new IllegalArgumentException("Root cannot be null");
		}
		this.root = root;
		this.nodes = new HashMap<>();
		this.current = root;
		this.setNodes(root);
	}

	private void setNodes(DialogueStep step) {
		if (step == null) {
			return;
		}
		nodes.put(step.getId(), step);
		if (step.getPlayerOptions() != null) {
			for (DialogueOption option : step.getPlayerOptions()) {
				if (option.getStep() != null) {
					setNodes(option.getStep());
				}
			}
		}else 
		if (step.getNextStep() != null) {
			setNodes(step.getNextStep());
		}
	}

	// public void addNode(DialogueStep node) {
	// nodes.add(node);
	// }

	public DialogueStep findById(int id) {
		DialogueStep result = nodes.get(id);
		if (result == null) {
			throw new IllegalArgumentException("Step with id=" + id + " does not exist!");
		}
		return result;
	}

	public DialogueStep getRoot() {
		return root;
	}
}
