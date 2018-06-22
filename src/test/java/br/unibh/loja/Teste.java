package br.unibh.loja;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Assert;
import org.junit.runners.MethodSorters;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

import loja.Categoria;
import loja.Cliente;
import loja.Produto;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Teste {

	private static Validator validator;
	
	@BeforeClass
		public static void setUp() {
		System.out.println("Inicializando validador...");
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void testeProduto() {
		Categoria c = new Categoria(1L, "Categoria");
		Produto p = new Produto(1L, "Produto", "Descricao@", c, new BigDecimal(2), "Farbicante");
		assertEquals(p.getNome(), "Produto");
		System.out.println(p);
		
		Set<ConstraintViolation<Produto>> constraintViolations = validator.validate( p );
		for (ConstraintViolation<Produto> pd: constraintViolations) {
			System.out.println(" Erro de Validacao: "+pd.getMessage());
		}
		Assert.assertEquals(1, constraintViolations.size() );
	}

	
	@Test
	public void testeProduto2() {
		Categoria c = new Categoria();
		Produto p = new Produto(1L, "(Produto1)", "Descricao1", c, new BigDecimal(-2), "Farbicante1");
		System.out.println(p);
		
		Set<ConstraintViolation<Produto>> constraintViolations = validator.validate( p );
		for (ConstraintViolation<Produto> pd: constraintViolations) {
			System.out.println(" Erro de Validacao: "+pd.getMessage());
		}
		Assert.assertEquals(4, constraintViolations.size() );
	}

	
	@Test
	public void testeCategoria() {
		Categoria c = new Categoria(1L, "Categoria");
		System.out.println(c);
		Set<ConstraintViolation<Categoria>> constraintViolations = validator.validate( c );
		for (ConstraintViolation<Categoria> ct: constraintViolations) {
			System.out.println(" Erro de Validacao: "+ct.getMessage());
		}
		Assert.assertEquals(0, constraintViolations.size() );
	}
	
	@Test
	public void testeCategoria2() {
		Categoria c = new Categoria(1L, "");
		System.out.println(c);
		Set<ConstraintViolation<Categoria>> constraintViolations = validator.validate( c );
		for (ConstraintViolation<Categoria> ct: constraintViolations) {
			System.out.println(" Erro de Validacao: "+ct.getMessage());
		}
		Assert.assertEquals(1, constraintViolations.size() );
	}
	
	
	@Test
	public void testeCliente() {
		Cliente c = new Cliente(1L, "Cliente", "cliente", "123456789", "perfil", "224556232-88", "(31)99998888", "cliente@cliente.com", new Date(), new Date());
		System.out.print(c);
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate( c );
		for (ConstraintViolation<Cliente> cl: constraintViolations) {
			System.out.println(" Erro de Validacao: "+cl.getMessage());
		}
		Assert.assertEquals(3, constraintViolations.size() );
	}
	
	
	@Test
	public void testeCliente2() {
		Cliente c = new Cliente(1L, "Cliente1", "cliénte", "123", "perfil1", "26.232-88", "3-1399998888", "cliente", new Date(), new Date());
		System.out.print(c);
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate( c );
		for (ConstraintViolation<Cliente> cl: constraintViolations) {
			System.out.println(" Erro de Validacao: "+cl.getMessage());
		}
		Assert.assertEquals(7, constraintViolations.size() );
	}
}