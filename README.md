# JAVA-EjercicioSerializacion
Ejercicio que simula la gestión de pacientes en un hospital (muy básico).
Cada alta de paciente se representa mediante un objeto Paciente.
La lista de objetos Paciente se serializan en un fichero para su posterior recuperación.
El programa tiene un menú de consola que permite:
1.- Añadir datos del paciente
2.- Listar pacientes
3.- Buscar paciente
4.- Borrar fichero de datos
5.- Salir

En la clase Tarea06Paciente se han implementado dos versiones de los métodos para serializar y desarializar la lista de pacientes en fichero:
  - Serialización/deserialización objeto a objeto: escribeFicheroPacientes/leeFicheroPacientes
  - Serialización/deserialización del ArrayList que contiene todos los objetos: escribeFicheroPacientesV2/leeFicheroPacientesV2
  
 Probar ambas versiones de forma aislada llamando desde métido main a una u otra versión.

