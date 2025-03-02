import java.util.Set;

public class Clases {
	private int codClase;
	private String nombre;
	private String dificultad;
	private int duracMin;
	private Set<Monitores> setMonitores;
	
	public Clases() {
	}

	public Clases(int codClase, String nombre, String dificultad, int duracMin, Set<Monitores> setMonitores) {
		this.codClase = codClase;
		this.nombre = nombre;
		this.dificultad = dificultad;
		this.duracMin = duracMin;
		this.setMonitores = setMonitores;
	}

	public int getCodClase() {
		return codClase;
	}

	public void setCodClase(int codClase) {
		this.codClase = codClase;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDificultad() {
		return dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}

	public int getDuracMin() {
		return duracMin;
	}

	public void setDuracMin(int duracMin) {
		this.duracMin = duracMin;
	}

	public Set<Monitores> getSetMonitores() {
		return setMonitores;
	}

	public void setSetMonitores(Set<Monitores> setMonitores) {
		this.setMonitores = setMonitores;
	}
	
	
	
}