# PROJET CV

# Groupe
- Enzo BATISTA
- Paul DE BOISSEL
- Rémi BELTRAMINI
- Clément BARDOUX

# Installation

1) Importer le projet dans IntelliJ IDEA en important le fichier "pom.xml" à la racine de ce répertoire

2) Importer le fichier application.properties dans src/main/resources

3) Importer le fichier onlinecv-83159-firebase-adminsdk-nofc8-1f5165cafe.json dans src/main/java/io/takima/demo

4) Importer les librairies Maven nécessaires à l'exécution du projet.

5) Exécuter votre DB mysql. Si vous avez docker, vous pouvez utiliser la commande suivante:
    ```
    docker run --name mariadb --rm -e MYSQL_ROOT_PASSWORD=toor -e MYSQL_DATABASE=defaultdb -p 3306:3306 -v "`pwd`/initdb:/docker-entrypoint-initdb.d" mariadb
    ```
6) Si vous n'avez pas Docker, et que vous avez un serveur MariaDB custom, vérifiez bien que vos utilisateurs / mdp sont les bons par rapport au fichier de configuration (src/main/resources/application.properties), et exécutez les scripts présents dans le dossier `initdb`

7) Tous les scripts sql contenus dans le dossier initdb seront exécutés automatiquement lors du premier chargement de la DB. 
    - Pour charger la DB : New => Data Source => MariaDB
(Démarche pas à pas sous Windows)
    - Name : vide
    - Host : Windows: 192.168.99.100 (Linux : localhost)
    - User : root
    - Password : toor
    - Database : defaultdb
    - Puis dans initdb, exécuter 1_TABLES.sql
    - Dans Target Data Source, ajouter la base de donnée fraichement créée, puis exécuter.
    - Exécuter 2_Default_Entries.sql de la même manière.

8) Exécuter l'application via IntelliJ, et vérifiez qu'elle fonctionne sur http://localhost:8080 (par défaut)

9) Connexion admin : user : noreply.supercv@gmail.com
password : rootroot

# Bilan : 

### Fonctionnalités réalisées :
- Créer / éditer / supprimer toutes les sections de du profil : about, experience, education, skills, hobbies
- Éditer la meta-data de chaque élément du profil : nom, email, photo de profil
- Ajouter des liens vers réseaux sociaux professionnels : LinkedIn, Github, Twitter, Instagram
- Affichage du CV en plusieurs pages (navigation)
- Autoriser à être contacté à travers un formulaire de contact, qui envoie un email directement

### Bonus réalisés:
- Autoriser l'import / export des données de votre CV vers un fichier JSON externe
- Importer la donnée depuis votre compte LinkedIn (juste le nom/prénom par manque de droit)
- Proposer une customisation des couleurs 
- Implémenter la sécurité (utiliser Firebase Auth)
- Faire en sorte que toutes les sections utilisent du Markdown et utiliser un éditeur Markdown pour formater votre contenu
