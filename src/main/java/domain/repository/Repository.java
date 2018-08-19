package domain.repository;

import java.util.List;
import java.util.Optional;

import domain.entity.BaseEntity;

/**
 * Definicao das operacoes basicas de persistencia
 */
public interface Repository<E extends BaseEntity> {
	public Optional<E> buscarPorID(String oid);

	public List<E> buscarTodos();

	public E persist(E entity);

	public E merge(E entity);
	
	public E salvar(E entidade);

	public void refresh(E entity);

	public void deletar(E entity);

}
