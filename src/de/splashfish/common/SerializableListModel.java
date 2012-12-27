package de.splashfish.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * 
 * @author lukas
 *
 * @param <E>
 */
public class SerializableListModel<E> implements ListModel, Serializable, Iterable<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4675346973195274115L;

			  private ArrayList<E> 				  data;
	transient private ArrayList<ListDataListener> listeners;
	
	public SerializableListModel() {
		this.data = new ArrayList<E>();
	}

	private void createListeners() {
		if(listeners == null)
			this.listeners = new ArrayList<ListDataListener>();
	}
	
	@Override
	public Iterator<E> iterator() {
		return new SerializableListIterator();
	}

	public void addElement(E element) {
		this.data.add(element);
		
		for(ListDataListener listener : listeners)
			listener.contentsChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, data.size()-1, data.size()-1));
	}
	
	public void removeElement(int index) {
		this.data.remove(index);
		
		createListeners();
		for(ListDataListener listener : listeners)
			listener.contentsChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index, index));
	}
	
	public void clear() {
		this.data.clear();
	}
	
	@Override
	public E getElementAt(int index) {
		return this.data.get(index);
	}

	@Override
	public int getSize() {
		return this.data.size();
	}
	
	@Override
	public void removeListDataListener(ListDataListener e) {
		createListeners();
		listeners.remove(e);
	}
	
	@Override
	public void addListDataListener(ListDataListener e) {
		createListeners();
		listeners.add(e);
	}
	
	private class SerializableListIterator implements Iterator<E> {
		
		private int pointer = 0;
		
		@Override
		public boolean hasNext() {
			return getSize() > pointer;
		}

		@Override
		public E next() {
			return (E) getElementAt(pointer++);
		}

		@Override
		public void remove() {
			if(pointer > 0)
				removeElement(pointer-1);
		}
		
	}
	
}
