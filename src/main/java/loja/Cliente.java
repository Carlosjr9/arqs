package loja;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name="tb_cliente", uniqueConstraints = {
	    @UniqueConstraint(columnNames = { "cpf"}),
	    @UniqueConstraint(columnNames = { "login"})
	})

@NamedQueries({
	@NamedQuery(name="Cliente.findByName", query = "select o from Cliente o where o.nome like :nome")
})

public class Cliente {
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	@Override
	public String toString() {
		return "Client [id=" + id + ", nome=" + nome + ", login=" + login + ", senha=" + senha + ", perfil=" + perfil
				+ ", cpf=" + cpf + ", telefone=" + telefone + ", email=" + email + ", dataNascimento=" + dataNascimento
				+ ", dataCadastro=" + dataCadastro + "]";
	}
	
	public Cliente() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Cliente(Long id, String nome, String login, String senha, String perfil, String cpf, String telefone,
			String email, Date dataNascimento, Date dataCadastro) {
		super();
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.perfil = perfil;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.dataCadastro = dataCadastro;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min=3, max=100)
	@Pattern(regexp="[A-zÀ-ú.´ ]*", message="Caracteres permitidos: letras, espaços, ponto e aspas simples")
	@Column(length=100, nullable=false)
	private String nome;
	
	@NotBlank
	@Pattern(regexp="[A-z0-9]*", message="Caracteres permitidos: letras e números")
	@Size(min=8, max=15)
	@Column(length=15, nullable=false)
	private String login;
	
	@NotBlank
	@Size(max=100)
	@Column(length=100, nullable=false)
	private String senha;
	
	@NotBlank
	@Pattern(regexp="[A-zÀ-ú]*")
	@Size(max=100)
	@Column(length=100, nullable=false)
	private String perfil;
	
	@CPF
	@Column(length=11, nullable=false)
	private String cpf;
	
	@Pattern(regexp="\\(\\d{2}\\)\\d{0,1}\\d{4}-\\d{4}", message="Fornecer um telefone no formato (99)09999-9999")
	@Size(max=100)
	@Column(length=14, nullable=true)
	private String telefone;
	
	@Email
	@Column(length=100, nullable=true)
	private String email;
	
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_nascimento", nullable=false)
	private Date dataNascimento;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_cadastro", nullable=false)
	private Date dataCadastro;
	
	@Version
	private Long version;
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
}
