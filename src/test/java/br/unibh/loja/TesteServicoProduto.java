package br.unibh.loja;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import loja.Categoria;
import loja.Produto;
import br.unibh.loja.negocio.DAO;
import br.unibh.loja.negocio.ServicoCategoria;
import br.unibh.loja.negocio.ServicoProduto;
import br.unibh.loja.util.Resources;

@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteServicoProduto {

	@Deployment
	public static Archive<?> createTestArchive() {
		// Cria o pacote que vai ser instalado no Wildfly para realizacao dos testes
		return ShrinkWrap.create(WebArchive.class, "testeloja.war")
				.addClasses(Produto.class, Categoria.class, DAO.class, ServicoProduto.class, Resources.class)
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	// Realiza as injecoes com CDI
		@Inject
		private Logger log;
		
		@Inject
		private ServicoProduto sp;
		
		@Test
		public void teste01_inserirProdutoSemErro() throws Exception {
			System.out.println(log);
			log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
			Categoria c = new Categoria(null, "Descricao Categoria");
			Produto o = new Produto(null, "Produto", "Descricao", c, new BigDecimal(300), "Fabricante");
			sp.insert(o);
			Produto aux = (Produto) sp.findByName("Produto").get(0);
			assertNotNull(aux);
			log.info("============> Finalizando o teste " +
			Thread.currentThread().getStackTrace()[1].getMethodName());
		}
		
		@Test
		public void teste02_inserirProdutoComErro() throws Exception {
			log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
			try {
				Categoria c = new Categoria(1L, "Descricao Categoria");
				Produto o = new Produto(1L, "(Produto1)", "Descricao1", c, new BigDecimal(-2), "Farbicante1");
				sp.insert(o);
			} catch (Exception e){
				assertTrue(checkString(e, "Caracteres permitidos: letras, espaços, acentos, ponto, barra e aspas simples"));
			}
			log.info("============> Finalizando o teste " +
			Thread.currentThread().getStackTrace()[1].getMethodName());
		}
		
		@Test
			public void teste03_atualizarProduto() throws Exception {
			log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
			Produto o = (Produto) sp.find(1L);
			o.setDescricao("Produto teste atualizar");
			sp.update(o);
			Produto aux = (Produto) sp.find(1L);
			assertNotNull(aux);
			log.info("============> Finalizando o teste " +
			Thread.currentThread().getStackTrace()[1].getMethodName());
		}
		
		@Test
			public void teste04_excluirProduto() throws Exception {
			log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
			Produto o = (Produto) sp.findByName("Produto").get(0);
			sp.delete(o);
			assertEquals(0, sp.findByName("Produto teste atualizar").size());
			log.info("============> Finalizando o teste " +
			Thread.currentThread().getStackTrace()[1].getMethodName());
		}
		private boolean checkString(Throwable e, String str){
		if (e.getMessage().contains(str)){
			return true;
			} else if (e.getCause() != null){
				return checkString(e.getCause(), str);
			}
			return false;
		}
}