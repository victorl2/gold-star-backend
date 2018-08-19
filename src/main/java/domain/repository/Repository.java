package domain.repository;

import java.util.List;
import java.util.Optional;

import domain.entity.BaseEntity;
import domain.exception.DAOException;

/**
 * Definicao das operacoes basicas de persistencia
 */
public interface Repository<E extends BaseEntity> {
	public Optional<E> buscarPorID(String oid);

	public List<E> buscarTodos() throws DAOException;

	public E persist(E entity) throws DAOException;

	public E merge(E entity) ;
	
	public E salvar(E entidade) throws DAOException;

	public void atualizar(E entity);

	public void deletar(E entity) throws DAOException;

}
