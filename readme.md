# 📊 Calculadora de Cadenas de Markov

Esta es una aplicación de escritorio interactiva desarrollada en Java con JavaFX que permite realizar cálculos relacionados con cadenas de Markov, incluyendo análisis de cadenas regulares y absorbentes. Entre sus funcionalidades se incluyen el cálculo de vectores de estado futuro, estado estacionario, detección de estados absorbentes y matriz fundamental.
---

## ✨ Funcionalidades

- **🔁 Cálculo de Estado Estacionario**
    Calcula el vector de estado estacionario de una cadena de Markov, representando las probabilidades a largo plazo de estar en cada estado.

- **🔮 Cálculo de Estado Futuro (Proyección Pⁿ)**
    Calcula el vector de estado después de un número definido de pasos, mostrando cómo evolucionan las probabilidades con el tiempo.

- **🧲 Análisis de Cadenas Absorbentes**
    Incluye herramientas especializadas para trabajar con cadenas con estados absorbentes:

- **🔍 Detección de Estados Absorbentes**

- **🧮 Cálculo de Matriz Canónica**

- **📘 Cálculo de la Matriz Fundamental (N)**

- **📊 Cálculo de las Probabilidades de Absorción (B)**

- **🎨 Interfaz Gráfica Intuitiva**
    Interfaz amigable y dinámica para ingresar fácilmente la matriz de transición, el vector de estado inicial y el número de pasos.
---

## 🧭 Cómo Usar

- **✍️ Ingresa el Número de Estados**

Define el número de estados de tu cadena de Markov (n) con el control tipo Spinner.

- **✍️ Ingresa la Matriz de Transición**

Cada fila representa un estado y debe sumar 1.

- **✍️ Ingresa el Vector de Estado Inicial (opcional)**

Si no se especifica, se usará por defecto [1, 0, 0, …].

- **⚙️ Selecciona la Operación Deseada**

- **📈 Proyección de estados a n pasos (Pⁿ)**

- **📉 Estado estacionario**

- **🧲 Herramientas de análisis para cadenas absorbentes**

- **✅ Haz clic en el botón correspondiente para calcular**
    Verás los resultados generados dinámicamente en la parte inferior.
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
