 import java.sql.Date;

public class Monitores {
	private int id;
	private String nombre;
	private String apellido;
	private Date fechaNac;
	private char sexo;
	private Gimnasios gym;
	
	public Monitores() {
	}
	
	public Monitores(int id, String nombre, String apellido, Date fechaNac, char sexo, Gimnasios gym) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNac = fechaNac;
		this.sexo = sexo;
		this.gym = gym;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public Date getFechaNac() {
		return fechaNac;
	}
	
	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}
	
	public char getSexo() {
		return sexo;
	}
	
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	
	public Gimnasios getGym() {
		return gym;
	}
	
	public void setGym(Gimnasios gym) {
		this.gym = gym;
	}

	@Override
	public String toString() {
		return "ID: " + id + " Nombre: " + nombre + " Apellido: " + apellido + " Fecha Nacimiento: " + fechaNac + " Sexo: " + sexo + " Gym: " + gym.getNombre();
	}
}