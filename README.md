Tu mejor compra
===============

Aplicacion de ejercicio de Java 7 EE con tecnologias como: Java Server Faces (JSF), Servlets, Websockets, JSON, Java persistence API y Enterprise
JavaBeans. Como tambien Git, SCSS y Mysql.

La aplicacion se dividio en 2 branches. Estos representan el tipo de diseño que se adopto para el desarrollo de esta.


Responsive Design
-------------------------

Este diseño fue desarrollado con el framework **Bootstrap**.
Para observar la ejecucion correr lo siguiente en la carpeta raiz:
```
mvn clean package
docker-compose build
docker-compose up
```
Luego ir a la URL: `http://localhost:8080/`

Sitio para escritorio y sitio para moviles
------------------------------------------

Este diseño fue desarrollado con el framework **PrimeFaces**.
Para observar la ejecucion correr lo siguiente en la carpeta raiz:
```
mvn clean package
docker-compose build
docker-compose up
```
Luego ir a la URL: `http://localhost:8080/`
