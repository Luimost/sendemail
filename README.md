<h1 align="center"> Enviar Correo con Java y SMTP Gmail </h1>

## Ambiente de Trabajo
Este proyecto se ha realizado usando el SO Windows Server 2022 64bits y Base de datos Oracle 11g
|Plataforma|Versión|
|-|:-:|
|Equipo Anfitrion|JDK 1.6.0_43|
|Base de Datos Oracle|JAVAMV 1.6.0._43|
|Equipo Anfitrion|JRE 1.8.0.351|

## Desarrollo
1. Compilamos el archivo `SendEmail.java`
```
javac SendEmail.java
```
2. Se genera la clase SendEmail.class
3. Se crea una carpera META-INF con el fichero `MANIFEST.MF` con el siguiente contenido:
```shell
Manifest-Version: 1.0
Class-Path: mail.jar
Main-Class: SendEmail
```
4. Luego se procede a generar el JAR con el siguiente comando en la misma ubicación de la clase compilada
```
jar cfm SendEmail.jar META-INF/MANIFEST.MF SendEmail.class
```
5. Ubicamos el fichero SendEmail.jar en la ubicacion por defecto `c:\emailsica` junto 
con el fichero `mail.jar` extraido del `JavaMail-1.4.7` en la misma ubicacion.

6. Procedmos a cargar la Base de Datos Oracle el fichero de nombre `MailSender.java` el cual es usado
para llamar la ejecución de fichero `SendEmail.jar` desde Oracle enviando todos los parametros
necesarios para enviar el correo con su cabecera y cuerpo segun se haya definido en los procedimientos
que hacen el llamado de dicho fichero MailSender.java

### Nota
> Cuando se procede a enviar un correo, la clase SendEmail crea un fichero log en donde se pueden revisar
los resultados de los envíos realizados. La ubicación es en `c:\emailsica` con el nombre `log.txt`

## Cuerpo de Email para el SICA
En la BD Oracle se tienen que modificar las siguientes funciones:
- STI_CUERPO_CORREO
- STI_CUERPO_CORREORRHH
- STI_CUERPO_REQ

Agregando las siguientes lineas de codigo despues de las declaraciones de la funcion
```SQL
-- BEGIN Editar Cabecera Html
    V_pag_init := '<html><head><title></title></head><body>';
    V_css_8080 := '';
    V_css := '';
-- END Editar Cabecera Html
```
Con estos cambios los correos no tendran inconvenientes en mostrar su contenido con HTML Limpio.



**Att. Luis Mostacero**