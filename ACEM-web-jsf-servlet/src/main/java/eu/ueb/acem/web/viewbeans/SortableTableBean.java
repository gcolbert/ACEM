package eu.ueb.acem.web.viewbeans;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SortableTableBean<E extends Comparable<E>> extends TableBean<E> {

	private static final long serialVersionUID = -5189856159830582038L;

	public void sort() {
		Collections.sort(tableEntries);
	}

	public void sortReverseOrder() {
		Collections.sort(tableEntries, Collections.reverseOrder());
	}

	@Override
	public List<E> setTableEntries(Collection<E> collectionEntities) {
		super.setTableEntries(collectionEntities);
		sortReverseOrder();
		return tableEntries;
	}
	
}
