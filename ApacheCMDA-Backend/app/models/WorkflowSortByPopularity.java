package models;

import java.util.*;
public class WorkflowSortByPopularity implements Comparator<Workflow>{

	public WorkflowSortByPopularity(){
		
	}
	@Override
	public int compare(Workflow o1, Workflow o2) {
		return o2.getPopularity()-o1.getPopularity();
	}
}
