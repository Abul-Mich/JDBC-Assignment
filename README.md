# Projet JDBC avec PostgreSQL - Assignment
## Connexion Java vers Base de Données PostgreSQL

## Description
Ce projet démontre l'utilisation de JDBC (Java Database Connectivity) pour se connecter à une base de données PostgreSQL 16 à partir d'une application Java standalone. Le programme crée automatiquement une table, insère des données et affiche les résultats.

## Informations sur l'Auteur

**Nom :** Michel Daher Geha  
**Date :** Février 2026  

## Logiciels et Versions Utilisés

### Configuration Système
- **Java Development Kit (JDK) :** Version 17 ou supérieure
- **PostgreSQL :** Version 16
- **pgAdmin :** Version 4 (interface graphique pour PostgreSQL)
- **Visual Studio Code :** Dernière version
- **Driver JDBC PostgreSQL :** Version 42.7.9

## Structure du Projet
```
jdbcAssignment/
├── lib/
│   └── postgresql-42.7.9.jar
├── jdbcAssignment/
│   └── Assignment.java
├── env/
│   └── Utils.java
└── README.md
```

## Fichiers du Projet

### 1. Assignment.java (Fichier Principal)
Programme principal qui effectue les opérations suivantes :
- Connexion à la base de données PostgreSQL
- Création de la table `personnes` si elle n'existe pas
- Affichage des données existantes
- Réinitialisation de la table (TRUNCATE)
- Insertion de nouvelles données
- Affichage des nouvelles données

### 2. Utils.java (Fichier Utilitaire)
Classe utilitaire pour gérer le mot de passe de manière sécurisée :
```java
package env;

public class Utils {
    public static String getPassword() {
        return "votre_mot_de_passe_postgresql";
    }
}
```

**Note :** Remplacer `"votre_mot_de_passe_postgresql"` par votre mot de passe PostgreSQL réel.

## Démarche d'Installation et Configuration

### Étape 1 : Installation de PostgreSQL 16

### Étape 2 : Installation du JDK

### Étape 3 : Installation de Visual Studio Code

### Étape 4 : Téléchargement du Driver JDBC PostgreSQL

1. **Téléchargement**
   - Aller sur https://jdbc.postgresql.org/download/
   - Cliquer sur "PostgreSQL JDBC 42.7.9 Driver"
   - Télécharger le fichier `postgresql-42.7.9.jar`

2. **Organisation du Projet**
   - Créer la structure de dossiers du projet
   - Créer un dossier `lib` à la racine du projet
   - Copier `postgresql-42.7.9.jar` dans le dossier `lib`

### Étape 5 : Configuration du Classpath dans VS Code

**Méthode : Configure Classpath**

1. Ouvrir VS Code dans le dossier du projet
2. Appuyer sur `Ctrl+Shift+P` pour ouvrir la palette de commandes
3. Taper et sélectionner : `Java: Configure Classpath`
4. Dans la fenêtre qui s'ouvre, section "Referenced Libraries"
5. Cliquer sur le bouton `+` (Add)
6. Naviguer vers `lib/postgresql-42.7.9.jar`
7. Sélectionner le fichier et cliquer sur "Select JAR libraries"
8. Vérifier que le JAR apparaît dans la liste

**Vérification :**
- Dans l'explorateur VS Code, développer "Java Projects"
- Vérifier que `postgresql-42.7.9.jar` apparaît sous "Referenced Libraries"

## Création de la Base de Données

### Méthode : Utilisation de pgAdmin

1. **Lancer pgAdmin 4**
   - Ouvrir pgAdmin depuis le menu Démarrer
   - Entrer le mot de passe maître de pgAdmin (défini à l'installation)

2. **Se connecter au serveur PostgreSQL**
   - Dans le panneau de gauche, développer "Servers"
   - Cliquer sur "PostgreSQL 16"
   - Entrer le mot de passe de l'utilisateur `postgres`

3. **Créer la base de données**
   - Clic droit sur "Databases" → "Create" → "Database..."
   - Dans l'onglet "General" :
     - Database : `jdbc`
     - Owner : `postgres`
   - Cliquer sur "Save"

4. **Vérification**
   - La base de données `jdbc` devrait apparaître dans la liste des bases de données
   - Développer `jdbc` → `Schemas` → `public` → `Tables`
   - La table sera créée automatiquement par le programme Java

## Code Source Expliqué

### Fichier Utils.java
```java
package env;

public class Utils {
    /**
     * Méthode pour retourner le mot de passe PostgreSQL
     * Cette approche permet de ne pas hardcoder le mot de passe dans le code principal
     * @return Le mot de passe de la base de données
     */
    public static String getPassword() {
        return "votre_mot_de_passe_postgresql";
    }
}
```

**Important :** Remplacer `"votre_mot_de_passe_postgresql"` par votre vrai mot de passe PostgreSQL.

### Fichier Assignment.java - Détails du Code
```java
package jdbcAssignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import env.Utils;

public class Assignment {
    public static void main(String[] args) {
        // 1. PARAMÈTRES DE CONNEXION
        String var1 = "jdbc:postgresql://localhost:5432/jdbc";  // URL de connexion
        String var2 = "postgres";  // Nom d'utilisateur
        String var3 = Utils.getPassword();  // Mot de passe depuis Utils
        
        // 2. DÉCLARATION DES VARIABLES JDBC
        Connection var4 = null;  // Connexion à la base
        Statement var5 = null;   // Pour exécuter les requêtes SQL
        ResultSet var6 = null;   // Pour stocker les résultats

        try {
            // 3. CHARGEMENT DU DRIVER JDBC
            Class.forName("org.postgresql.Driver");
            
            // 4. ÉTABLISSEMENT DE LA CONNEXION
            var4 = DriverManager.getConnection(var1, var2, var3);
            System.out.println("Connected to PostgreSQL database successfully!");
            System.out.println("Username: Michel Daher Geha\n");
            
            // 5. CRÉATION DU STATEMENT
            var5 = var4.createStatement();

            // 6. CRÉATION DE LA TABLE (SI ELLE N'EXISTE PAS)
            var5.executeUpdate("CREATE TABLE IF NOT EXISTS personnes (" +
                    "nom VARCHAR(20)," +
                    "age INT )");

            // 7. AFFICHAGE DES DONNÉES EXISTANTES
            var6 = var5.executeQuery("SELECT * FROM personnes");
            while (var6.next()) {
                String var7 = var6.getString("nom");
                int var8 = var6.getInt("age");
                System.out.println(var7 + " a " + var8 + " ans");
            }

            // 8. RÉINITIALISATION DE LA TABLE
            System.out.println("\nResetting the table personnes and inserting new rows...\n");
            var5.executeUpdate("TRUNCATE TABLE personnes");

            // 9. INSERTION DE NOUVELLES DONNÉES
            var5.executeUpdate("INSERT INTO personnes (nom, age)" +
                    "VALUES ('michel daher geha', 21)," +
                    "('will smith', 50);");

            // 10. AFFICHAGE DES NOUVELLES DONNÉES
            var6 = var5.executeQuery("SELECT * FROM personnes");
            while (var6.next()) {
                String var7 = var6.getString("nom");
                int var8 = var6.getInt("age");
                System.out.println(var7 + " a " + var8 + " ans");
            }
            
        } catch (ClassNotFoundException var19) {
            // Gestion erreur : Driver non trouvé
            System.out.println("PostgreSQL JDBC Driver not found!");
            var19.printStackTrace();
        } catch (SQLException var20) {
            // Gestion erreur : Problème de connexion ou SQL
            System.out.println("Connection failed!");
            var20.printStackTrace();
        } finally {
            // 11. FERMETURE DES RESSOURCES (TOUJOURS EXÉCUTÉ)
            try {
                if (var6 != null) var6.close();
                if (var5 != null) var5.close();
                if (var4 != null) var4.close();
                System.out.println("Connection closed.");
            } catch (SQLException var18) {
                var18.printStackTrace();
            }
        }
    }
}
```

## Explication des Concepts JDBC Utilisés

### 1. URL de Connexion JDBC
```java
String var1 = "jdbc:postgresql://localhost:5432/jdbc";
```
- **jdbc:postgresql://** : Protocole JDBC pour PostgreSQL
- **localhost** : Serveur (machine locale)
- **5432** : Port PostgreSQL par défaut
- **jdbc** : Nom de la base de données

### 2. Chargement du Driver
```java
Class.forName("org.postgresql.Driver");
```
- Charge dynamiquement le driver PostgreSQL dans la JVM
- Nécessaire pour que JDBC puisse communiquer avec PostgreSQL

### 3. Connexion
```java
var4 = DriverManager.getConnection(var1, var2, var3);
```
- Établit une connexion à la base de données
- Utilise l'URL, le nom d'utilisateur et le mot de passe

### 4. Statement
```java
var5 = var4.createStatement();
```
- Crée un objet Statement pour exécuter des requêtes SQL
- Permet d'exécuter des commandes SQL sur la base de données

### 5. Méthodes SQL

**CREATE TABLE IF NOT EXISTS**
```java
var5.executeUpdate("CREATE TABLE IF NOT EXISTS personnes (...)");
```
- Crée la table seulement si elle n'existe pas déjà
- Évite les erreurs si la table est déjà présente

**TRUNCATE TABLE**
```java
var5.executeUpdate("TRUNCATE TABLE personnes");
```
- Supprime toutes les données de la table
- Plus rapide que DELETE pour vider une table

**INSERT**
```java
var5.executeUpdate("INSERT INTO personnes (nom, age) VALUES (...), (...)");
```
- Insère plusieurs lignes en une seule requête
- Utilise `executeUpdate()` car c'est une modification de données

**SELECT**
```java
var6 = var5.executeQuery("SELECT * FROM personnes");
```
- Récupère les données de la table
- Utilise `executeQuery()` car retourne un ResultSet

### 6. ResultSet
```java
while (var6.next()) {
    String var7 = var6.getString("nom");
    int var8 = var6.getInt("age");
}
```
- `next()` : Avance au prochain enregistrement
- `getString()` : Récupère une valeur String
- `getInt()` : Récupère une valeur Integer

### 7. Gestion des Exceptions

**ClassNotFoundException**
- Lancée si le driver JDBC n'est pas trouvé
- Vérifie que le JAR est bien dans le classpath

**SQLException**
- Lancée pour toute erreur SQL ou de connexion
- Peut indiquer : mot de passe incorrect, base inexistante, erreur SQL, etc.

### 8. Bloc Finally
```java
finally {
    // Fermeture des ressources
}
```
- **Toujours exécuté**, même en cas d'exception
- Important pour libérer les ressources (connexions, statements, resultsets)
- Ordre de fermeture : ResultSet → Statement → Connection

## Compilation et Exécution

### Via le Terminal VS Code

1. **Ouvrir le terminal dans VS Code**

2. **Compilation**
```bash
   javac -cp ".;lib/postgresql-42.7.9.jar" env/Utils.java jdbcAssignment/Assignment.java
```

3. **Exécution**
```bash
   java -cp ".;lib/postgresql-42.7.9.jar" jdbcAssignment.Assignment
```

## Lien du Repository GitHub

**Repository :** `https://github.com/Abul-Mich/JDBC-Assignment`

## Notes Finales

Ce projet démontre :
- ✅ La connexion JDBC à PostgreSQL
- ✅ L'utilisation des méthodes Statement
- ✅ La création dynamique de tables
- ✅ L'insertion et la lecture de données
- ✅ La gestion correcte des ressources
- ✅ La gestion des exceptions
- ✅ L'utilisation de classes utilitaires

**Réalisé par :** Michel Daher Geha  
**Date de réalisation :** Février 2026  
**Technologie :** Java JDBC + PostgreSQL 16  
**IDE :** Visual Studio Code  

---

