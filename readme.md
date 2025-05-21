# 📊 Calculadora de Cadenas de Markov

Esta es una aplicación de escritorio interactiva desarrollada en Java con JavaFX que permite realizar cálculos relacionados con cadenas de Markov, incluyendo vectores de estado futuro y estado estacionario.

---

## ✨ Funcionalidades

- 🔁 **Cálculo de Estado Estacionario**  
  Calcula el vector de estado estacionario de una cadena de Markov dada una matriz de transición. Este vector representa las probabilidades a largo plazo de estar en cada estado.

- 🔮 **Cálculo de Estado Futuro**  
  Permite calcular el vector de estado después de un número determinado de pasos, mostrando cómo evolucionan las probabilidades con el tiempo.

- 🧮 **Interfaz Gráfica Intuitiva**  
  Interfaz amigable basada en JavaFX para ingresar matrices, vectores y parámetros de forma sencilla.

---

## 🧭 Cómo Usar

1. ✍️ Ingresa la **Matriz de Transición**:  
   - Debe ser cuadrada (n x n) y cada fila debe sumar 1.
   - Representa las probabilidades de transición entre estados.

2. ✍️ Ingresa el **Vector de Estado Inicial**:  
   - Debe tener la misma longitud que el número de estados.
   - Sus elementos deben sumar 1.

3. ⚙️ Selecciona el tipo de cálculo:  
   - 📈 "Estado Futuro": especifica el número de pasos.
   - 📉 "Estado Estacionario": sin pasos adicionales.

4. ✅ Haz clic en el botón "Calcular".

5. 📋 Visualiza los resultados en la parte inferior de la pantalla.

---

## ⚙️ Requisitos

- ☕ Java Development Kit (JDK 11 o superior)
- 📦 Maven

---

## 🛠️ Construcción y Ejecución

1. 📥 Clona el repositorio:
   ```bash
   git clone <repository_url>

2. 📂 Entra en el directorio del proyecto:
   ```bash 
   cd <nombre_del_proyecto>
   ```

3. 🧱 Construye el proyecto:
    ```bash
    mvn clean install
    ```

4. ▶️ Ejecuta la aplicación:
    ```bash
    mvn javafx:run
    ```

## 📁 Estructura del Proyecto

    src/
    ├── main/
    │   ├── java/
    │   │   └── com/varcal/
    │   │       ├── App.java                          # Punto de entrada de la aplicación
    │   │       ├── controller/MarkovChainController.java  # Lógica de control de interfaz
    │   │       └── logica/MarkovChain.java           # Lógica de cálculo de Markov
    │   └── resources/
    │       ├── com/varcal/markov_chain.fxml          # Interfaz gráfica
    │       └── com/varcal/style.css                  # Estilos visuales (CSS)

## 💡 Contribuciones

    ¡Las contribuciones son bienvenidas!
    Puedes abrir issues, enviar pull requests o proponer mejoras.

## 🧑‍💻 Autor
    Desarrollado por VarCal Tech.
    📍 Caquetá, Colombia

## 📄 Licencia
    Este proyecto está bajo la Licencia MIT.
    Consulta el archivo LICENSE para más detalles.
