# Proyecto-Grupo1
Nombres de usuarios y contraseñas:

Usuario Administrador: Usuario: Admin Password: Admin

Usuario Consulta: Usuario: Consulta Password: Consulta

Usuario Cliente: Usuario: Cliente Password: Cliente

Creación de las carpetas y rutas para los archivos serializables

1-En la raíz del proyecto al momento de descomprimirlo o clonarlo, se debe crear una carpeta llamada "Archivos", la cual debe contener  otras 3 carpetas, con los nombres de:
-Carpeta1
-Carpeta2
-Carpeta3


2-Dentro de cualquiera de las carpetas debe copiar pegar y los archivos txt manejados con JSON para su funcionamiento
correcto, esto debido a que se están utilizando rutas absolutas en vez de relativas.

3-Ingrese al Json_Utility y en método createPathFather(), en la primera línea cambie la ruta de los archivos por la ruta origen de
la carpeta archivos.

4-En el mismo Json_Utility en el método getPath(), en el último if, de igual forma cambie por la ruta por la misma que anterior,
pero añadiendo al final \\Carpeta2


Uso de Librerias:
Añadir las librerias que están en el WinRar.lib y la dependencia com.itextpdf.text

Si da error el email ir al module info y comentar la línea donde esté // requires javax.mail.api;
