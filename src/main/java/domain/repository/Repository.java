package domain.repository;

import java.util.List;
import java.util.Optional;

import domain.entity.BaseEntity;

public interface Repository<E extends BaseEntity> {
	
	public E searchByID(long oid);

	public Optional<List<E>> listAll();

	public Optional<E> persist(E entity);

	public Optional<E> merge(E entity);
	
	public Optional<E> save(E entidade);

	public void refresh(E entity);

	public void delete(E entity);

}