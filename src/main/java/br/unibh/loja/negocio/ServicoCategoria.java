package br.unibh.loja.negocio;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import loja.Categoria;

@Stateless
@LocalBean
public class ServicoCategoria {

	@Inject
	EntityManager em;
	
	@Inject
	private Logger log;
	
	public Categoria insert(Categoria c) throws Exception {
		log.info("Persistindo "+c);
		em.persist(c);
		return c;
	}
	
	public Categoria update(Categoria c) throws Exception {
		log.info("Atualizando "+c);
		return em.merge(c);
	}
	
	public void delete(Categoria c) throws Exception {
		log.info("Removendo "+c);
		Object o = em.merge(c);
		em.remove(o);
	}
	
	public Categoria find(Long k) throws Exception {
		log.info("Encontrando pela chave "+k);
		return em.find(Categoria.class, k);
	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> findAll() throws Exception {
		log.info("Encontrando todos os objetos");
		return em.createQuery("from Categoria").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> findByName(String descricao) throws Exception {
		log.info("Encontrando o "+descricao);
		return em.createNamedQuery("Categoria.findByName")
		.setParameter("descricao", "%"+descricao+"%").getResultList();
	}
}