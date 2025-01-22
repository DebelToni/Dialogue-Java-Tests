package com.example;

import java.util.ArrayList;
import java.util.List;

class DialogueTree {
		private DialogueStep root;
		private DialogueStep current;

		private List<DialogueStep> steps = new ArrayList<DialogueStep>();

		public DialogueStep findById(int id){
				for (DialogueStep step : steps){
						if (step.getId() == id){
								return step;
						}
				}
				throw new IllegalArgumentException("No step with id " + id + " found.");
		}
}

