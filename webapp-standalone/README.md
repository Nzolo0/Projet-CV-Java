# PROJET CV 
=====

1. Importer le projet dans IntelliJ IDEA en important le fichier "pom.xml" à la racine de ce répertoire

2. Importer le fichier application.properties dans src/main/resources

3. Importer le fichier onlinecv-83159-firebase-adminsdk-nofc8-1f5165cafe.json dans src/main/java/io/takima/demo

4. Importer les librairies Maven nécessaires à l'exécution du projet.

5. Exécuter votre DB mysql. Si vous avez docker, vous pouvez utiliser la commande suivante:
```
docker run --name mariadb --rm -e MYSQL_ROOT_PASSWORD=toor -e MYSQL_DATABASE=defaultdb -p 3306:3306 -v "`pwd`/initdb:/docker-entrypoint-initdb.d" mariadb
```

6. Si vous n'avez pas Docker, et que vous avez un serveur MariaDB custom, vérifiez bien que vos utilisateurs / mdp sont les bons par rapport au fichier de configuration (src/main/resources/application.properties), et exécutez les scripts présents dans le dossier `initdb`

7. Tous les scripts sql contenus dans le dossier initdb seront exécutés automatiquement lors du premier chargement de la DB. 
Pour charger la DB : New => Data Source => MariaDB
(Démarche pas à pas sous Windows)
Name : vide
Host : 192.168.99.100
User : root
Password : toor
Database : defaultdb
Puis dans initdb, exécuter 1_TABLES.sql
Dans Target Data Source, ajouter la base de donnée fraichement créée, puis exécuter.
Exécuter 2_Default_Entries.sql de la même manière.

8. Exécuter l'application via IntelliJ, et vérifiez qu'elle fonctionne sur http://localhost:8080 (par défaut)

9. Connexion admin : user : noreply.supercv@gmail.com
password : rootroot