import java.util.Set;

public class Gimnasios {
	private int id;
	private String nombre;
	private Monitores responsable;
	private String direccion;
	private String localidad;
	private String provincia;
	private Set<Monitores> setMonitores;
	
	public Gimnasios() {
	}

	public Gimnasios(int id, String nombre, Monitores responsable, String direccion, String localidad, String provincia,
			Set<Monitores> setMonitores) {
		this.id = id;
		this.nombre = nombre;
		this.responsable = responsable;
		this.direccion = direccion;
		this.localidad = localidad;
		this.provincia = provincia;
		this.setMonitores = setMonitores;
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

	public Monitores getMonitor() {
		return responsable;
	}

	public void setMonitor(Monitores responsable) {
		this.responsable = responsable;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Set<Monitores> getSetMonitores() {
		return setMonitores;
	}

	public void setSetMonitores(Set<Monitores> setMonitores) {
		this.setMonitores = setMonitores;
	}
}