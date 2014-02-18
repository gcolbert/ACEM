package eu.ueb.acem.web.viewbeans.bleu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;

public class PedagogicalActivityViewBean implements Serializable, Comparable<PedagogicalActivityViewBean> {

	private static final long serialVersionUID = 8190209757734229700L;

	private ActivitePedagogique pedagogicalActivity;

	private Long positionInScenario;
	private String name;
	private String objective;
	private String description;
	private String duration;
	private List<ResourceViewBean> resources;

	public PedagogicalActivityViewBean(ActivitePedagogique pedagogicalActivity) {
		setPedagogicalActivity(pedagogicalActivity);
		resources = new ArrayList<ResourceViewBean>();
	}

	public ActivitePedagogique getPedagogicalActivity() {
		return pedagogicalActivity;
	}

	public void setPedagogicalActivity(ActivitePedagogique pedagogicalActivity) {
		this.pedagogicalActivity = pedagogicalActivity;
		setPositionInScenario(pedagogicalActivity.getPositionInScenario());
		setName(pedagogicalActivity.getName());
		setObjective(pedagogicalActivity.getObjective());
		setDescription(pedagogicalActivity.getDescription());
		setDuration(pedagogicalActivity.getDuration());
		//setResources(pedagogicalActivity.getResources());
	}

	public Long getPositionInScenario() {
		return positionInScenario;
	}

	public void setPositionInScenario(Long positionInScenario) {
		this.positionInScenario = positionInScenario;
		pedagogicalActivity.setPositionInScenario(positionInScenario);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		pedagogicalActivity.setName(name);
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
		pedagogicalActivity.setObjective(objective);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		pedagogicalActivity.setDescription(description);
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
		pedagogicalActivity.setDuration(duration);
	}

	public List<ResourceViewBean> getResources() {
		return resources;
	}

	public void setResources(List<ResourceViewBean> resources) {
		this.resources = resources;
		//pedagogicalActivity.setResources(resources.); // TODO ajouter les ressources
	}

	@Override
	public int compareTo(PedagogicalActivityViewBean o) {
		return getPedagogicalActivity().compareTo(o.getPedagogicalActivity());
	}
	
}
